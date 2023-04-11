package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     *
     * @param rowNum getting number of rows in maze
     * @param colNum getting number of cols in maze
     * @return returns a generated Empty Maze
     */
    @Override
    public Maze generate(int rowNum, int colNum){
        Maze tmp=new Maze(rowNum, colNum);
        tmp.breakAllWalls();
         return tmp;
    }
}
