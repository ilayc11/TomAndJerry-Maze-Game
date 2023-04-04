package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    Solution solution;

    protected int visitedNodes;

    public ASearchingAlgorithm(){
        visitedNodes = 0;
    }

    public abstract Solution solve(ISearchable s);

    public abstract String getName();

    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
}
