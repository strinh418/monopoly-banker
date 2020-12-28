import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.Method;
import static org.junit.Assert.*;

public class PlayerTests {
    public static Player P1;
    public static Player P2;
    public static Player P3;
    public static Property RED1;
    public static Property RED2;

    @Before
    public void setUp() {
        P1 = new Player(10, "player1");
        P2 = new Player(10, "player2");
        P3 = new Player(1, "player3");
        ColorProperty[] tempArr = ColorProperty.createProperties("red", new String[] {"red1", "red2"}, 
                new double[] {2, 2}, new double[] {1, 1}, 2, new double[][] {{.18, .9, 2.5, 7, 8.75, 10.5},
                        {.18, .9, 2.5, 7, 8.75, 10.5}});
        RED1 = tempArr[0];
        RED2 = tempArr[1];
    }

    @Test
    public void testSetUp() {
        assertEquals(10, P1.getMoney(), 0);
        assertEquals(10, P2.getMoney(), 0);
        assertEquals(1, P3.getMoney(), 0);
        assertEquals("player1", P1.getName());
        assertEquals("player2", P2.getName());
        assertEquals("player3", P3.getName());

        // TODO: Figure out problems with setting correct PlayerID
        //assertEquals(0, P1.getPlayerID());
        //assertEquals(1, P2.getPlayerID());
        //assertEquals(2, P3.getPlayerID());
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
            P3.buyProperty(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Insufficient funds to complete this transaction.", errMsg);
        errMsg = "";

        // Case 2: Successfully buy the property.
        P1.buyProperty(RED1);
        assertEquals(8, P1.getMoney(), 0);
        assertEquals(RED1.getOwner(), P1);

        // Case 3: Unable to buy property that is already owned by someone else.
        try {
            P2.buyProperty(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("The property is already owned.", errMsg);
        errMsg = "";

        // Case 4: Unable to buy property that is already owned by the same player.
        try {
            P1.buyProperty(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("The property is already owned.", errMsg);
    }

    @Test
    public void testBuySellBuildings() {
        // Case 1: Fail to buy buildings on a property player doesn't own
        String errMsg = "";
        try {
            P1.buyBuilding(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Player does not own this property.", errMsg);
        assertEquals(0, RED1.getNumBuildings());
        errMsg = "";

        // Case 2: Fail to buy buildings with insufficient funds
        RED1.changeOwnership(P3);
        try {
            P3.buyBuilding(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Insufficient funds to complete this transaction.", errMsg);
        errMsg = "";
        assertEquals(1, P3.getMoney(), 0);
        assertEquals(0, RED1.getNumBuildings());

        // Case 3: Fail to buy buildings without owning all properties in set.
        RED2.changeOwnership(P1);
        try {
            P1.buyBuilding(RED2);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Owner does not have the full set.", errMsg);
        errMsg = "";
        assertEquals(10, P1.getMoney(), 0);
        assertEquals(0, RED2.getNumBuildings());

        // Case 4: Successfully buy building on a property
        RED1.changeOwnership(P1);
        P1.buyBuilding(RED1);
        assertEquals(8, P1.getMoney(), 0);
        assertEquals(1, RED1.getNumBuildings());
        assertEquals(0, RED2.getNumBuildings());

        // Case 5: Fail to buy building that would cause uneven distribution
        try {
            P1.buyBuilding(RED1);
        } catch (MonopolyException e) {
            errMsg = e.getMessage();
        }
        assertEquals("Houses must be evenly distributed amongst properties.", errMsg);

        // Case 6: Successfully buy a building on a property, then sell a building on a property.
        P1.buyBuilding(RED2);
        assertEquals(6, P1.getMoney(), 0);
        assertEquals(1, RED1.getNumBuildings());
        assertEquals(1, RED2.getNumBuildings());
        P1.sellBuilding(RED1);
        assertEquals(7, P1.getMoney(), 0);
        assertEquals(0, RED1.getNumBuildings());
        assertEquals(1, RED2.getNumBuildings());
    }
}
