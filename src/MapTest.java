package assignments.Ex3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MapTest {

    @Test
    public void testInitAndCopy() {
        Map2D m = new Map(3,2,7);
        assertEquals(3, m.getWidth());
        assertEquals(2, m.getHeight());
        assertEquals(7, m.getPixel(1,1));

        int[][] c = m.getMap();
        c[1][1] = 99;
        assertEquals(7, m.getPixel(1,1)); // deep copy
    }

    @Test
    public void testFillNonCyclic() {
        Map m = new Map(4,4,0);
        m.setCyclic(false);
        m.setPixel(1,1,5);
        m.setPixel(1,2,5);
        m.setPixel(2,1,5);
        int filled = m.fill(new Index2D(1,1), 9);
        assertEquals(3, filled);
        assertEquals(9, m.getPixel(1,1));
        assertEquals(9, m.getPixel(1,2));
        assertEquals(9, m.getPixel(2,1));
    }

    @Test
    public void testShortestPathBasic() {
        Map m = new Map(5,5,0);
        m.setCyclic(false);
        int OBS = 1;
        // add a wall line with a gap
        for(int y=0;y<5;y++) m.setPixel(2,y,OBS);
        m.setPixel(2,2,0);

        Pixel2D[] path = m.shortestPath(new Index2D(0,2), new Index2D(4,2), OBS);
        assertNotNull(path);
        assertEquals("0,2", path[0].toString());
        assertEquals("4,2", path[path.length-1].toString());
    }

    @Test
    public void testAllDistance() {
        Map m = new Map(3,3,0);
        m.setCyclic(false);
        int OBS = 1;
        m.setPixel(1,1,OBS);
        Map2D d = m.allDistance(new Index2D(0,0), OBS);
        assertEquals(0, d.getPixel(0,0));
        assertEquals(-1, d.getPixel(1,1)); // obstacle
        assertTrue(d.getPixel(2,2) >= 0);
    }
}
