package algorithms.maze3D;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.ISearchable;
import algorithms.search.MazeState;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {


    public Maze3D maze;
    protected Maze3DState startState;
    protected Maze3DState endState;


    public SearchableMaze3D(Maze3D m) {
        maze = m;
        startState = new Maze3DState(maze.getStartPosition());
        endState = new Maze3DState(maze.getGoalPosition());
    }

    @Override
    public Maze3DState getStartState() { return startState;  }

    @Override
    public Maze3DState getGoalState() { return  endState;}

    /**
     * This function return all the possible moves from a state. Possible move would be a one that is inside the bounds
     * of the maze and to a non wall cell.
     * @param s The current state to examine possible moves from it.
     * @return List of all the possible moves to make from s.
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> neighbors = new ArrayList<AState>();
        Maze3DState mazeState = (Maze3DState) s;   // Casting s from Astate to mazeState
        Position3D[][][] curGrid = maze.getGrid();

        Position3D curPosition = mazeState.getPosition();

        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();
        int curDep = curPosition.getDepthIndex();

        /**
         check all 8 possible neighbours stating with the 2 slants that makes
         the route to E much shorter [right+down] and [left+down] then moving
         to
         */


        // [just down] :
        if(curRow + 1 < maze.getRow() && curRow + 1 >= 0) {
            if (!curGrid[curDep][curRow + 1][curCol].isWall()) neighbors.add(new Maze3DState(curGrid[curDep][curRow + 1][curCol]));
            else
            if(curRow + 1 == this.endState.getPosition().getRowIndex() &&curDep==this.endState.getPosition().getDepthIndex()&& curCol == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep][curRow + 1][curCol]));
            }
        }
        // [just right] :
        if(curCol + 1 < maze.getCol() && curCol + 1 >= 0) {
            if (!curGrid[curDep][curRow][curCol + 1].isWall()) neighbors.add(new Maze3DState(curGrid[curDep][curRow][curCol + 1]));
            else
            if(curRow == this.endState.getPosition().getRowIndex() &&curDep==this.endState.getPosition().getDepthIndex()&& curCol + 1 == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep][curRow][curCol + 1]));
            }
        }
        // [just left ] :
        if(curCol - 1  < maze.getCol() && curCol - 1 >= 0) {
            if (!curGrid[curDep][curRow][curCol - 1].isWall()) neighbors.add(new Maze3DState(curGrid[curDep][curRow][curCol - 1]));
            else
            if(curRow == this.endState.getPosition().getRowIndex() &&curDep==this.endState.getPosition().getDepthIndex()&& curCol - 1 == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep][curRow][curCol - 1]));
            }
        }
        // [just up ] :
        if(curRow - 1 < maze.getRow() && curRow - 1 >= 0 ) {
            if (!curGrid[curDep][curRow - 1][curCol].isWall()) neighbors.add(new Maze3DState(curGrid[curDep][curRow - 1][curCol]));
            else
            if(curRow - 1 == this.endState.getPosition().getRowIndex() &&curDep== curPosition.getDepthIndex()&& curCol == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep][curRow - 1][curCol]));
            }
        }
        //[shallower ]:
        if(curDep - 1 < maze.getRow() && curDep - 1 >= 0 ) {
            if (!curGrid[curDep-1][curRow][curCol].isWall()) neighbors.add(new Maze3DState(curGrid[curDep-1][curRow][curCol]));
            else
            if(curDep - 1 == this.endState.getPosition().getDepthIndex() &&curRow== curPosition.getRowIndex()&& curCol == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep-1][curRow][curCol]));
            }
        }
        //[deeper ]:
        if(curDep + 1 < maze.getRow() && curDep + 1 >= 0 ) {
            if (!curGrid[curDep+1][curRow][curCol].isWall()) neighbors.add(new Maze3DState(curGrid[curDep+1][curRow][curCol]));
            else
            if(curDep + 1 == this.endState.getPosition().getDepthIndex() &&curRow== curPosition.getRowIndex()&& curCol == this.endState.getPosition().getColumnIndex()){
                neighbors.add(new Maze3DState(curGrid[curDep+1][curRow][curCol]));
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
        for (int depth=0;depth< maze.getDepth();depth++)
            for (int row=0; row<maze.getRow(); row++) {
                for (int col = 0; col < maze.getCol(); col++) {
                    if (maze.getGrid()[depth][row][col].isVisited() && !maze.getGrid()[depth][row][col].isWall()){
                    numOfNodesVisited++;
                }
                maze.getGrid()[depth][row][col].setIsVisited(false);
            }
        }
        return numOfNodesVisited;
    }
    public void printMaze(){
        this.maze.print();
    }
}