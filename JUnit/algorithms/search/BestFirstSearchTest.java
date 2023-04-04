package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

public class BestFirstSearchTest {
    @Test
    void TestMaze() {
        MyMazeGenerator maze=new MyMazeGenerator();
        maze.generate(100,100).print();
    }
}
