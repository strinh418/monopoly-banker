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

    /** Updates the player's money by AMOUNT. Throws an InsufficientFundsException if unable to complete transaction. */
    public void updateMoney(double amount) {
        if (money + amount < 0) {
            throw new InsufficientFundsException();
        }
        money += amount;
    }

    /** Pays player OTHER AMOUNT. Throws a MonopolyException if unable to complete the transaction. */
    public void payPlayer(Player other, double amount) {
        if (money < amount) {
            throw new InsufficientFundsException();
        }
        money -= amount;
        other.money += amount;
    }

    /**
     *  Adds PROPERTY to this player's properties list and changes property's ownership.
     *  Throws an OwnershipException if the property is owned by another player.
     */
    private void addProperty(Property property) {
        if (property.isOwned()) {
            if (!property.getOwner().equals(this)) {
                throw new OwnershipException();
            }
        } else {
            property.setOwner(this);
            properties.add(property);
            // TODO: Need to update rent of this property and those in its color set.
        }
    }
    /** Buys property if possible. Throws a MonopolyException if unable to do so. */
    public void buyProperty(Property property) {
        if (property.isOwned())
        {
            throw new OwnershipException();
        }
        else if (property.getCost() > money)
        {
            throw new InsufficientFundsException();
        }
        else
        {
            // TODO: Finish this method.
        }
    }
}
