package algorithms.search;
import java.util.ArrayList;

public abstract class AState {

    protected boolean visited;
    private String state;
    private int cost;
    private AState cameFrom;

    public void setParentState(AState p) { cameFrom = p; }

    public void setState(String s){ this.state = s;}

    public String getState(){ return this.state;}

    public AState getParentState() {return cameFrom;}

    public void setVisited(boolean visited) {}

    public void setCost(int cost) { this.cost = cost; }

    public int getCost() {return cost;}

    public boolean isVisited() {return visited;}

    public void setCost(AState parent) {}

    public boolean getVisited() {return this.visited;}

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object os);
}
