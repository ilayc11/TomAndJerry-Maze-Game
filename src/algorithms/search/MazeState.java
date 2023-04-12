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
    private final Position position;



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


    /**
     *
     */
    @Override
    public void setCost(AState parentState) {
        MazeState parent = (MazeState) parentState;
        if (Math.abs(parent.getPosition().getRowIndex() - this.getPosition().getRowIndex()) == 1
                && Math.abs(parent.getPosition().getColumnIndex() - this.getPosition().getColumnIndex()) == 1) {
            //h(state) = abs(state.row - goal.row) + abs(state.col - goal.col)
            this.setCost(parent.getCost() + 15);
            //
        } else
            this.setCost(parent.getCost() + 20);
    }



    @Override
    public String toString() {
        return position.toString();
    }
}