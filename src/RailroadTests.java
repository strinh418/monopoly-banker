import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RailroadTests {
    public static Railroad RLRD1;
    public static Railroad RLRD2;
    public static Railroad RLRD3;
    public static Railroad RLRD4;
    public static Player P1;
    public static Player P2;

    @Before
    public void setUp() {
        P1 = new Player(5, "player1");
        P2 = new Player(5, "player2");
        RLRD1 = new Railroad("Reading", 1, 1, new double[] {.25, .5, 1, 2});
        RLRD2 = new Railroad("Short", 1, 1, new double[] {.25, .5, 1, 2});
        RLRD3 = new Railroad("Pennsylvania", 1, 1, new double[] {.25, .5, 1, 2});
        RLRD4 = new Railroad("B&O", 1, 1, new double[] {.25, .5, 1, 2});
    }

    @Test
    public void testCorrectSetStatus() {
        assertTrue(RLRD1.correctSetStatus());
        assertTrue(RLRD2.correctSetStatus());
        assertTrue(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(0, RLRD1.getRailroadsOwned());
        assertEquals(0, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
        P1.addPropertyList(RLRD1);
        RLRD1.setOwner(P1);
        assertFalse(RLRD1.correctSetStatus());
        assertTrue(RLRD2.correctSetStatus());
        assertTrue(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(0, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
    }
}
