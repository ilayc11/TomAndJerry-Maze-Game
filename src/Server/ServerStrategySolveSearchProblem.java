package Server;

import java.io.*;
import algorithms.mazeGenerators.*;
import algorithms.search.*;
import Server.Configurations;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;


public class ServerStrategySolveSearchProblem implements IServerStrategy, Serializable{
    /*
    numberOfSolution for safe implementation of counter
     using AtomicInteger in thread based environment :
     */
    private AtomicInteger numberOfSolutions;

    public ServerStrategySolveSearchProblem(){
        this.numberOfSolutions = new AtomicInteger(0);
    }
    /**
     * The strategy gets from the clients Maze, solves it and finally returns to the
     * client Solve object with the maze solution
     * @param inputStream input stream that the server got from the socket connection
     * @param outputStream output stream that the server got from the socket connection
     */
    @Override
    public void ServerStrategyHendler(InputStream inputStream, OutputStream outputStream) {
        try {
            ObjectInputStream out = new ObjectInputStream(inputStream);
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            Maze clientMaze = (Maze) out.readObject();
            Solution MazeSolutionForClient = findAvailableSolutionForMaze(clientMaze);
            /*
            if the solution is null, then client's Maze is not on the disk
             */
            if(MazeSolutionForClient == null){
                ISearchingAlgorithm searchAlgo = Configurations.getInstance().getSearchingAlgorithm();
                MazeSolutionForClient = searchAlgo.solve((ISearchable) clientMaze);
                SaveSolutionToFile(MazeSolutionForClient, clientMaze);
                oos.writeObject(MazeSolutionForClient);

                out.close();
                oos.flush();
                oos.close();
                return;
            }

            oos.writeObject(MazeSolutionForClient);
            out.close();
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The next method helps the Server to find available solution to clients Maze
     * object, the method search that Maze in the disk, if the maze found, it retrieves
     * his solution, if not found, return null.
     * @param maze clients Maze.
     * @return Solution object that represents the solution to the maze or null if the
     * Maze is not on the disk.
     */
    private Solution findAvailableSolutionForMaze(Maze maze){
        if(maze == null)
            return null;
        Maze wantedMaze;
        String tmpdir = System.getProperty("java.io.tmpdir");
        File f = new File(tmpdir);
        File[] lsOfTempFiles = f.listFiles();

        for(int i = 0; i < lsOfTempFiles.length; i++){
            try{
                FileInputStream fis = new FileInputStream(lsOfTempFiles[i]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                wantedMaze = (Maze) ois.readObject();
                if(maze.equals(wantedMaze)){
                    /*
                    get the solution for the maze from the file and return it :
                     */
                    String solutionNumber = lsOfTempFiles[i].toString().substring(0,1);
                    FileInputStream SolFile = new FileInputStream(solutionNumber);
                    ObjectInputStream ois2 = new ObjectInputStream(SolFile);
                    Solution solution = (Solution) ois2.readObject();
                    return solution;
                }
            }catch (IOException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * The method helps the server to save the Maze object and it's Solution
     * object on the disk for faster performance in retrieving answer to the client.
     * The method saves the maze and his solution in seperated files
     * @param solution Solution object that represents solution to maze
     * @param maze Maze object from the client
     */
    public void SaveSolutionToFile(Solution solution, Maze maze){
        int solNum = this.numberOfSolutions.incrementAndGet();
        // creates string that represents the current solution in the file :
        String solName = String.format("%dSolution.txt", solNum);
        String mazeName = String.format("%dMaze",solNum);
        try {
            /*
            tries to write the Solution for the maze to the file,
            so it can be used later by the server to find the correct
            solution to that exact maze if possible :
             */
            FileOutputStream out = new FileOutputStream(solName);
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(solution);

            oos.flush();
            oos.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try {
            /*
            tries to write the Maze object to the file
            (other file) so it can be used later by the server
            to determine if there are exact Maze object as
            the clients request for faster respond and solution :
             */
            Path temp = Files.createTempFile(mazeName, ".tmp");
            /*
            in case the file already exist at the path, delete it :
             */
            temp.toFile().deleteOnExit();

            FileOutputStream out = new FileOutputStream(String.valueOf(temp));
            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(maze);

            oos.flush();
            oos.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


