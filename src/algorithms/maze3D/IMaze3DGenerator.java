package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;

public interface IMaze3DGenerator {
    Maze3D generate(int depth,int rowNum, int colNum);
    long measureAlgorithmTimeMillis(int depth,int rowNum,int colNum);
}
