package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    public AMazeGenerator() {}

    public abstract Maze generate(int row, int cole);

    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols){
        /*
        The method measure the time takes to generate maze by generate()
        and returns long
         */
        long start = System.currentTimeMillis();
        Maze m = generate(rows, cols);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
