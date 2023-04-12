package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


public class BreadthFirstSearch extends ASearchingAlgorithm{

    LinkedList<AState> openList = new LinkedList<AState>();

    /**
     * The method solves ISearchable object in breadth first search algorithm
     * @param s ISearchable object
     * @return LinkedList with Astates objects that represents the route to the solution
     */
    @Override
    public Solution solve(ISearchable s) {

        AState start = s.getStartState();
        AState goal = s.getGoalState();

        this.openList.add(start);

        start.setParentState(null);
        start.setVisited(true);


        while (!openList.isEmpty()) {
            AState curState = openList.poll();
            if (curState.equals(goal)) {
                Solution sol = new Solution();
                while (curState != null){
                    sol.setIntoSolutionArray(curState);
                    curState = curState.getParentState();
                }
                visitedNodes = s.cleanSearchable();
                return sol;
            } //

            ArrayList<AState> curNeighbours = s.getAllPossibleStates(curState);
            for (AState curNeighbour : curNeighbours) {         // For loop through all neighbours
                if (!curNeighbour.isVisited()) {
                    curNeighbour.setVisited(true);
                    openList.add(curNeighbour);

                    curNeighbour.setParentState(curState);
                }
            }
        }

        s.cleanSearchable();

        return null;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}