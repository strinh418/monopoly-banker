import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class PlayerTests {
    public static Player P1;
    public static Player P2;
    public static Player P3;
    public static Property TESTPROPERTY;

    @Before
    public void setUp() {
        P1 = new Player(10, "player1");
        P2 = new Player(10, "player2");
        P3 = new Player(1, "player3");
        TESTPROPERTY = ColorProperty.createProperties("red", new String[] {"red1"}, new double[] {2},
                new double[] {1}, 1, new double[][] {{.18, .9, 2.5, 7, 8.75, 10.5}})[0];
    }

    @Test
    public void testSetUp() {
        assertEquals(10, P1.getMoney(), 0);
        assertEquals(10, P2.getMoney(), 0);
        assertEquals(10, P3.getMoney(), 0);
        assertEquals("player1", P1.getName());
        assertEquals("player2", P2.getName());
        assertEquals("player3", P2.getName());
        assertEquals(0, P1.getPlayerID());
        assertEquals(1, P2.getPlayerID());
        assertEquals(2, P3.getPlayerID());
    }

    @Test
    public void testUpdateMoney() {
        // Case 1: Insufficient funds to complete
        String errMsg = "";
        try {
            P1.updateMoney(-11);
        } catch(MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Insufficient funds to complete this transaction.", errMsg);

        // Case 2: Successfully add money.
        assertEquals(14.5, P1.updateMoney(4.5), 0);

        // Case 3: Successfully subtract money.
        assertEquals(0, P1.updateMoney(-14.5), 0);
    }

    @Test
    public void testPayPlayer() {
        // Case 1: Insufficient funds to pay other player.
        String errMsg = "";
        try {
            P1.payPlayer(P2, 10.1);
        } catch(MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Insufficient funds to complete this transaction.", errMsg);

        // Case 2: Sucessfully pay some money to other player.
        P1.payPlayer(P2, 3.5);
        assertEquals(6.5, P1.getMoney(), 0);
        assertEquals(13.5, P2.getMoney(), 0);

        // Case 3: Successfully pay all money to other player.
        P2.payPlayer(P1, 13.5);
        assertEquals(20, P1.getMoney(), 0);
        assertEquals(0, P2.getMoney(), 0);
    }
    // TODO: Write tests for addPropertyList() and removePropertyList() ?

    @Test
    public void testBuyProperty() {
        String errMsg = "";

        // Case 1: Insufficient funds to buy the property.
        try {
            P3.buyProperty(TESTPROPERTY);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Insufficient funds to complete this transaction.", errMsg);
        errMsg = "";

        // Case 2: Successfully buy the property.
        P1.buyProperty(TESTPROPERTY);
        assertEquals(8, P1.getMoney(), 0);
        assertEquals(TESTPROPERTY.getOwner(), P1);

        // Case 3: Unable to buy property that is already owned by someone else.
        try {
            P2.buyProperty(TESTPROPERTY);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("The property is already owned.", errMsg);
        errMsg = "";

        // Case 4: Unable to buy property that is already owned by the same player.
        try {
            P1.buyProperty(TESTPROPERTY);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("The property is already owned.", errMsg);
    }
}
