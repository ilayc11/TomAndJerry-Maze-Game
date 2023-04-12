package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    BestFirstSearch bfs = new BestFirstSearch();
    @Test
    void solve() {

        assertEquals(null,bfs.solve(null));

    }

    @Test
    void getName() {
        assertEquals("Best First Search",bfs.getName());
    }
}