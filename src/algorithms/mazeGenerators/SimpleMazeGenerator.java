package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    static Random rand = new Random();
    public SimpleMazeGenerator() {}

    @Override
    public Maze generate(int row, int col){
        /*
        In this method, we generate simple maze easily by first creating maze
        full of walls, then each row will be or full of walls or full of paths.
        Finally, we're randomly creating single path in each row that represents walls row.
         */
        if( row < 1 || col < 1){
            return null;
        }
        Maze maze = new Maze(row, col);
        /*
         generate maze that from the first row, each row will look like that:
         {path,path,path,path....wall}
         {wall,wall,wall,wall...wall}
         {path,path,path,path....wall}
         {wall,wall,wall,wall...wall}
         note : still doesn't count for starting position and ending position.
         */
        for(int i = 0; i < row; i++)
            for(int j = 0; j< col; j++) {
                if (i % 2 == 0)
                    maze.setWallState(new Position(i, j), false);
                else
                    maze.setWallState(new Position(i,j), true);
            }
        /*
        Generating starting & ending position for the maze
         */
        Position start = new Position(0,0);
        Position end = new Position(row -1,col -1);
        maze.setStartPosition(start);
        maze.setGoalPosition(end);
        /*
        Now, the algorithm creates single path in each row of walls :
         */
        for(int i = 0; i < row; i++)
            if(i % 2 ==1) { // if we in walls row, create random path
                int r = getRandomNumberInRange(col -1);
                maze.setWallState(new Position(i, r), false);
            }
        return maze;
    }

    private static int getRandomNumberInRange(int colNum) {
        /*
        Private method to generate number in the range of 0 to the number of columns
        in the maze. The idea is to help build simple & random mazes that each time
        we generate maze, we get different path in the maze to move, thus we cant
        generate easily lots of different mazes.
         */
        return rand.nextInt(colNum + 1);
    }
}



