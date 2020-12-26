import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

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
        // TODO: Test multiplier and multiplier list
        assertEquals(2, Property.TYPESETS.get("utility").size());
        assertFalse(UTILITY1.isOwned());
        assertFalse(UTILITY2.isOwned());
        assertFalse(UTILITY1.isMortgaged());
        assertFalse(UTILITY2.isMortgaged());
        assertEquals(1, UTILITY1.getMortgageValue(), 0);
        assertEquals(1, UTILITY2.getMortgageValue(), 0);
        assertEquals(2, UTILITY1.getCost(), 0);
        assertEquals(2, UTILITY2.getCost(), 0);
    }
}
