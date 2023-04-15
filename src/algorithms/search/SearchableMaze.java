package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchableMaze implements ISearchable{


    public Maze maze;
    protected MazeState startState;
    protected MazeState endState;


    public SearchableMaze(Maze m) {
        maze = m;
        startState = new MazeState(maze.getStartPosition());
        endState = new MazeState(maze.getGoalPosition());
    }

    @Override
    public MazeState getStartState() { return startState;  }

    @Override
    public MazeState getGoalState() { return  endState;}

    /**
     * This function return all the possible moves from a state. Possible move would be a one that is inside the bounds
     * of the maze and to a non wall cell.
     * @param s The current state to examine possible moves from it.
     * @return List of all the possible moves to make from s.
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> neighbors = new ArrayList<AState>();
        MazeState mazeState = (MazeState) s;   // Casting s from Astate to mazeState
        Position[][] curGrid = maze.getGrid();

        Position curPosition = mazeState.getPosition();

        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();

        /**
        check all 8 possible neighbours stating (including slants)
         */

        // [right + down] slant (if the solution exit is one of current state neighbour then add it as well) :
        if(curRow + 1 < maze.getRow() && curCol + 1 < maze.getCol() && curRow+ 1 >= 0 && curCol+ 1 >= 0) {
            if ((!curGrid[curRow + 1][curCol + 1].isWall())) neighbors.add(new MazeState(curGrid[curRow + 1][curCol + 1]));
            else { // check if the slant leads to E:
                if(curRow + 1 == this.endState.getPosition().getRowIndex() && curCol + 1 == this.endState.getPosition().getColumnIndex())
                    neighbors.add(new MazeState(curGrid[curRow + 1][curCol + 1]));
            }
        }
        // [left + down] slant :
        if(curRow + 1 < maze.getRow() && curCol - 1 < maze.getCol() && curRow + 1 >= 0 && curCol - 1 >= 0) {
            if (!curGrid[curRow + 1][curCol - 1].isWall()) neighbors.add(new MazeState(curGrid[curRow + 1][curCol - 1]));
            else { // check if the slant leads to E :
                if (curRow + 1 == this.endState.getPosition().getRowIndex() && curCol - 1 == this.endState.getPosition().getColumnIndex())
                    neighbors.add(new MazeState(curGrid[curRow + 1][curCol - 1]));
            }
        }
        // [right + up] slant :
        if(curRow - 1 < maze.getRow() && curCol + 1 < maze.getCol() && curRow - 1 >= 0 && curCol + 1 >= 0) {
            if (!curGrid[curRow - 1][curCol + 1].isWall())  neighbors.add(new MazeState(curGrid[curRow - 1][curCol + 1]));
            else
                if(curRow - 1 == this.endState.getPosition().getRowIndex() && curCol + 1 == this.endState.getPosition().getColumnIndex()) {
                    neighbors.add(new MazeState(curGrid[curRow - 1][curCol + 1]));
                }
        }
        // [just down] :
        if(curRow + 1 < maze.getRow() && curRow + 1 >= 0) {
            if (!curGrid[curRow + 1][curCol].isWall()) neighbors.add(new MazeState(curGrid[curRow + 1][curCol]));
            else
                if(curRow + 1 == this.endState.getPosition().getRowIndex() && curCol == this.endState.getPosition().getColumnIndex()){
                    neighbors.add(new MazeState(curGrid[curRow + 1][curCol]));
                }
        }
        // [just right] :
        if(curCol + 1 < maze.getCol() && curCol + 1 >= 0) {
            if (!curGrid[curRow][curCol + 1].isWall()) neighbors.add(new MazeState(curGrid[curRow][curCol + 1]));
            else
                if(curRow == this.endState.getPosition().getRowIndex() && curCol + 1 == this.endState.getPosition().getColumnIndex()){
                    neighbors.add(new MazeState(curGrid[curRow][curCol + 1]));
                }
        }
        // [just left ] :
        if(curCol - 1  < maze.getCol() && curCol - 1 >= 0) {
            if (!curGrid[curRow][curCol - 1].isWall()) neighbors.add(new MazeState(curGrid[curRow][curCol - 1]));
            else
                if(curRow == this.endState.getPosition().getRowIndex() && curCol - 1 == this.endState.getPosition().getColumnIndex()){
                    neighbors.add(new MazeState(curGrid[curRow][curCol - 1]));
                }
        }
        // [just up ] :
        if(curRow - 1 < maze.getRow() && curRow - 1 >= 0 ) {
            if (!curGrid[curRow - 1][curCol].isWall()) neighbors.add(new MazeState(curGrid[curRow - 1][curCol]));
            else
                if(curRow - 1 == this.endState.getPosition().getRowIndex() && curCol == this.endState.getPosition().getColumnIndex()){
                    neighbors.add(new MazeState(curGrid[curRow - 1][curCol]));
                }
        }
        // [left + up] slant :
        if(curRow - 1 < maze.getRow() && curCol - 1 < maze.getCol() && curRow - 1 >= 0 && curCol - 1 >= 0) {
            if (!curGrid[curRow - 1][curCol - 1].isWall()) neighbors.add(new MazeState(curGrid[curRow - 1][curCol - 1]));
            else
                if(curRow - 1 == this.endState.getPosition().getRowIndex() && curCol - 1 == this.endState.getPosition().getColumnIndex()){
                    neighbors.add(new MazeState(curGrid[curRow - 1][curCol - 1]));
                }
        }
        return neighbors;
    }

    /**
     * This function cleaning the maze by setting all the cells to unvisited for the next search algorithm to solve it.
     * @return Int number of the cells that been visited in the search.
     */

    @Override
    public int cleanSearchable() {
        int numOfNodesVisited = 0;
        for (int row=0; row<maze.getRow(); row++) {
            for (int col = 0; col < maze.getCol(); col++) {

                if (maze.getGrid()[row][col].isVisited() && !maze.getGrid()[row][col].isWall()){
                    numOfNodesVisited++;
                }
                maze.getGrid()[row][col].setIsVisited(false);
            }
        }
        return numOfNodesVisited;
    }
    public void printMaze(){
        this.maze.print();
    }
}