package algorithms.mazeGenerators;

public interface IMazeGenerator {

    Maze generate(int rowNum, int colNum);
    long measureAlgorithmTimeMillis(int rowNum,int colNum);
}
