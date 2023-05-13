package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static AtomicInteger numOfSolution ;

    /**
     * This function apply the server strategy to solve a given maze. First it checks if the maze already been solved
     * earlier if it does then it will send back to the client the existing solution throw the socket.Else
     * it will try to solve it, write the solution to a file to hold it and then send it back to client.
     * @param inFromClient Client input stream the server got from the socket connection.
     * @param outToClient Client output  stream the server got from the socket connection.
     */
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        InputStream interruptibleInputStream = Channels.newInputStream(Channels.newChannel(inFromClient));
        try {
            ObjectInputStream fromClient = new ObjectInputStream(interruptibleInputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ISearchingAlgorithm SearchAlgorithm = Configurations.getInstance().getSearchAlgorithm();

            Maze maze = (Maze)fromClient.readObject();
            Solution sol = findIfSolutionExist(maze);
            if(sol == null){

                SearchableMaze searchableMaze = new SearchableMaze(maze);
                sol = SearchAlgorithm.solve(searchableMaze);
                writeMazeSolutionToFile(maze,sol);
            }

            toClient.writeObject(sol);
            toClient.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function search for a solution if there is one in the temp directory that hold the already solved maze object files.
     * @param maze maze to check if it been already solved.
     * @return return the existing solution to the maze if there is one or else null.
     */
    private Solution findIfSolutionExist(Maze maze) {
        String tmpdir = System.getProperty("java.io.tmpdir");
        File folder = new File(tmpdir);
        File[] listOfTempFiles = folder.listFiles();

        Maze existMaze;
        for (int i = 0; i < listOfTempFiles.length; i++) {
            try {
                FileInputStream fis = new FileInputStream(listOfTempFiles[i]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                existMaze = (Maze) ois.readObject();

                if (maze.equals(existMaze)) {

                    String fileName = listOfTempFiles[i].getName().substring(0,1) + "Solution.txt";
                    FileInputStream SolutionFile = new FileInputStream(fileName);
                    ObjectInputStream oiss = new ObjectInputStream(SolutionFile);

                    return (Solution) oiss.readObject();
                }

            } catch (FileNotFoundException e) {
                continue;
            } catch (IOException e) {
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * This function create a new files, temp file for the maze and regular file for the solution ( as requested for the project).
     * @param maze The maze we want to write to a file.
     * @param sol The solution we want to write to a file.
     */
    private void writeMazeSolutionToFile(Maze maze, Solution sol ){
        if(numOfSolution == null) numOfSolution = new AtomicInteger();
        int numSol = numOfSolution.incrementAndGet();
        String name = String.format("%dSolution.txt", numSol);
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sol);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path tempFile = Files.createTempFile(String.format("%dMaze",numSol), ".tmp");
            tempFile.toFile().deleteOnExit();
            FileOutputStream fos = new FileOutputStream(String.valueOf(tempFile));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(maze);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}