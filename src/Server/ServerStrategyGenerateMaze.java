package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.*;
import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy, Serializable {

    /**
     * The strategy creates maze with the corresponding parameters
     * in MazeSizesArray and compress the maze with the help of
     * MyCompressorOutputStream, finally sent it back to the
     * client byte[] the represents the generates maze
     * @param inputStream input stream that the server got from the socket connection
     * @param outputStream output stream that the server got from the socket connection
     */
    @Override
    public void ServerStrategyHendler(InputStream inputStream, OutputStream outputStream) {
        IMazeGenerator generator = Configurations.getInstance().getMazeGenerator();

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = generator.generate(mazeSize[0], mazeSize[1]);
            byte[] compressedMaze = getCompressMaze(maze); // uses private method getCompressMaze(Maze m)
            toClient.writeObject(compressedMaze);

            toClient.flush();
            toClient.close();
            fromClient.close();
        } catch (IOException e ) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] getCompressMaze(Maze m){
        String mazeFileName = "saved_C_Maze.txt";
        try{
            OutputStream outCompressor = new MyCompressorOutputStream(new FileOutputStream(mazeFileName));
            outCompressor.write(m.toByteArray());
            outCompressor.flush();
            outCompressor.close();

            InputStream in = new MyDecompressorInputStream(new FileInputStream(mazeFileName));
            byte[] ByteMaze = new byte[m.toByteArray().length];
            in.read(ByteMaze);
            in.close();
            return ByteMaze;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
