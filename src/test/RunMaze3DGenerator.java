package test;

import algorithms.maze3D.*;


public class RunMaze3DGenerator {
    public static void main(String[] args) {
        testMazeGenerator(new MyMaze3DGenerator());
    }
    private static void testMazeGenerator(IMaze3DGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s", mazeGenerator.measureAlgorithmTimeMillis(400/*depth*/,400/*rows*/, 400/*columns*/)));
        // generate another maze
        Maze3D maze = mazeGenerator.generate(400/*Depth*/,400/*rows*/, 400/*columns*/);

        // prints the maze
        maze.print();

        // get the maze entrance
        Position3D startPosition = maze.getStartPosition();

        // print the position
        System.out.println(String.format("Start Position: %s", startPosition)); // format "{depth,row,column}"

        // prints the maze exit position
        System.out.println(String.format("Goal Position: %s", maze.getGoalPosition()));
    }

}
