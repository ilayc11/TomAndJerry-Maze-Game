package algorithms.search;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class AState implements Comparator<AState> {
    /**
     * This abstract class represents state that going to be extended by other
     * subclasses (such as Maze)
     * The class helps to know if we visited in that state, what the cost of that state,
     * what the state of our "father" and what our goal state
     */
    protected boolean visited;
    private String state;
    private int cost;
    private AState cameFrom;

    public AState goalState;

    /**
     Compare Astates based on their cost
     This method is basically for Best First Search algorithm,
     so we will be able to determine the best path by cost
     */
    @Override
    public int compare(AState o1, AState o2) {
        return Integer.compare(o1.cost, o2.cost);
    }

    public void setParentState(AState p) { cameFrom = p; }

    public void setState(String s){ this.state = s;}

    public String getState(){ return this.state;}

    public AState getParentState() {return cameFrom;}

    public void setVisited(boolean visited) {}

    public void setCost(int cost) { this.cost = cost; }

    public int getCost() {return cost;}

    public boolean isVisited() {return visited;}

    public void setCost(AState parent) {}

    public void setGoalState(AState goal){}

    public boolean getVisited() {return this.visited;}

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object os);
}
