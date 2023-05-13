package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.nio.channels.Channels;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    /**
     * This function apply the server strategy to generate maze, and then send it back compressed to the client by his socket connection.
     * @param inFromClient Client input stream the server got from the socket connection.
     * @param outToClient Client output  stream the server got from the socket connection.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        IMazeGenerator generator = Configurations.getInstance().getGeneratorAlgorithm();
        InputStream interruptibleInputStream = Channels.newInputStream(Channels.newChannel(inFromClient));

        try {

            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ObjectInputStream fromClient = new ObjectInputStream(interruptibleInputStream);

            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = generator.generate(mazeSize[0],mazeSize[1]);
            byte[] compressMaze = getCompressMaze(maze);
            toClient.writeObject(compressMaze);
            toClient.flush();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * getCompressMaze responsible to compress the maze, It does that by sending a file output stream to the compressor class
     * where he will write the result to the file and then this function will read it from that file and return the result.
     * @param maze The maze for compression.
     * @return compress maze represented by byte array.
     */
    private byte[] getCompressMaze(Maze maze){
        String tempFileName = "mazeHolder.txt";
        try {
            OutputStream compressor = new MyCompressorOutputStream(new FileOutputStream(tempFileName));
            compressor.write(maze.toByteArray());
            compressor.flush();

            InputStream in = new FileInputStream(tempFileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            byte[] data =out.toByteArray();
            File tempFile = new File(tempFileName);
            tempFile.delete();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}