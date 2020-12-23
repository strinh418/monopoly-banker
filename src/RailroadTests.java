import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

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
    public void testCorrectRailroadsOwned() {
        try {
            Method test = Railroad.class.getDeclaredMethod("correctRailroadsOwned");
            test.setAccessible(true);
            assertEquals(0, RLRD1.getRailroadsOwned());
            assertEquals(0, RLRD2.getRailroadsOwned());
            assertEquals(0, RLRD3.getRailroadsOwned());
            assertEquals(0, RLRD4.getRailroadsOwned());
            P1.addPropertyList(RLRD1);
            RLRD1.setOwner(P1);
            assertEquals(false, test.invoke(RLRD1));
            assertEquals(true, test.invoke(RLRD2));
            assertEquals(true, test.invoke(RLRD3));
            assertEquals(true, test.invoke(RLRD4));
            assertEquals(1, RLRD1.getRailroadsOwned());
            assertEquals(0, RLRD2.getRailroadsOwned());
            assertEquals(0, RLRD3.getRailroadsOwned());
            assertEquals(0, RLRD4.getRailroadsOwned());
            P1.addPropertyList(RLRD2);
            RLRD2.setOwner(P1);
            assertEquals(false, test.invoke(RLRD1));
            assertEquals(false, test.invoke(RLRD2));
            assertEquals(true, test.invoke(RLRD3));
            assertEquals(true, test.invoke(RLRD4));
            assertEquals(2, RLRD1.getRailroadsOwned());
            assertEquals(2, RLRD2.getRailroadsOwned());
            assertEquals(0, RLRD3.getRailroadsOwned());
            assertEquals(0, RLRD4.getRailroadsOwned());
            P2.addPropertyList(RLRD3);
            RLRD3.setOwner(P2);
            assertEquals(true, test.invoke(RLRD1));
            assertEquals(true, test.invoke(RLRD2));
            assertEquals(false, test.invoke(RLRD3));
            assertEquals(true, test.invoke(RLRD4));
            assertEquals(2, RLRD1.getRailroadsOwned());
            assertEquals(2, RLRD2.getRailroadsOwned());
            assertEquals(1, RLRD3.getRailroadsOwned());
            assertEquals(0, RLRD4.getRailroadsOwned());
            P1.removePropertyList(RLRD2);
            RLRD2.setOwner(null);
            P2.addPropertyList(RLRD2);
            RLRD2.setOwner(P2);
            assertEquals(false, test.invoke(RLRD1));
            assertEquals(true, test.invoke(RLRD2));
            assertEquals(false, test.invoke(RLRD3));
            assertEquals(true, test.invoke(RLRD4));
            assertEquals(1, RLRD1.getRailroadsOwned());
            assertEquals(2, RLRD2.getRailroadsOwned());
            assertEquals(2, RLRD3.getRailroadsOwned());
            assertEquals(0, RLRD4.getRailroadsOwned());
            P2.addPropertyList(RLRD4);
            RLRD4.setOwner(P2);
            assertEquals(true, test.invoke(RLRD1));
            assertEquals(false, test.invoke(RLRD2));
            assertEquals(false, test.invoke(RLRD3));
            assertEquals(false, test.invoke(RLRD4));
            assertEquals(1, RLRD1.getRailroadsOwned());
            assertEquals(3, RLRD2.getRailroadsOwned());
            assertEquals(3, RLRD3.getRailroadsOwned());
            assertEquals(3, RLRD4.getRailroadsOwned());
        } catch (ReflectiveOperationException ignored) {
            assertTrue("Problem with reflecting the method.", false);
        }
    }

    @Test
    public void testCorrectSetStatus() {
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
        P1.addPropertyList(RLRD2);
        RLRD2.setOwner(P1);
        assertFalse(RLRD1.correctSetStatus());
        assertTrue(RLRD2.correctSetStatus());
        assertTrue(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
        P2.addPropertyList(RLRD3);
        RLRD3.setOwner(P2);
        assertTrue(RLRD1.correctSetStatus());
        assertTrue(RLRD2.correctSetStatus());
        assertFalse(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(1, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
        P1.removePropertyList(RLRD2);
        RLRD2.setOwner(null);
        P2.addPropertyList(RLRD2);
        RLRD2.setOwner(P2);
        assertFalse(RLRD1.correctSetStatus());
        assertTrue(RLRD2.correctSetStatus());
        assertTrue(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(2, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
        P2.addPropertyList(RLRD4);
        RLRD4.setOwner(P2);
        assertTrue(RLRD1.correctSetStatus());
        assertFalse(RLRD2.correctSetStatus());
        assertTrue(RLRD3.correctSetStatus());
        assertTrue(RLRD4.correctSetStatus());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(3, RLRD2.getRailroadsOwned());
        assertEquals(3, RLRD3.getRailroadsOwned());
        assertEquals(3, RLRD4.getRailroadsOwned());
    }
}
