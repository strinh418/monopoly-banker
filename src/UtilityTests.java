import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class UtilityTests {
    public static Utility UTILITY1;
    public static Utility UTILITY2;
    public static Player P1;
    public static Player P2;

    @Before
    public void setUp() {
        P1 = new Player(5, "player1");
        P2 = new Player(5, "player2");
        Utility[] utilityList = Utility.createProperties(new String[] {"utility1", "utility2"}, 2,
                1, new double[] {0, 4, 10});
        UTILITY1 = utilityList[0];
        UTILITY2 = utilityList[1];
    }

    @Test
    public void testSetUp() {
        // TODO: Test multiplier list
        assertEquals(2, Property.TYPESETS.get("utility").size());
        assertFalse(UTILITY1.isOwned());
        assertFalse(UTILITY2.isOwned());
        assertFalse(UTILITY1.isMortgaged());
        assertFalse(UTILITY2.isMortgaged());
        assertEquals(1, UTILITY1.getMortgageValue(), 0);
        assertEquals(1, UTILITY2.getMortgageValue(), 0);
        assertEquals(2, UTILITY1.getCost(), 0);
        assertEquals(2, UTILITY2.getCost(), 0);
        assertEquals(0, UTILITY1.getRent(), 0);
        assertEquals(0, UTILITY2.getRent(), 0);
    }

    @Test
    public void testCorrectUtilitiesOwned() {
        try {
            Method test = Utility.class.getDeclaredMethod("correctUtilitiesOwned");
            test.setAccessible(true);
            assertEquals(0, UTILITY1.getUtilitiesOwned());
            assertEquals(0, UTILITY2.getUtilitiesOwned());
            P1.addPropertyList(UTILITY1);
            UTILITY1.setOwner(P1);
            assertEquals(false, test.invoke(UTILITY1));
            assertEquals(true, test.invoke(UTILITY2));
            assertEquals(1, UTILITY1.getUtilitiesOwned());
            assertEquals(0, UTILITY2.getUtilitiesOwned());
            P2.addPropertyList(UTILITY2);
            UTILITY2.setOwner(P2);
            assertEquals(true, test.invoke(UTILITY1));
            assertEquals(false, test.invoke(UTILITY2));
            assertEquals(1, UTILITY1.getUtilitiesOwned());
            assertEquals(1, UTILITY2.getUtilitiesOwned());
            P2.removePropertyList(UTILITY2);
            UTILITY2.setOwner(null);
            P1.addPropertyList(UTILITY2);
            UTILITY2.setOwner(P1);
            assertEquals(false, test.invoke(UTILITY1));
            assertEquals(false, test.invoke(UTILITY2));
            assertEquals(2, UTILITY1.getUtilitiesOwned());
            assertEquals(2, UTILITY2.getUtilitiesOwned());
        } catch (ReflectiveOperationException ignored) {
            assertTrue("Problem with reflecting the method.", false);
        }
    }

    @Test
    public void testCorrectSetStatus() {
        assertEquals(0, UTILITY1.getUtilitiesOwned());
        assertEquals(0, UTILITY2.getUtilitiesOwned());
        P1.addPropertyList(UTILITY1);
        UTILITY1.setOwner(P1);
        UTILITY1.correctSetStatus();
        assertEquals(1, UTILITY1.getUtilitiesOwned());
        assertEquals(0, UTILITY2.getUtilitiesOwned());

        P2.addPropertyList(UTILITY2);
        UTILITY2.setOwner(P2);
        UTILITY2.correctSetStatus();
        assertEquals(1, UTILITY1.getUtilitiesOwned());
        assertEquals(1, UTILITY2.getUtilitiesOwned());

        P2.removePropertyList(UTILITY2);
        UTILITY2.setOwner(null);
        P1.addPropertyList(UTILITY2);
        UTILITY2.setOwner(P1);
        UTILITY2.correctSetStatus();
        assertEquals(2, UTILITY1.getUtilitiesOwned());
        assertEquals(2, UTILITY2.getUtilitiesOwned());
    }

    @Test
    public void testUpdateOwnerRent() {
        assertEquals(0, UTILITY1.getRent(), 0);
        assertEquals(0, UTILITY2.getRent(), 0);

        P1.addPropertyList(UTILITY1);
        UTILITY1.setOwner(P1);
        UTILITY1.updateOwnerRent(false);
        assertEquals(4, UTILITY1.getRent(), 0);
        assertEquals(0, UTILITY2.getRent(), 0);

        P2.addPropertyList(UTILITY2);
        UTILITY2.setOwner(P2);
        UTILITY2.updateOwnerRent(false);
        assertEquals(4, UTILITY1.getRent(), 0);
        assertEquals(4, UTILITY2.getRent(), 0);
        P2.removePropertyList(UTILITY2);
        UTILITY2.setOwner(null);
        P1.addPropertyList(UTILITY2);
        UTILITY2.setOwner(P1);
        UTILITY2.updateOwnerRent(false);
        assertEquals(10, UTILITY1.getRent(), 0);
        assertEquals(10, UTILITY2.getRent(), 0);
    }

    @Test
    public void testChangeOwnership() {
        assertEquals(0, UTILITY1.getRent(), 0);
        assertEquals(0, UTILITY2.getRent(), 0);

        UTILITY1.changeOwnership(P1);
        assertEquals(4, UTILITY1.getRent(), 0);
        assertEquals(0, UTILITY2.getRent(), 0);
        assertEquals(UTILITY1.getOwner(), P1);
        assertNull(UTILITY2.getOwner());
        assertEquals(1, UTILITY1.getUtilitiesOwned());
        assertEquals(0, UTILITY2.getUtilitiesOwned());

        UTILITY2.changeOwnership(P2);
        assertEquals(4, UTILITY1.getRent(), 0);
        assertEquals(4, UTILITY2.getRent(), 0);
        assertEquals(UTILITY1.getOwner(), P1);
        assertEquals(UTILITY2.getOwner(), P2);
        assertEquals(1, UTILITY1.getUtilitiesOwned());
        assertEquals(1, UTILITY2.getUtilitiesOwned());

        UTILITY2.changeOwnership(P1);
        assertEquals(10, UTILITY1.getRent(), 0);
        assertEquals(10, UTILITY2.getRent(), 0);
        assertEquals(UTILITY1.getOwner(), P1);
        assertEquals(UTILITY2.getOwner(), P1);
        assertEquals(2, UTILITY1.getUtilitiesOwned());
        assertEquals(2, UTILITY2.getUtilitiesOwned());

        UTILITY1.changeOwnership(null);
        assertEquals(0, UTILITY1.getRent(), 0);
        assertEquals(4, UTILITY2.getRent(), 0);
        assertNull(UTILITY1.getOwner());
        assertEquals(UTILITY2.getOwner(), P1);
        assertEquals(0, UTILITY1.getUtilitiesOwned());
        assertEquals(1, UTILITY2.getUtilitiesOwned());
    }

    @Test
    public void testUpdateBuildings() {
        String errMsg = "";
        try {
            UTILITY1.updateBuildings(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings cannot be bought on this Property type.", errMsg);
        errMsg = "";
        try {
            UTILITY2.updateBuildings(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings cannot be bought on this Property type.", errMsg);
    }

    @Test
    public void testChangeMortgageStatus() {
        String errMsg = "";
        try {
            UTILITY1.changeMortgageStatus(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Property is not owned.", errMsg);
        P1.addPropertyList(UTILITY1);
        UTILITY1.setOwner(P1);
        assertFalse(UTILITY1.changeMortgageStatus(false));
        assertFalse(UTILITY1.isMortgaged());
        assertTrue(UTILITY1.changeMortgageStatus(true));
        assertTrue(UTILITY1.isMortgaged());
        assertFalse(UTILITY1.changeMortgageStatus(true));
        assertTrue(UTILITY1.isMortgaged());
    }
}
