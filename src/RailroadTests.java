import org.junit.Before;
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

        Railroad[] tempArr = Railroad.createProperties(new String[] {"Reading", "Short", "Pennsylvania", "B&O"}, 1,
                1, new double[] {0, .25, .5, 1, 2});
        RLRD1 = tempArr[0];
        RLRD2 = tempArr[1];
        RLRD3 = tempArr[2];
        RLRD4 = tempArr[3];
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
        RLRD1.correctSetStatus();
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(0, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        P1.addPropertyList(RLRD2);
        RLRD2.setOwner(P1);
        RLRD2.correctSetStatus();
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        P2.addPropertyList(RLRD3);
        RLRD3.setOwner(P2);
        RLRD3.correctSetStatus();
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(1, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        P1.removePropertyList(RLRD2);
        RLRD2.setOwner(null);
        P2.addPropertyList(RLRD2);
        RLRD2.setOwner(P2);
        RLRD2.correctSetStatus();
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(2, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        P2.addPropertyList(RLRD4);
        RLRD4.setOwner(P2);
        RLRD4.correctSetStatus();
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(3, RLRD2.getRailroadsOwned());
        assertEquals(3, RLRD3.getRailroadsOwned());
        assertEquals(3, RLRD4.getRailroadsOwned());
    }

    @Test
    public void testUpdateOwnerRent() {
        assertEquals(0, RLRD1.getRent(), 0);
        assertEquals(0, RLRD2.getRent(), 0);
        assertEquals(0, RLRD3.getRent(), 0);
        assertEquals(0, RLRD4.getRent(), 0);

        P1.addPropertyList(RLRD1);
        RLRD1.setOwner(P1);
        RLRD1.updateOwnerRent(false);
        assertEquals(.25, RLRD1.getRent(), 0);
        assertEquals(0, RLRD2.getRent(), 0);
        assertEquals(0, RLRD3.getRent(), 0);
        assertEquals(0, RLRD4.getRent(), 0);

        P1.addPropertyList(RLRD2);
        RLRD2.setOwner(P1);
        RLRD2.updateOwnerRent(false);
        assertEquals(.5, RLRD1.getRent(), 0);
        assertEquals(.5, RLRD2.getRent(), 0);
        assertEquals(0, RLRD3.getRent(), 0);
        assertEquals(0, RLRD4.getRent(), 0);

        P2.addPropertyList(RLRD3);
        RLRD3.setOwner(P2);
        RLRD3.updateOwnerRent(false);
        assertEquals(.5, RLRD1.getRent(), 0);
        assertEquals(.5, RLRD2.getRent(), 0);
        assertEquals(.25, RLRD3.getRent(), 0);
        assertEquals(0, RLRD4.getRent(), 0);

        P1.removePropertyList(RLRD2);
        RLRD2.setOwner(null);
        P2.addPropertyList(RLRD2);
        RLRD2.setOwner(P2);
        RLRD2.updateOwnerRent(false);
        assertEquals(.25, RLRD1.getRent(), 0);
        assertEquals(.5, RLRD2.getRent(), 0);
        assertEquals(.5, RLRD3.getRent(), 0);
        assertEquals(0, RLRD4.getRent(), 0);

        P2.addPropertyList(RLRD4);
        RLRD4.setOwner(P2);
        RLRD4.updateOwnerRent(false);
        assertEquals(.25, RLRD1.getRent(), 0);
        assertEquals(1, RLRD2.getRent(), 0);
        assertEquals(1, RLRD3.getRent(), 0);
        assertEquals(1, RLRD4.getRent(), 0);
    }

    // TODO: testing
    //  - priority 4
    //  - double check that changeOwnership also correctly updates rent
    @Test
    public void testChangeOwnership() {
        // Case 1: Unowned to owned, changing one property status
        RLRD1.changeOwnership(P1);
        assertEquals(P1, RLRD1.getOwner());
        assertNull(RLRD2.getOwner());
        assertNull(RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(0, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 2: Owned to different owner, not changing property status
        RLRD1.changeOwnership(P2);
        assertEquals(P2, RLRD1.getOwner());
        assertNull(RLRD2.getOwner());
        assertNull(RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(0, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 3: Unowned to owned, not changing first property's status
        RLRD2.changeOwnership(P1);
        assertEquals(P2, RLRD1.getOwner());
        assertEquals(P1, RLRD2.getOwner());
        assertNull(RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(1, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 4: Unowned to owned, changing first property's status
        RLRD3.changeOwnership(P1);
        assertEquals(P2, RLRD1.getOwner());
        assertEquals(P1, RLRD2.getOwner());
        assertEquals(P1, RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(1, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(2, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 4: Owned to different owner, changing property status of two
        RLRD2.changeOwnership(P2);
        assertEquals(P2, RLRD1.getOwner());
        assertEquals(P2, RLRD2.getOwner());
        assertEquals(P1, RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(.5, RLRD1.getRent(), 0);
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(1, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 5: Owned to unowned, not affecting other properties
        RLRD3.changeOwnership(null);
        assertEquals(P2, RLRD1.getOwner());
        assertEquals(P2, RLRD2.getOwner());
        assertNull(RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(2, RLRD1.getRailroadsOwned());
        assertEquals(2, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());

        // Case 6: Owned to unowned, affecting other properties
        RLRD1.changeOwnership(null);
        assertNull(RLRD1.getOwner());
        assertEquals(P2, RLRD2.getOwner());
        assertNull(RLRD3.getOwner());
        assertNull(RLRD4.getOwner());
        assertEquals(0, RLRD1.getRailroadsOwned());
        assertEquals(1, RLRD2.getRailroadsOwned());
        assertEquals(0, RLRD3.getRailroadsOwned());
        assertEquals(0, RLRD4.getRailroadsOwned());
    }

    @Test
    public void testUpdateBuildings() {
        String errMsg = "";
        try {
            RLRD1.updateBuildings(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings cannot be bought on this Property type.", errMsg);
        errMsg = "";
        try {
            RLRD2.updateBuildings(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings cannot be bought on this Property type.", errMsg);
    }

    @Test
    public void testChangeMortgageStatus() {
        String errMsg = "";
        try {
            RLRD1.changeMortgageStatus(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Property is not owned.", errMsg);
        P1.addPropertyList(RLRD1);
        RLRD1.setOwner(P1);
        assertFalse(RLRD1.changeMortgageStatus(false));
        assertFalse(RLRD1.isMortgaged());
        assertTrue(RLRD1.changeMortgageStatus(true));
        assertTrue(RLRD1.isMortgaged());
        assertFalse(RLRD1.changeMortgageStatus(true));
        assertTrue(RLRD1.isMortgaged());
    }
}
