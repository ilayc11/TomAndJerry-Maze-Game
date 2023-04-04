package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    Stack<AState> openList = new Stack<>();

    @Override
    public Solution solve(ISearchable s) {
        AState start = s.getStartState();
        AState goal = s.getGoalState();
        //Random rand = new Random();

        start.setParentState(null);
        start.setVisited(true);
        openList.add(start);
        while (!openList.empty()) {

            AState curState = openList.pop();

            while( true) {

                ArrayList<AState> neighbors = s.getAllPossibleStates(curState);
                // if(neighbors.size() == 0) break;

                AState curNeighbor = null;
                for(AState neighbor: neighbors){
                    if(!neighbor.isVisited()){
                        curNeighbor = neighbor;
                        break;
                    }
                }
                if(curNeighbor == null) break;

                //AState curNeighbor = neighbors.get(rand.nextInt(neighbors.size()));
                curNeighbor.setParentState(curState);
                curNeighbor.setVisited(true);

                openList.add(curNeighbor);
                curState = curNeighbor;

                if(curState.equals(goal)){
                    Solution sol = new Solution();

                    while (curState != null) {

                        sol.setIntoSolutionArray(curState);
                        curState = curState.getParentState();
                    }

                    visitedNodes = s.cleanSearchable();

                    return sol;
                }

            }

        }

        return null;
    }

    @Override
    public String getName() {
        return "DFS";
    }


}
