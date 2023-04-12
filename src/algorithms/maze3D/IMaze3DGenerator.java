package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

public interface IMaze3DGenerator {
    /**
     * simillar to normal mazegenerator
     *
     *
     * */
    Maze3D generate(int depth,int row, int col);
    long measureAlgorithmTimeMillis(int depth,int rowNum,int colNum);
}
