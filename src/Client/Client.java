package Client;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Client implements Serializable {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress _IP, int _port, IClientStrategy _clientStrategy){
        this.serverIP = _IP;
        this.serverPort = _port;
        this.clientStrategy = _clientStrategy;
    }

    public void communicateWithServer(){
        try {
            Socket ServertSocket = new Socket(this.serverIP, this.serverPort);
            System.out.println("Client is connected to server !");
            this.clientStrategy.clientStrategy(ServertSocket.getInputStream(), ServertSocket.getOutputStream());
            ServertSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}