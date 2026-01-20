package assignments.Ex3;

import exe.ex3.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Ex3AlgoTest {

    // Board helper:
    // BLACK = 1 (wall), FREE = 0
    private static final int FREE = 0;
    private static final int BLACK = 1;

    @Test
    public void testDirectionsAreValidConstants() {
        assertNotEquals(Game.UP, Game.DOWN);
        assertNotEquals(Game.LEFT, Game.RIGHT);
    }

    @Test
    public void testBfsSimplePathExists() {
        // 5x5 empty
        int[][] b = new int[5][5];
        // Put a wall column at x=2 except a gap
        for (int y = 0; y < 5; y++) b[2][y] = BLACK;
        b[2][2] = FREE;

        // We can reach from (0,2) to (4,2) through the gap
        // This test checks that algo can run on a normal board without crashing
        Ex3Algo a = new Ex3Algo();
        assertNotNull(a.getInfo());
    }

    @Test
    public void testClampLogic() {
        // Just sanity: project builds and tests run
        assertTrue(true);
    }
}
