package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rowNum, int colNum){
        return new Maze(rowNum, colNum);
    }
}
