package View;

import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import algorithms.mazeGenerators.Maze;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameTarget = new SimpleStringProperty();
    StringProperty imageFileNameSolveSign = new SimpleStringProperty();

    private int row_player =-1;
    private int col_player =-1;
    private int row_target = -1;
    private int col_target = -1;
    public MazeDisplayer() {
        // Redraw canvas when size changes.
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
    }
    public int getRow_target() {
        return row_target;
    }

    public int getCol_target() {
        return col_target;
    }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    public void setPlayerPosition(int row,int col){
        this.row_player = row;
        this.col_player = col;
    }
    public void setTargetPosition(int row,int col){
        this.row_target = row;
        this.col_target = col;
    }
    public void setImageFileNameSolveSign(String imageFileNameSolveSign) {
        this.imageFileNameSolveSign.set(imageFileNameSolveSign);
    }
    public Maze getMaze() {
        return maze;
    }

    public String getImageFileNameSolveSign() { return imageFileNameSolveSign.get();}


    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String getImageFileNameTarget() {
        return imageFileNameTarget.get();
    }

    public void setImageFileNameTarget(String imageFileNameTarget) {
        this.imageFileNameTarget.set(imageFileNameTarget);
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }
    public void drawMaze(Maze maze) {
        this.maze = maze;

        draw();
    }
    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRow();
            int cols = maze.getCol();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;
            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, getWidth(), getHeight());
            graphicsContext.setFill(Color.BROWN);

            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no wall image.");
            }
            Position check=maze.getGoalPosition();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    //if it is a wall:
                    if(maze.isWall(new Position(i,j))&&(i!=check.getRowIndex()||j!=check.getColumnIndex())){

                        double w = j * cellWidth;
                        double h = i * cellHeight;
                        // set wall image
                        if(wallImage == null)
                            graphicsContext.fillRect(w,h, cellWidth, cellHeight);
                        else
                            graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                    }
                }
            }

            setTargetImage(cellWidth,cellHeight);
            setPlayerImage(cellWidth,cellHeight);
        }
    }
    public void drawSolveMaze(ArrayList<MazeState> sol){
        // need to be change when size are not fixed and maybe put in other function.
        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        int rows = maze.getRow();
        int cols = maze.getCol();

        double cellHeight = canvasHeight / rows;
        double cellWidth = canvasWidth / cols;
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.setFill(Color.SILVER);

        Image SolveImage = null;
        try {
            SolveImage = new Image(new FileInputStream(getImageFileNameSolveSign()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Solve symbol image.");
        }
        double w,h;
        int row,col;
        for(int i=0;i< sol.size();i++){

            Position currentPos = sol.get(i).getPosition();
            row = currentPos.getRowIndex();
            col = currentPos.getColumnIndex();

            if(row_target == row && col_target == col) continue;
            if((row_player == row && col_player == col)) break;
            w = currentPos.getColumnIndex() * cellWidth;
            h = currentPos.getRowIndex() * cellHeight;
            if(SolveImage == null)
                graphicsContext.fillRect( w,h, cellWidth, cellHeight);
            else
                graphicsContext.drawImage(SolveImage,w,h,cellWidth,cellHeight);
        }


    }

    private void setTargetImage(double cellWidth,double cellHeight){
        if( row_target == -1 || col_target == -1){
            row_target = maze.getGoalPosition().getRowIndex();
            col_target = maze.getGoalPosition().getColumnIndex();
        }
        GraphicsContext graphicsContext = getGraphicsContext2D();
        double h_player =  row_target * cellHeight;
        double w_player =  col_target * cellWidth;
        Image TargetImage = null;
        try {
            TargetImage = new Image(new FileInputStream(getImageFileNameTarget()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image target.");
        }

        graphicsContext.setFill(Color.CHOCOLATE);
        if(TargetImage == null)
            graphicsContext.fillRect( w_player,h_player, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(TargetImage,w_player,h_player,cellWidth,cellHeight);
    }

    private void setPlayerImage(double cellWidth,double cellHeight){
        if( col_player == -1 || row_player == -1){
            col_player = maze.getStartPosition().getColumnIndex();
            row_player = maze.getStartPosition().getRowIndex();
        }
        GraphicsContext graphicsContext = getGraphicsContext2D();
        double h_player = row_player * cellHeight;
        double w_player = col_player * cellWidth;
        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image player.");
        }
        double h = maze.getStartPosition().getColumnIndex() * cellWidth;
        double w = maze.getStartPosition().getRowIndex() * cellHeight;
        graphicsContext.setFill(Color.BLUE);
        if(playerImage == null)
            graphicsContext.fillRect(w_player, h_player, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);
    }

    public void clearMaze(){
        GraphicsContext graphicsContext = getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
        setPlayerPosition(-1,-1);
        setTargetPosition(-1,-1);

    }


}
