import java.util.ArrayList;
import java.util.List;

public class Player {

    /** Amount of money this player owns. */
    private double money;

    /** The properties this player owns. */
    private List<Property> properties;

    /** Name of this player. */
    private String name;

    /** ID of this player. */
    private int playerID;

    /** The current turn this player is on. */
    private int turn;

    /** Initializes a Player with STARTINGMONEY, NAME, and playerID ID. */
    public Player(double startingMoney, String name, int id) {
        money = startingMoney;
        properties = new ArrayList<>();
        this.name = name;
        playerID = id;
        turn = 1;
    }
}
