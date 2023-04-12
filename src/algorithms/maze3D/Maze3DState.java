package algorithms.maze3D;

import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;

import java.io.Serializable;

public class Maze3DState extends AState implements Serializable {
    /**
     * The class represents current state in the Maze.
     * The class extends the abstract class Astate and implements the
     * interface Serializable.
     */
    private final Position3D position;



    public Maze3DState() {position=null;}

    public Maze3DState(Position3D p){
        this.position = p;
        if(p != null) setState(position.toString());
    }

    @Override
    public boolean equals(Object os)
    {
        Maze3DState ms = (Maze3DState) os;
        return this.position.getDepthIndex()==ms.position.getDepthIndex()&&position.getColumnIndex() == ms.position.getColumnIndex() && (this.position.getRowIndex() == ms.position.getRowIndex());
    }

    public Position3D getPosition() {return position;}

    @Override
    public void setVisited(boolean visited) {
        this.visited = visited;
        position.setIsVisited(visited);
    }


    @Override
    public boolean isVisited() {return position.isVisited();}



    @Override
    public void setCost(AState parentState) {
        Maze3DState parent = (Maze3DState) parentState;
        if (Math.abs(parent.getPosition().getDepthIndex()-this.getPosition().getDepthIndex())==1&&Math.abs(parent.getPosition().getRowIndex() - this.getPosition().getRowIndex()) == 1
                && Math.abs(parent.getPosition().getColumnIndex() - this.getPosition().getColumnIndex()) == 1) {
            //h(state) = abs(state.row - goal.row) + abs(state.col - goal.col)
            this.setCost(parent.getCost() + 15);
            //
        } else
            this.setCost(parent.getCost() + 20);
    }



    @Override
    public String toString() {
        return "Maze3DState{" +
                "position=" + position +
                '}';
    }
}
