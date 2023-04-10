package algorithms.search;

import java.io.Serializable;
import java.lang.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState implements Serializable {
    /**
     * The class represents current state in the Maze.
     * The class extends the abstract class Astate and implements the
     * interface Serializable.
     */
    private Position position;

    public MazeState() {position=null;}

    public MazeState(Position p){
        this.position = p;
        if(p != null) setState(position.toString());
    }

    @Override
    public boolean equals(Object os)
    {
        MazeState ms = (MazeState) os;
        return this.position.getColumnIndex() == ms.position.getColumnIndex() && (this.position.getRowIndex() == ms.position.getRowIndex());
    }

    public Position getPosition() {return position;}

    @Override
    public void setVisited(boolean visited) {
        this.visited = visited;
        position.setIsVisited(visited);
    }


    @Override
    public boolean isVisited() {return position.isVisited();}
    
    @Override
    public void setCost(AState parentState) {
        MazeState parent = (MazeState) parentState;
        if((Math.abs(this.getPosition().getRowIndex() - parent.getPosition().getRowIndex()) == 0 &&
                Math.abs(this.getPosition().getColumnIndex() - parent.getPosition().getColumnIndex()) == 1) ||
                (Math.abs(this.getPosition().getRowIndex() - parent.getPosition().getRowIndex()) == 1 &&
                        Math.abs(this.getPosition().getColumnIndex() - parent.getPosition().getColumnIndex()) == 0)) {
            MazeState parentOfparent = (MazeState)parent.getParentState();
            if(parentOfparent != null)
                if ((Math.abs(parent.getPosition().getRowIndex() - parentOfparent.getPosition().getRowIndex()) == 1 &&
                        Math.abs(parent.getPosition().getColumnIndex() - parentOfparent.getPosition().getColumnIndex()) == 0) ||
                        (Math.abs(parent.getPosition().getRowIndex() - parentOfparent.getPosition().getRowIndex()) == 0 &&
                                Math.abs(parent.getPosition().getColumnIndex() - parentOfparent.getPosition().getColumnIndex()) == 1)) {
                    /*
                    In case there are available slant, set current position parent to be the
                    parent of current parent & updated the cost to 15. (thus slant) //TODO check if working
                     */
                    this.setCost(parent.getCost() + 15);
                    this.setParentState(parentOfparent);
                }
            else this.setCost(parent.getCost() + 10);
        }
        else this.setCost(parent.getCost() + 10);
    }
/*
    @Override
    public void setCost(AState parentState) {
        MazeState parent = (MazeState) parentState;
        if (Math.abs(parent.getPosition().getRowIndex() - this.getPosition().getRowIndex()) == 1
                && Math.abs(parent.getPosition().getColumnIndex() - this.getPosition().getColumnIndex()) == 1) {
            this.setCost(parent.getCost() + 15);
        }
        else{
            this.setCost(parent.getCost() + 10);
        }
    }


 */

    @Override
    public String toString() {
        return "MazeState{" +
                "position=" + position +
                '}';
    }
}