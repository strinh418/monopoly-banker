import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Method;

public class PropertyTests {
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

    @BeforeClass
    public static void setUp() {
        /** Players being tested with. */
        Player p1 = new Player(5, "player1", 1);
        Player p2 = new Player(5, "player2", 2);

        /** The following are ColorProperty cases. */
        // Case 1: Single property in a set, unowned.
        RED1 = new ColorProperty("red1", Property.typeEnum.RED, 1, 1.1,
                1.5, new double[] {.18, .9, 2.5, 7, 8.75, 10.5});

        // Case 2: Single property in a set, owned.
        GREEN1 = new ColorProperty("green1", Property.typeEnum.GREEN, 1, 1.5,
                2, new double[] {.26, 1.3, 3.9, 9, 11, 12.75});
        p1.addPropertyList(GREEN1);
        GREEN1.setOwner(p1);

        // Case 3: Multiple properties in a set, all owned by different players.
        YELLOW1 = new ColorProperty("yellow1", Property.typeEnum.YELLOW, 1, 1.4,
                1.5, new double[] {.24, 1.2, 3.6, 8.5, 10.25, 12});
        YELLOW2 = new ColorProperty("yellow2", Property.typeEnum.YELLOW, 1, 1.3,
                1.5, new double[] {.22, 1.1, 3.3, 8, 9.75, 11.5});
        YELLOW3 = new ColorProperty("yellow3", Property.typeEnum.YELLOW, 1, 1.3,
                1.5, new double[] {.22, 1.1, 3.3, 8, 9.75, 11.5});
        p1.addPropertyList(YELLOW1);
        YELLOW1.setOwner(p1);
        p1.addPropertyList(YELLOW2);
        YELLOW2.setOwner(p1);
        p2.addPropertyList(YELLOW3);
        YELLOW3.setOwner(p2);

        // Case 4: Multiple properties in a set, some owned by one player, remaining unowned.
        PURPLE1 = new ColorProperty("purple1", Property.typeEnum.PURPLE, 1, .7,
                1, new double[] {.1, .5, 1.5, 4.5, 6.25, 7.5});
        PURPLE2 = new ColorProperty("purple2", Property.typeEnum.PURPLE, 1, .7,
                1, new double[] {.1, .5, 1.5, 4.5, 6.25, 7.5});
        PURPLE3 = new ColorProperty("purple3", Property.typeEnum.PURPLE, 1, .8,
                1, new double[] {.12, .6, 1.8, 5, 7, 9});
        p1.addPropertyList(PURPLE1);
        PURPLE1.setOwner(p1);
        p1.addPropertyList(PURPLE2);
        PURPLE2.setOwner(p1);

        // Case 5: Multiple properties in a set, some owned by different players, remaining unowned.
        ORANGE1 = new ColorProperty("orange1", Property.typeEnum.ORANGE, 1, .9,
                1, new double[] {.14, .7, 2, 5.5, 7.5, 9.5});
        ORANGE2 = new ColorProperty("orange2", Property.typeEnum.ORANGE, 1, .9,
                1, new double[] {.14, .7, 2, 5.5, 7.5, 9.5});
        ORANGE3 = new ColorProperty("orange3", Property.typeEnum.ORANGE, 1, 1,
                1, new double[] {.16, .8, 2.2, 6, 8, 10});
        p1.addPropertyList(ORANGE1);
        ORANGE1.setOwner(p1);
        p2.addPropertyList(ORANGE2);
        ORANGE2.setOwner(p2);

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
        p2.addPropertyList(BROWN1);
        BROWN1.setOwner(p2);
        p2.addPropertyList(BROWN2);
        BROWN2.setOwner(p2);
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
        try {
            Method test = Property.class.getDeclaredMethod("checkOwnFullSet");
            test.setAccessible(true);
            assertFalse((Boolean) test.invoke(RED1));
            assertTrue((Boolean) test.invoke(GREEN1));
            assertFalse((Boolean) test.invoke(YELLOW1));
            assertFalse((Boolean) test.invoke(YELLOW2));
            assertFalse((Boolean) test.invoke(YELLOW3));
            assertFalse((Boolean) test.invoke(PURPLE1));
            assertFalse((Boolean) test.invoke(PURPLE2));
            assertFalse((Boolean) test.invoke(PURPLE3));
            assertFalse((Boolean) test.invoke(ORANGE1));
            assertFalse((Boolean) test.invoke(ORANGE2));
            assertFalse((Boolean) test.invoke(ORANGE3));
            assertFalse((Boolean) test.invoke(BLUE1));
            assertFalse((Boolean) test.invoke(BLUE2));
            assertFalse((Boolean) test.invoke(BLUE3));
            assertTrue((Boolean) test.invoke(BROWN1));
            assertTrue((Boolean) test.invoke(BROWN2));

        } catch (ReflectiveOperationException ignored) {
            assertTrue("Problem with reflecting the method.", false);
        }

    }
}
