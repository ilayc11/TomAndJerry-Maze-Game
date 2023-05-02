package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class Server implements Runnable, Serializable {
    private int port; // the port number
    private int WaitingIntervalsMS;
    private int numOfClients;
    private volatile boolean stop;
    private IServerStrategy serverStrategy;
    private ExecutorService threadPool;
    private Thread serverThread;

    public Server(int _port,int _WaitingIntervalsMS, IServerStrategy _serverStrategy){
        this.port = _port;
        this.WaitingIntervalsMS = _WaitingIntervalsMS;
        this.serverStrategy = _serverStrategy;
        this.numOfClients = 0;
        // using instance of Configuration object to extract number of pool threads :
        this.threadPool = Executors.newFixedThreadPool(Configurations.getInstance().getNumberOfThreads());
        this.serverThread = new Thread(this);
        this.stop = false;
    }

    /**
     * the method responsible to start the server
     * by running serverThread from threads pool
     */
    public void start(){
        if(!serverThread.isAlive())
            serverThread.start();
    }
    public void stop(){
        synchronized ((Object)this.stop){
            this.stop = true;
        }
    }

    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(this.port);
            serverSocket.setSoTimeout(this.WaitingIntervalsMS);
            while (true){
                synchronized ((Object) stop){
                    if(stop)
                        break;
                }
                try {
                    System.out.println("Server is running !");
                    Socket client = serverSocket.accept();
                    System.out.println("Binding with the client executed successfully !");
                    numOfClients++;
                    //this.serverThread = new ServerThread(client, numOfClients, this);
                    this.threadPool.execute(new Strategy(client, numOfClients, this));
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            serverSocket.close();
            this.threadPool.shutdown();
            System.out.println("Server is closed");

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * private class that uses the thread from thread pool to
     * execute server strategy and handle it
     */
    private class Strategy implements Runnable{
        private Server server;
        private Socket client;
        private int counterClient;

        Strategy(Socket _client, int _numOfClientsCounter, Server _server ) throws IOException {
            this.server = _server;
            this.client = _client;
            this.counterClient = _numOfClientsCounter;
            System.out.println("Connection " + this.counterClient + " established with client : " + this.client + " by server : " + this.server);
        }

        /**
         * The run() method above is synchronized to make sure server is
         * handling only one client at a time
         */
        @Override
        public synchronized void run() {
            try {
                System.out.println("Server starts handle client number -> (" + this.counterClient + ")" );
                serverStrategy.ServerStrategyHendler(this.client.getInputStream(), this.client.getOutputStream());
                this.client.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
