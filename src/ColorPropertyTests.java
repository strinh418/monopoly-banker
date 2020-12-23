import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.util.Set;

public class ColorPropertyTests {
    public static ColorProperty RED1;
    public static ColorProperty GREEN1;
    public static ColorProperty YELLOW1;
    public static ColorProperty YELLOW2;
    public static ColorProperty YELLOW3;
    public static ColorProperty PURPLE1;
    public static ColorProperty PURPLE2;
    public static ColorProperty PURPLE3;
    public static ColorProperty ORANGE1;
    public static ColorProperty ORANGE2;
    public static ColorProperty ORANGE3;
    public static ColorProperty BLUE1;
    public static ColorProperty BLUE2;
    public static ColorProperty BLUE3;
    public static ColorProperty BROWN1;
    public static ColorProperty BROWN2;
    public static Player P1;
    public static Player P2;

    @Before
    public void setUp() {
        /** Clear the property sets before initializing. */
        for (Set s : Property.TYPESETS.values()) {
            s.clear();
        }
        /** Players being tested with. */
        P1 = new Player(5, "player1");
        P2 = new Player(5, "player2");

        /** The following are ColorProperty cases. */
        // Case 1: Single property in a set, unowned.
        RED1 = new ColorProperty("red1", Property.typeEnum.RED, 1, 1.1,
                1.5, new double[] {.18, .9, 2.5, 7, 8.75, 10.5});

        // Case 2: Single property in a set, owned.
        GREEN1 = new ColorProperty("green1", Property.typeEnum.GREEN, 1, 1.5,
                2, new double[] {.26, 1.3, 3.9, 9, 11, 12.75});
        P1.addPropertyList(GREEN1);
        GREEN1.setOwner(P1);

        // Case 3: Multiple properties in a set, all owned by different players.
        YELLOW1 = new ColorProperty("yellow1", Property.typeEnum.YELLOW, 1, 1.4,
                1.5, new double[] {.24, 1.2, 3.6, 8.5, 10.25, 12});
        YELLOW2 = new ColorProperty("yellow2", Property.typeEnum.YELLOW, 1, 1.3,
                1.5, new double[] {.22, 1.1, 3.3, 8, 9.75, 11.5});
        YELLOW3 = new ColorProperty("yellow3", Property.typeEnum.YELLOW, 1, 1.3,
                1.5, new double[] {.22, 1.1, 3.3, 8, 9.75, 11.5});
        P1.addPropertyList(YELLOW1);
        YELLOW1.setOwner(P1);
        P1.addPropertyList(YELLOW2);
        YELLOW2.setOwner(P1);
        P2.addPropertyList(YELLOW3);
        YELLOW3.setOwner(P2);

        // Case 4: Multiple properties in a set, some owned by one player, remaining unowned.
        PURPLE1 = new ColorProperty("purple1", Property.typeEnum.PURPLE, 1, .7,
                1, new double[] {.1, .5, 1.5, 4.5, 6.25, 7.5});
        PURPLE2 = new ColorProperty("purple2", Property.typeEnum.PURPLE, 1, .7,
                1, new double[] {.1, .5, 1.5, 4.5, 6.25, 7.5});
        PURPLE3 = new ColorProperty("purple3", Property.typeEnum.PURPLE, 1, .8,
                1, new double[] {.12, .6, 1.8, 5, 7, 9});
        P1.addPropertyList(PURPLE1);
        PURPLE1.setOwner(P1);
        P1.addPropertyList(PURPLE2);
        PURPLE2.setOwner(P1);

        // Case 5: Multiple properties in a set, some owned by different players, remaining unowned.
        ORANGE1 = new ColorProperty("orange1", Property.typeEnum.ORANGE, 1, .9,
                1, new double[] {.14, .7, 2, 5.5, 7.5, 9.5});
        ORANGE2 = new ColorProperty("orange2", Property.typeEnum.ORANGE, 1, .9,
                1, new double[] {.14, .7, 2, 5.5, 7.5, 9.5});
        ORANGE3 = new ColorProperty("orange3", Property.typeEnum.ORANGE, 1, 1,
                1, new double[] {.16, .8, 2.2, 6, 8, 10});
        P1.addPropertyList(ORANGE1);
        ORANGE1.setOwner(P1);
        P2.addPropertyList(ORANGE2);
        ORANGE2.setOwner(P2);

        // Case 6: Multiple properties in a set, all unowned.
        BLUE1 = new ColorProperty("blue1", Property.typeEnum.LIGHTBLUE, 1, .5,
                1, new double[] {.06, .3, .9, 2.7, 4, 5.5});
        BLUE2 = new ColorProperty("blue2", Property.typeEnum.LIGHTBLUE, 1, .5,
                1, new double[] {.06, .3, .9, 2.7, 4, 5.5});
        BLUE3 = new ColorProperty("blue3", Property.typeEnum.LIGHTBLUE, 1, .6,
                1, new double[] {.08, .4, 1, 3, 4.5, 6});

        // Case 7: Multiple properties all owned by the same player.
        BROWN1 = new ColorProperty("brown1", Property.typeEnum.BROWN, 1, .3,
                .5, new double[] {.02, .1, .3, .9, 1.6, 2.5});
        BROWN2 = new ColorProperty("brown2", Property.typeEnum.BROWN, 1, .3,
                .5, new double[] {.04, .2, .6, 1.8, 3.2, 4.5});
        P2.addPropertyList(BROWN1);
        BROWN1.setOwner(P2);
        P2.addPropertyList(BROWN2);
        BROWN2.setOwner(P2);
    }

    @Test
    public void testTypeSets() {
        assertEquals(1, Property.TYPESETS.get(Property.typeEnum.RED).size());
        assertEquals(1, Property.TYPESETS.get(Property.typeEnum.GREEN).size());
        assertEquals(3, Property.TYPESETS.get(Property.typeEnum.YELLOW).size());
        assertEquals(3, Property.TYPESETS.get(Property.typeEnum.PURPLE).size());
        assertEquals(3, Property.TYPESETS.get(Property.typeEnum.ORANGE).size());
        assertEquals(3, Property.TYPESETS.get(Property.typeEnum.LIGHTBLUE).size());
        assertEquals(2, Property.TYPESETS.get(Property.typeEnum.BROWN).size());
    }

    @Test
    public void testTotalOwnedSet() {
        try {
            Method test = Property.class.getDeclaredMethod("totalOwnedSet");
            test.setAccessible(true);
            assertEquals(0, test.invoke(RED1));
            assertEquals(1, test.invoke(GREEN1));
            assertEquals(2, test.invoke(YELLOW1));
            assertEquals(2, test.invoke(YELLOW2));
            assertEquals(1, test.invoke(YELLOW3));
            assertEquals(2, test.invoke(PURPLE1));
            assertEquals(2, test.invoke(PURPLE2));
            assertEquals(0, test.invoke(PURPLE3));
            assertEquals(1, test.invoke(ORANGE1));
            assertEquals(1, test.invoke(ORANGE2));
            assertEquals(0, test.invoke(ORANGE3));
            assertEquals(0, test.invoke(BLUE1));
            assertEquals(0, test.invoke(BLUE2));
            assertEquals(0, test.invoke(BLUE3));
            assertEquals(2, test.invoke(BROWN1));
            assertEquals(2, test.invoke(BROWN2));

        } catch (ReflectiveOperationException ignored) {
            assertTrue("Problem with reflecting the method.", false);
        }
    }

    @Test
    public void testCheckOwnFullSet() {
        assertFalse(RED1.checkOwnFullSet());
        assertTrue(GREEN1.checkOwnFullSet());
        assertFalse(YELLOW1.checkOwnFullSet());
        assertFalse(YELLOW2.checkOwnFullSet());
        assertFalse(YELLOW3.checkOwnFullSet());
        assertFalse(PURPLE1.checkOwnFullSet());
        assertFalse(PURPLE2.checkOwnFullSet());
        assertFalse(PURPLE3.checkOwnFullSet());
        assertFalse(ORANGE1.checkOwnFullSet());
        assertFalse(ORANGE2.checkOwnFullSet());
        assertFalse(ORANGE3.checkOwnFullSet());
        assertFalse(BLUE1.checkOwnFullSet());
        assertFalse(BLUE2.checkOwnFullSet());
        assertFalse(BLUE3.checkOwnFullSet());
        assertTrue(BROWN1.checkOwnFullSet());
        assertTrue(BROWN2.checkOwnFullSet());
    }

    @Test
    public void testCorrectSetStatus() {
        assertTrue(RED1.correctSetStatus());
        assertFalse(RED1.getInFullSet());

        assertFalse(GREEN1.correctSetStatus());
        assertTrue(GREEN1.getInFullSet());

        assertTrue(YELLOW1.correctSetStatus());
        assertFalse(YELLOW1.getInFullSet());
        assertTrue(YELLOW2.correctSetStatus());
        assertFalse(YELLOW2.getInFullSet());
        assertTrue(YELLOW3.correctSetStatus());
        assertFalse(YELLOW3.getInFullSet());

        assertTrue(PURPLE1.correctSetStatus());
        assertFalse(PURPLE1.getInFullSet());
        assertTrue(PURPLE2.correctSetStatus());
        assertFalse(PURPLE2.getInFullSet());
        assertTrue(PURPLE3.correctSetStatus());
        assertFalse(PURPLE3.getInFullSet());

        assertTrue(ORANGE1.correctSetStatus());
        assertFalse(ORANGE1.getInFullSet());
        assertTrue(ORANGE2.correctSetStatus());
        assertFalse(ORANGE2.getInFullSet());
        assertTrue(ORANGE3.correctSetStatus());
        assertFalse(ORANGE3.getInFullSet());

        assertTrue(BLUE1.correctSetStatus());
        assertFalse(BLUE1.getInFullSet());
        assertTrue(BLUE2.correctSetStatus());
        assertFalse(BLUE2.getInFullSet());
        assertTrue(BLUE3.correctSetStatus());

        assertFalse(BROWN1.correctSetStatus());
        assertTrue(BROWN2.correctSetStatus());
    }

    @Test
    public void testUpdateOwnerRent() {
        RED1.updateOwnerRent(false);
        assertEquals(.18, RED1.getRent(), 0);

        GREEN1.updateOwnerRent(false);
        assertEquals(.26*2, GREEN1.getRent(), 0);

        ORANGE1.updateOwnerRent(false);
        assertEquals(.14, ORANGE1.getRent(), 0);
        ORANGE2.updateOwnerRent(false);
        assertEquals(.14, ORANGE2.getRent(), 0);
        ORANGE3.updateOwnerRent(false);
        assertEquals(.16, ORANGE3.getRent(), 0);

        BROWN1.updateOwnerRent(false);
        assertEquals(.02*2, BROWN1.getRent(), 0);
        BROWN2.updateOwnerRent(false);
        assertEquals(.04*2, BROWN2.getRent(), 0);
    }

    @Test
    public void testChangeOwnership() {
        // Case 1: Unowned to owned, changing set status
        RED1.changeOwnership(P1);
        assertEquals(RED1.getOwner(), P1);
        assertTrue(RED1.getInFullSet());
        assertEquals(.18*2, RED1.getRent(), 0);

        // Case 2: Unowned to owned, not changing set status
        BLUE1.changeOwnership(P2);
        assertEquals(BLUE1.getOwner(), P2);
        assertNull(BLUE2.getOwner());
        assertNull(BLUE3.getOwner());
        assertFalse(BLUE1.getInFullSet());
        assertFalse(BLUE2.getInFullSet());
        assertFalse(BLUE3.getInFullSet());
        assertEquals(.06, BLUE1.getRent(), 0);
        assertEquals(.06, BLUE1.getRent(), 0);
        assertEquals(.08, BLUE3.getRent(), 0);

        // Case 3: Owned to different owner, changing set status from not full to full
        YELLOW3.changeOwnership(P1);
        assertEquals(YELLOW1.getOwner(), P1);
        assertEquals(YELLOW2.getOwner(), P1);
        assertEquals(YELLOW3.getOwner(), P1);
        assertTrue(YELLOW1.getInFullSet());
        assertTrue(YELLOW2.getInFullSet());
        assertTrue(YELLOW3.getInFullSet());
        assertEquals(.24*2, YELLOW1.getRent(), 0);
        assertEquals(.22*2, YELLOW2.getRent(), 0);
        assertEquals(.22*2, YELLOW3.getRent(), 0);

        // Case 4: Owned to different owner, changing set status from full to not full
        BROWN2.changeOwnership(P1);
        assertEquals(BROWN1.getOwner(), P2);
        assertEquals(BROWN2.getOwner(), P1);
        assertFalse(BROWN1.getInFullSet());
        assertFalse(BROWN2.getInFullSet());
        assertEquals(.02, BROWN1.getRent(), 0);
        assertEquals(.04, BROWN2.getRent(), 0);

        // Case 5: Owned to different owner, not changing set status
        PURPLE2.changeOwnership(P2);
        assertEquals(PURPLE1.getOwner(), P1);
        assertEquals(PURPLE2.getOwner(), P2);
        assertNull(PURPLE3.getOwner());
        assertFalse(PURPLE1.getInFullSet());
        assertFalse(PURPLE2.getInFullSet());
        assertFalse(PURPLE3.getInFullSet());
        assertEquals(.1, PURPLE1.getRent(), 0);
        assertEquals(.1, PURPLE2.getRent(), 0);
        assertEquals(.12, PURPLE3.getRent(), 0);

        // Case 6: Owned to unowned, changing set status from full to not full
        GREEN1.changeOwnership(null);
        assertNull(GREEN1.getOwner());
        assertFalse(GREEN1.getInFullSet());
        assertEquals(.26, GREEN1.getRent(), 0);

        // Case 7: Owned to unowned, not changing set status
        ORANGE1.changeOwnership(null);
        assertNull(ORANGE1.getOwner());
        assertEquals(ORANGE2.getOwner(), P2);
        assertNull(ORANGE3.getOwner());
        assertFalse(ORANGE1.getInFullSet());
        assertFalse(ORANGE2.getInFullSet());
        assertFalse(ORANGE3.getInFullSet());
        assertEquals(.14, ORANGE1.getRent(), 0);
        assertEquals(.14, ORANGE2.getRent(), 0);
        assertEquals(.16, ORANGE3.getRent(), 0);
    }

    @Test
    public void testUpdateBuildings() {
        String errMsg = "";
        // Case 1: Full set not owned, should not be able to add or subtract buildings.
        try {
            RED1.updateBuildings(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Owner does not have the full set.", errMsg);
        errMsg = "";
        try {
            RED1.updateBuildings(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Owner does not have the full set.", errMsg);
        errMsg = "";

        // Case 2: Full set owned. Not able to subtract building when 0 on the site.
        GREEN1.correctSetStatus();
        try {
            GREEN1.updateBuildings(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Number of buildings must be between 0 and 5.", errMsg);
        errMsg = "";
        GREEN1.updateBuildings(true);
        assertEquals(1, GREEN1.getNumBuildings());
        assertEquals(1.3, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(true);
        assertEquals(2, GREEN1.getNumBuildings());
        assertEquals(3.9, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(true);
        assertEquals(3, GREEN1.getNumBuildings());
        assertEquals(9, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(true);
        assertEquals(4, GREEN1.getNumBuildings());
        assertEquals(11, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(false);
        assertEquals(3, GREEN1.getNumBuildings());
        assertEquals(9, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(true);
        assertEquals(4, GREEN1.getNumBuildings());
        assertEquals(11, GREEN1.getRent(), 0);
        GREEN1.updateBuildings(true);
        assertEquals(5, GREEN1.getNumBuildings());
        assertEquals(12.75, GREEN1.getRent(), 0);

        // Case 3: Full set owned. Not able to add building when 5 on the site.
        try {
            GREEN1.updateBuildings(true);
        } catch(MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Number of buildings must be between 0 and 5.", errMsg);
        errMsg = "";

        // Case 4: Full set owned. Able to add/subtract building (but not when distribution is uneven)
        BROWN1.correctSetStatus();
        BROWN1.updateBuildings(true);
        assertEquals(1, BROWN1.getNumBuildings());
        assertEquals(.1, BROWN1.getRent(), 0);
        try {
            BROWN1.updateBuildings(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Houses must be evenly distributed amongst properties.", errMsg);
        errMsg = "";
        BROWN1.updateBuildings(false);
        assertEquals(0, BROWN1.getNumBuildings());
        assertEquals(.02, BROWN1.getRent(), 0);
        BROWN1.updateBuildings(true);
        BROWN2.updateBuildings(true);
        BROWN2.updateBuildings(true);
        assertEquals(1, BROWN1.getNumBuildings());
        assertEquals(.1, BROWN1.getRent(), 0);
        assertEquals(2, BROWN2.getNumBuildings());
        assertEquals(.6, BROWN2.getRent(), 0);
        try {
            BROWN1.updateBuildings(false);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Houses must be evenly distributed amongst properties.", errMsg);
    }

    @Test
    public void testChangeMortgageStatus() {
        // Case 1: Mortgage a property that isn't in a full set.
        YELLOW1.changeMortgageStatus(true);
        YELLOW2.changeMortgageStatus(false);
        YELLOW3.changeMortgageStatus(true);
        assertTrue(YELLOW1.isMortgaged());
        assertFalse(YELLOW2.isMortgaged());
        assertTrue(YELLOW3.isMortgaged());

        // Case 2: Unmortgage a property that isn't in a full set.
        YELLOW1.changeMortgageStatus(true);
        YELLOW2.changeMortgageStatus(true);
        YELLOW3.changeMortgageStatus(false);
        assertTrue(YELLOW1.isMortgaged());
        assertTrue(YELLOW2.isMortgaged());
        assertFalse(YELLOW3.isMortgaged());

        // Case 3: Mortgage a property that is in a full set.
        BROWN1.changeMortgageStatus(true);
        BROWN2.changeMortgageStatus(false);
        assertTrue(BROWN1.isMortgaged());
        assertFalse(BROWN2.isMortgaged());

        // Case 4: Unmortgage a property that is in a full set.
        BROWN1.changeMortgageStatus(false);
        BROWN2.changeMortgageStatus(false);
        assertFalse(BROWN1.isMortgaged());
        assertFalse(BROWN2.isMortgaged());

        String errMsg = "";
        // Case 5: Try to mortgage a property that has buildings on it.
        BROWN1.correctSetStatus();
        BROWN1.updateBuildings(true);
        try {
            BROWN1.changeMortgageStatus(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings exist on a property of this type.", errMsg);
        errMsg = "";

        // Case 6: Try to mortgage a property where buildings are on a property of that type.
        try {
            BROWN2.changeMortgageStatus(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Buildings exist on a property of this type.", errMsg);
        errMsg = "";

        // Case 7: Try to mortgage a property that is unowned.
        try {
            RED1.changeMortgageStatus(true);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Property is not owned.", errMsg);
    }
}
