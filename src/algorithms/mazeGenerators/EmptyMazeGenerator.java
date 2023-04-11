package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generate(int rowNum, int colNum){
        Maze tmp=new Maze(rowNum, colNum);
        tmp.breakAllWalls();
         return tmp;
    }
}
