package algorithms.search;

public interface ISearchingAlgorithm {

    public String name = null;

    Solution solve(ISearchable s);

    public String getName();

    int getNumberOfNodesEvaluated();
}