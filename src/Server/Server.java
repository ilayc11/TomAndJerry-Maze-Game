package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private int port;
    private int listeningIntervalMS;
    private volatile boolean stop;
    private IServerStrategy strategy;
    private ExecutorService threadPool;
    private Thread ServerThread;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        this.port = port;
        this.stop = false;
        this.listeningIntervalMS = listeningIntervalMS;
        this.strategy = strategy;
        this.threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getNumberOfThreads());
        this.ServerThread = new Thread(this);
    }

    /**
     * This function responsible to start the server by running separate thread
     */
    public void start(){
        if(!ServerThread.isAlive()) ServerThread.start();
    }
    public void stop(){
        synchronized ((Object)stop){
            stop = true;
        }
    }

    /**
     * This function responsible to run the server by waiting for clients to connect to the serverSocket and then send
     * him to the thread pool where he will be hanlded by the suitable strategy.
     *
     */
    @Override
    public void run()  {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while (true) {
                synchronized ((Object)stop){
                    if(stop) break;
                }
                try {
                    System.out.println("Server is running..");
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Server connected to client.");
                    // Insert the new task into the thread pool:
                    threadPool.execute(new RunStrategy(clientSocket));

                } catch (IOException e) {
                    System.out.println("Server waiting for client...");
                }
            }
            serverSocket.close();
            threadPool.shutdown();
            System.out.println("Server is closed, Bye Bye");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Private class that execute the server strategy by the threads in the thread pool.
     */
    private class RunStrategy implements Runnable{
        Socket clientSocket;
        public RunStrategy(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                strategy.applyStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}