package algorithms.search;

import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm{

    PriorityQueue<AState> openList = new PriorityQueue<>(Comparator.comparingInt(AState::getCost));
    LinkedList<AState> closeList = new LinkedList<>();

    /**
     * This function solve the maze with Best First Search algorithm by using priority queue that return the smallest cost move forward.
     * In each move it's updating the cost in the state neighbors if the move from the current state is smaller than the previous one.
     * @param s Searchable object to solve.
     * @return A solution object that holds the path from start state to end state.
     */
    @Override
    public Solution solve(ISearchable s) {

        if(s == null) return null;

        AState start = s.getStartState();
        AState goal = s.getGoalState();
        if(start == null || goal == null) return null;

        start.setParentState(null);
        start.setVisited(true);
        start.setCost(0);
        this.openList.add(start);

        while (!openList.isEmpty()) {
            AState curState = openList.poll();
            ArrayList<AState> curNeighbors = s.getAllPossibleStates(curState);

            for (AState curNeighbor : curNeighbors) {         // For loop through all neighbors

                if(!curNeighbor.isVisited()){
                    curNeighbor.setVisited(true);
                    curNeighbor.setCost(curState);
                    curNeighbor.setParentState(curState);
                    openList.add(curNeighbor);
                }
                else{ // check if new path is better than the previous.
                    int fromQueueFlag = 0;
                    AState visitedState = getFromList(curNeighbor);

                    if( visitedState == null) {
                        visitedState = getFromQueue(curNeighbor);
                        fromQueueFlag = 1;
                    }

                    assert visitedState != null;
                    int oldCost = visitedState.getCost();
                    visitedState.setCost(curState);

                    if(oldCost < visitedState.getCost()) visitedState.setCost(oldCost);
                    else visitedState.setParentState(curState);

                    if( fromQueueFlag == 1){
                        openList.add(visitedState);
                        continue;
                    }
                    closeList.add(visitedState);

                }


                if (curNeighbor.equals(goal)) {

                    Solution sol = new Solution();

                    while (curNeighbor != null){

                        sol.setIntoSolutionArray(curNeighbor);
                        curNeighbor = curNeighbor.getParentState();
                    }

                    visitedNodes = s.cleanSearchable();

                    return sol;
                }

            }
            closeList.add(curState);
        }

        return null;
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

    private AState getFromQueue(AState curr){
        for(AState s: openList){
            if( s.getState().equals(curr.getState())) {
                openList.remove(s);
                return s;
            }

        }
        return null;
    }
    private  AState getFromList(AState curr){
        for( AState s: closeList){
            if( s.getState().equals(curr.getState())){
                closeList.remove(s);
                return s;
            }
        }
        return null;
    }

}