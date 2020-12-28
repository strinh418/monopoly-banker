import java.util.ArrayList;
import java.util.List;

public class Player {

    // TODO: More specific exception messages to print in the game.
    /** Amount of money this player owns. */
    private double money;

    /** The properties this player owns. */
    private List<Property> properties;

    /** Total created players. */
    private static int NUMPLAYERS = 0;

    /** Name of this player. */
    private String name;

    /** ID of this player. */
    private int playerID;

    /** The current turn this player is on. */
    private int turn;

    /** Initializes a Player with STARTINGMONEY, NAME, and playerID ID. */
    public Player(double startingMoney, String name) {
        money = startingMoney;
        properties = new ArrayList<>();
        this.name = name;
        playerID = NUMPLAYERS;
        NUMPLAYERS += 1;
    }

    /** Updates the player's money by AMOUNT. Throws an InsufficientFundsException if unable to complete transaction.
     *  Returns the amount of money this player now has after the transaction. */
    public double updateMoney(double amount) {
        if (money + amount < 0) {
            throw new InsufficientFundsException();
        }
        money += amount;
        return money;
    }

    /** Pays player OTHER AMOUNT. Throws a MonopolyException if unable to complete the transaction. */
    public void payPlayer(Player other, double amount) {
        if (money < amount) {
            throw new InsufficientFundsException();
        }
        money -= amount;
        other.money += amount;
    }

    /** Adds PROPERTY to this player's properties list.
     * Throws an OwnershipException if the property is owned by another player. */
    public void addPropertyList(Property property) {
        if (property.isOwned()) {
            if (!property.getOwner().equals(this)) {
                throw new OwnershipException();
            }
        } else {
            properties.add(property);
        }
    }
    /** Removes PROPERTY from this player's properties list.
     *  Throws an OwnershipException if the property is not owned by this player. */
    public void removePropertyList(Property property) {
        if (property.isOwned()) {
            if (!property.getOwner().equals(this)) {
                throw new OwnershipException();
            }
            properties.remove(property);
        } else {
            throw new OwnershipException("The player does not own this property");
        }
    }

    /** Buys property if possible. Throws a MonopolyException if unable to do so. */
    public void buyProperty(Property property) {
        if (property.isOwned())
        {
            throw new OwnershipException();
        }
        updateMoney(-1 * property.getCost());
        property.changeOwnership(this);
    }

    /** Adds a building on PROPERTY if possible. */
    public void buyBuilding(Property property) {
        if (property.getOwner() != this) {
            throw new OwnershipException("Player does not own this property.");
        }
        if (money >= property.getBuildingCost()) {
            property.updateBuildings(true);
        }
        updateMoney(-1 * property.getBuildingCost());
    }

    /** Sells a building on PROPERTY if possible. */
    public void sellBuilding(Property property) {
        if (property.getOwner() != this) {
            throw new OwnershipException("Player does not own this property.");
        }
        property.updateBuildings(false);
        updateMoney(.5 * property.getBuildingCost());
    }

    // TODO: Deal with paying rent on utilities (having to get the dice roll number)
    /** Pay rent for landing on PROPERTY. */
    public void payRent(Property property) {
        if (property.getOwner() == null || property.getOwner() == this) {
            throw new OwnershipException("Player does not have to pay rent on this property.");
        }
        payPlayer(property.getOwner(), property.getRent());
    }

    /** Increments the turn of this player and returns the turn. */
    public void incrementTurn() {
        turn += 1;
    }

    /** Returns the player's current turn. */
    public int getTurn() {
        return turn;
    }

    /** Returns the money this player has. */
    public double getMoney() {
        return money;
    }

    /** Returns the name of this player. */
    public String getName() {
        return name;
    }

    /** Returns the playerID of this player. */
    public int getPlayerID() {
        return playerID;
    }

}
