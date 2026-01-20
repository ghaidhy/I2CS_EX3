package assignments.Ex3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {
    @Test
    public void testDistance2D() {
        Pixel2D a = new Index2D(0,0);
        Pixel2D b = new Index2D(3,4);
        assertEquals(5.0, a.distance2D(b), 1e-9);
        assertEquals(0.0, a.distance2D(new Index2D(0,0)), 1e-9);
    }

    @Test
    public void testEquals() {
        assertEquals(new Index2D(2,7), new Index2D(2,7));
        assertNotEquals(new Index2D(2,7), new Index2D(2,8));
    }
}
