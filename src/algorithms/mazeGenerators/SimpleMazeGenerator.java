package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    Random rand = new Random();
    public SimpleMazeGenerator() {}

    @Override
    public Maze generate(int row, int col){
        if( row < 1 || col < 1){
            return null;
        }
        Maze maze = new Maze(row,col);

        // initialize the maze with walls :

        if( row < 4 || col < 4){
            createDefaultMaze(maze);
            return maze;
        }

        Position start = new Position(0, rand.nextInt(col));
        maze.setStartPosition(start);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(rand.nextInt(2) == 0) maze.setWallState(new Position(i,j),false);
            }
        }

        Position end = new Position(row -1, rand.nextInt(col));
        maze.setGoalPosition(end);

        createPassage(start, end, maze);
        return maze;

    }

    private void createDefaultMaze(Maze maze){

        for(int i=0;i< maze.getCol();i++){
            maze.setWallState(new Position(0, i), false);
        }
        maze.setStartPosition(new Position(0,0));
        maze.setGoalPosition(new Position(0, maze.getCol() -1));
    }

    private void createPassage(Position start, Position goal, Maze maze){
        Position initial,target;
        if(start.getRowIndex() < goal.getRowIndex()){
            initial = start;
            target = goal;
        }
        else {
            target = start;
            initial = goal;
        }

        for(int i = initial.getRowIndex()+1 ;i<= target.getRowIndex() ;i++){
            maze.setWallState(new Position(i, initial.getColumnIndex()), false);
        }

        int targetRow = target.getRowIndex();
        if(initial.getColumnIndex() > target.getColumnIndex()){
            Position temp = target;
            target = initial;
            initial = temp;

        }

        for(int i=initial.getColumnIndex()+1;i<target.getColumnIndex();i++){
            maze.setWallState(new Position(targetRow, i), false);
        }

    }
}



