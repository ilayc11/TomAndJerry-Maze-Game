package Model;

import IO.MyDecompressorInputStream;
import Server.*;
import View.MyViewController;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel{

    public static final Logger logger = LogManager.getLogger(MyModel.class);
    private int rowLocation;
    private int colLocation;
    private final Server genServer;
    private final Server solServer;
    private Maze maze;
    private Solution sol;
    private ArrayList<MazeState> solPath;
    private int stepsCount;

    public MyModel(){
        solPath = new ArrayList<>();
        IServerStrategy generator = new ServerStrategyGenerateMaze();
        IServerStrategy solver = new ServerStrategySolveSearchProblem();
        genServer = new Server(5400,5000,generator);
        solServer = new Server(5401,5000,solver);
        genServer.start();
        solServer.start();

        logger.info("Information about the generator server -->  " + genServer);
        logger.info("Information about the solve server -->  " + solServer);


    }

    public void closeModel(){
        try {
            File directory = new File("SavedMazes");
            for (File f : directory.listFiles())
                f.delete();
        }catch (NullPointerException e){
            System.out.println("Need to open SavedMazes folder to save maze.");
        }
        genServer.stop();
        solServer.stop();
    }

    public void updateCharacterLocation(int step) {
        boolean wallFlag = true;
        switch (step)
        {
            case 1: // </
                if(getRowLocation() + 1 < maze.getRow() && getColLocation() - 1 < maze.getCol() && getRowLocation() + 1 >= 0 && getColLocation() - 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation() + 1][getColLocation() - 1].isWall()){
                        rowLocation += 1;
                        colLocation -= 1;
                        wallFlag = false;
                    }
                    else { // check if the slant leads to E :
                        if (getRowLocation() + 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() - 1 == this.maze.getGoalPosition().getColumnIndex())
                            rowLocation += 1;
                            colLocation -= 1;
                            wallFlag = false;
                    }
                }
                break;
            case 2: // down
                if(getRowLocation() + 1 < maze.getRow() && getRowLocation() + 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation() + 1][getColLocation()].isWall()){
                        this.rowLocation += 1;
                        wallFlag = false;
                    }
                    else
                    if(this.rowLocation + 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() == this.maze.getGoalPosition().getColumnIndex()){
                        this.rowLocation += 1;
                        wallFlag = false;
                    }
                }
                break;
            case 3: // \>
                if(getRowLocation() + 1 < this.maze.getRow() && getColLocation() + 1 < this.maze.getCol() && getColLocation() + 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation() + 1][getColLocation() + 1].isWall()) {
                        this.colLocation += 1;
                        this.rowLocation += 1;
                        wallFlag = false;
                    }
                    else{ // check if the slant leads to E :
                        if(getRowLocation() + 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() + 1 == this.maze.getGoalPosition().getColumnIndex()){
                            this.colLocation += 1;
                            this.rowLocation += 1;
                            wallFlag = false;
                        }
                    }
                }
                break;
            case 4: // left
                if(getColLocation() - 1  < maze.getCol() && getColLocation() - 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation()][getColLocation() - 1].isWall()){
                        this.colLocation -= 1;
                        wallFlag = false;
                    }
                    else
                    if(getRowLocation() == this.maze.getGoalPosition().getRowIndex() && getColLocation() - 1 == this.maze.getGoalPosition().getColumnIndex()){
                        this.colLocation -= 1;
                        wallFlag = false;
                    }
                }
                break;
            case 5: wallFlag = false;
                break;
            case 6: // right
                if(getColLocation() + 1 < maze.getCol() && getColLocation() + 1 >= 0) {
                    if (!maze.getGrid()[getRowLocation()][getColLocation() + 1].isWall()){
                        this.colLocation += 1;
                        wallFlag=false;
                    }
                    else
                    if(getRowLocation() == this.maze.getGoalPosition().getRowIndex() && getColLocation() + 1 == this.maze.getGoalPosition().getColumnIndex()){
                        this.colLocation += 1;
                        wallFlag=false;
                    }
                }
                break;
            case 7: // <\
                if(getRowLocation() - 1 < maze.getRow() && getColLocation() - 1 < maze.getCol() && getRowLocation() - 1 >= 0 && getColLocation() - 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation() - 1][getColLocation() - 1].isWall()){
                        this.rowLocation -= 1;
                        this.colLocation -= 1;
                        wallFlag = false;
                    }
                    else
                    if(getRowLocation() - 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() - 1 == this.maze.getGoalPosition().getColumnIndex()){
                        this.rowLocation -= 1;
                        this.colLocation -= 1;
                        wallFlag = false;
                    }
                }
                break;
            case 8://up
                if(getRowLocation() - 1 < maze.getRow() && getRowLocation() - 1 >= 0 ) {
                    if (!this.maze.getGrid()[getRowLocation() - 1][getColLocation()].isWall()){
                        this.rowLocation -= 1;
                        wallFlag = false;                    }
                    else
                    if(getRowLocation() - 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() == this.maze.getGoalPosition().getColumnIndex()){
                        this.rowLocation -= 1;
                        wallFlag = false;                    }
                }
                break;
            case 9:
                if(getRowLocation() - 1 < maze.getRow() && getColLocation() + 1 < maze.getCol() && getRowLocation() - 1 >= 0 && getColLocation() + 1 >= 0) {
                    if (!this.maze.getGrid()[getRowLocation() - 1][getColLocation() + 1].isWall()){
                        this.rowLocation -= 1;
                        this.colLocation += 1;
                        wallFlag = false;
                    }
                    else
                    if(getRowLocation() - 1 == this.maze.getGoalPosition().getRowIndex() && getColLocation() + 1 == this.maze.getGoalPosition().getColumnIndex()) {
                        this.rowLocation -= 1;
                        this.colLocation += 1;
                        wallFlag = false;
                    }
                }
                break;
        }
        if(!wallFlag) stepsCount++;


        setChanged();
        if( rowLocation == maze.getGoalPosition().getRowIndex() && colLocation == maze.getGoalPosition().getColumnIndex())
            notifyObservers("Changed Location and win");
        notifyObservers("Character Location Changed");
    }

    public int getPlayerSteps(){return stepsCount;}

    public void loadMaze(String fileName) {
        String result = "loaded successfully";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("SavedMazes",fileName + ".txt"));
            ObjectInputStream oiss = new ObjectInputStream(fis);
            maze = (Maze) oiss.readObject();
            System.out.println("Maze start: " + maze.getStartPosition());
            oiss.close();
            rowLocation = maze.getStartPosition().getRowIndex();
            colLocation = maze.getStartPosition().getColumnIndex();

        } catch (FileNotFoundException e) {
            result = "load failed";
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(result);

    }

    public void saveMaze(String fileName){
        if(maze != null){
            try {
                FileOutputStream fos = new FileOutputStream(new File("SavedMazes",fileName + ".txt"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(maze);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateMaze(int row,int col){
        stepsCount = 0;
        try {
            Socket serverSocket = new Socket( "127.0.0.1", 5400); // change to InetAddress.getLocalHost() ,
            ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
            toServer.flush();
            toServer.writeObject(new int[]{row, col});
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[row*col+24];
            is.read(decompressedMaze);
            maze = new Maze(decompressedMaze);
            setChanged();
            notifyObservers("Maze generated");
            logger.info("Maze has been generated -->  Row: "+row+" Col: " + col);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        rowLocation = maze.getStartPosition().getRowIndex();
        colLocation = maze.getStartPosition().getColumnIndex();

    }

    public void solveMaze(){

        if(maze != null){
            try {
                Socket serverSocket = new Socket("127.0.0.1", 5401);// change to InetAddress.getLocalHost()
                ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
                ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
                toServer.flush();
                toServer.writeObject(maze); //send maze to server
                toServer.flush();
                sol = (Solution)fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if( sol != null){
                ArrayList<AState> origin = sol.getSolutionPath();
                solPath.clear();

                for (AState aState : origin) {
                    solPath.add((MazeState) aState);
                }
                setChanged();
                notifyObservers("Maze solved");
                logger.info("Maze has been solved" );
            }
        }
    }

    public Maze getMaze(){ return maze;}

    public ArrayList<MazeState> getSolutionPath(){ return solPath;}

    public int getRowLocation() {
        return this.rowLocation;
    }

    public int getColLocation() {
        return this.colLocation;
    }
}
