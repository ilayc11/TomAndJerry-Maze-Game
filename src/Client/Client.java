package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements IClientStrategy {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
    }

    public void communicateWithServer(){
        try {
            Socket serverSocket = new Socket("127.0.0.1", serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {

    }
}