package algorithms.maze3D;

import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMaze3DGenerator extends AMaze3DGenerator {

    Random rand = new Random();
    Position3D[][][]grid;
    int depth,row,col;
    Position3D current,next;

    /**
     * This function use back tracking DFS algorithm to generate a maze. The DFS algorithm get the possible moves it can make
     * from the current cell and then randomly chose one to move forward while breaking the wall between the cells.
     * It repeats this process until there is no unvisited cells or no more possible moves to make in the maze.
     * @param depth number of depth in the maze
     * @param row number of rows in the maze
     * @param col number of columns in the maze
     * @return a full complex maze that got at least one path to solve it.
     */

    public Maze3D generate(int depth,int row, int col){

        if( depth<1||row < 1 || col < 1){
            return null;
        }
        Maze3D maze = new Maze3D(depth,row,col);
        this.depth=depth;
        this.row=row;
        this.col=col;
         grid = maze.getGrid();

        if( depth<4||row < 4 || col < 4){
            createDefaultMaze(maze);
            return maze;
        }
        Stack<Position3D> stack = new Stack<Position3D>();
        maze.setStartPosition(grid[0][0][0]);
        maze.setGoalPosition(grid[0][row-1][col-1]);
        current = maze.getStartPosition();
        stack.push(current);
        //current.setIsWall(false);

        while(!stack.isEmpty()){
            while( (next = getRandomOption( )) == null && !stack.isEmpty()){
                current = stack.pop();
            }
            if( next == null) break;

            next.setIsWall(false);
            createPassage();
            current = next;
            stack.push(current);
            if(maze.getGoalPosition() == null && (current.getDepthIndex()==depth-1|| current.getRowIndex() == row-1 || current.getColumnIndex() == col-1 ||current.getDepthIndex()==0||current.getRowIndex()==0|| current.getColumnIndex() == 0)){
               // maze.setGoalPosition(current);
            }
        }
        Position3D tmp=new Position3D(0,row-1,col-2);
        tmp.setIsWall(false);
        maze.setPositionState(tmp);
        tmp.setPosition(0,row-2,col-2);
        tmp.setIsWall(false);
        maze.setPositionState(tmp);
        return maze;
    }

    /**
     *  Create a simple defalut maze with one solving path.
     * @param maze Maze that has number of rows or columns smaller than 4.
     */
    private void createDefaultMaze(Maze3D maze){
        Position3D[][][] grid = maze.getGrid();
        for (int k=0;k<maze.getRow();k++)
            for(int i=0;i< maze.getCol();i++){
            grid[0][k][i].setIsWall(false);
        }
        maze.setStartPosition(grid[0][0][0]);
        maze.setGoalPosition(grid[0][maze.getRow()-1][maze.getCol()-1]);
    }

    /**
     * Create a passage between to cells by breaking the wall in the cell between them.
     */
    private void createPassage(){
        int x = (current.getRowIndex() + next.getRowIndex())/2;
        int y = (current.getColumnIndex() + next.getColumnIndex())/2;
        int z = (current.getDepthIndex()+next.getDepthIndex())/2;
        grid[current.getDepthIndex()][current.getRowIndex()][current.getColumnIndex()].setIsWall(false);
        grid[z][x][y].setIsWall(false);
    }

    /**
     * This function check all the possible moves forward from a cell. possible moves would be to cells that which are walls and
     * inside the bounds of the maze.
     *
     * @return if there is one or more possible moves than randomly return one of them, else return null.
     */

    private Position3D getRandomOption( ){
        ArrayList<Position3D> neighbours = new ArrayList<Position3D>();
        int col2 = current.getColumnIndex();
        int row2 = current.getRowIndex();
        int depth2 =current.getDepthIndex();

        if(depth2==depth-2&& row2==row-2&&col2==col-2) {
            grid[depth2+1][row2][col2].setIsWall(false);
        }

        if (depth2 > 1 && grid[depth2-2][row2][col2].isWall()) {
            neighbours.add(grid[depth2-2][row2][col2]);
        } // check outer option

        if (row2 > 1 && grid[depth2][row2 - 2][col2].isWall()) {
            neighbours.add(grid[depth2][row2 - 2][col2]);
        } // check top option

        if (col2 > 1 && grid[depth2][row2][col2 - 2].isWall()) {
            neighbours.add(grid[depth2][row2][col2 - 2]);
        } // check left option

        if(depth2 < depth - 2 && grid[depth2 + 2][row2][col2].isWall()) {
            neighbours.add(grid[depth2 + 2][row2][col2]);
        }//check inner

        if (row2 < row - 2 && grid[depth2][row2 + 2][col2].isWall()) {
            neighbours.add(grid[depth2][row2 + 2][col2]);
        } // check bottom option

        if (col2 < col - 2 && grid[depth2][row2][col2 + 2].isWall()) {
            neighbours.add(grid[depth2][row2][col2 + 2]);
        } // check right border




        if( neighbours.size() > 0){
            return neighbours.get(rand.nextInt(neighbours.size()));
        }
        return null;
    }

}
