package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

public abstract class AMaze3DGenerator implements IMaze3DGenerator{
    /**
     * abstract class that implements IMazeGenerator interface
     */
    public AMaze3DGenerator() {}

    public abstract Maze3D generate(int depth,int row, int cole);

    @Override
    public long measureAlgorithmTimeMillis(int depth,int rows, int cols){
        /**
         The method measure the time takes to generate maze by generate()
         and returns long
         */
        long start = System.currentTimeMillis();
        Maze3D m = generate(depth,rows, cols);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
