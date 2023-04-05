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

        MazeState mazeState = (MazeState) s;    // DownCasting

        Position[][] curGrid = maze.getGrid();


        Position curPosition = mazeState.getPosition();

        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();

        for (int row=-1; row<2; row++){
            for (int col=-1; col<2; col++){

                // Don't check all slants, just straight routs to find neighbours
                if ((row == 0 && col == 0)|| (row == -1 && col == -1) || (row == -1 && col == 1)
                ||( row == 1 && col == -1 )||( row == 1 && col == 1)) continue;

                // Check all the 8 options beside the cur position and if valid add to neighbours
                if (curRow+row < maze.getRow() && curRow+row >= 0 && curCol+col >= 0  && curCol+col < maze.getCol() ) {
                    if (!curGrid[curRow + row][curCol + col].isWall() )
                        neighbors.add(new MazeState(curGrid[curRow + row][curCol + col]));
                }
               // if(curRow== maze.getRow()-1&&curCol==maze.getCol()-2||curRow==)


                if ((curRow==maze.getRow()-1&&curCol+col==maze.getCol()-1)) {
                    if (!curGrid[curRow + row][curCol].isWall())
                        neighbors.add(new MazeState(curGrid[curRow][curCol+col]));
                }

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