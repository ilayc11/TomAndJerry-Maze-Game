package ViewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private MyModel model;
    private Boolean askForHelp;

    public MyViewModel() {
        this.model = new MyModel();
        model.addObserver(this);
        askForHelp = false;
    }

    public int getBestSteps(){ return model.getSolutionPath().size()-1;}

    public int getPlayerSteps(){
        return  model.getPlayerSteps();
    }

    public int getRows(){ return model.getMaze().getRow();}

    public int getCols(){ return model.getMaze().getCol();}

    public void generateMaze(int row , int col ){
        askForHelp = false;
        model.generateMaze(row,col);
    }

    public void solveMaze(){
        askForHelp = true;
        model.solveMaze();
    }

    public void saveMaze(String fileName){
        model.saveMaze(fileName);
    }

    public void loadMaze(String fileName){
        model.loadMaze(fileName);
    }

    public void moveCharacter(KeyEvent keyEvent){
        // direcrtion 0 --> UP
        // direcrtion 1 --> DOWN
        // direcrtion 2 --> LEFT
        // direcrtion 3 --> RIGHT
        // direcrtion 4 --> <\
        // direcrtion 5 --> />
        // direcrtion 6 --> </
        // direcrtion 7 --> \>
        try {
            int direction = Integer.parseInt(keyEvent.getText());
            if(direction > 0 && direction < 10)
                model.updateCharacterLocation(direction);
        }catch (NumberFormatException e){
            System.out.println("Check your Num Lock please.");
        }



    }

    public void endGame(){
        model.closeModel();
    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof IModel){

            String option = (String) arg;
            if(option.equals("Changed Location and win"))
                model.solveMaze();

            setChanged();
            notifyObservers(option);
        }

    }

    public Boolean getAskForHelp(){ return askForHelp;}

    public ArrayList<MazeState> getSolutionPath(){
        return model.getSolutionPath();
    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public int getRowLocation() {
        return model.getRowLocation();
    }

    public int getColLocation() {
        return model.getColLocation();
    }

    public void setModel(MyModel model) {
        this.model = model;
    }
}
