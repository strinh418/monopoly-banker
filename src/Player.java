import java.util.ArrayList;
import java.util.List;

public class Player {

    // TODO: clarity improvement
    //  - priority 3
    //  - give more specific exception messages (e.g. when property is already owned, specify who owns it.)
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

    // TODO: testing
    //  priority 2
    //  write tests for this payRent method for utilities

    // TODO: code efficiency improvement
    //  priority: 1
    //  figure out a better way to pay rent on utilities

    // TODO: urgent code change
    //  priority 5
    //  player shouldn't have to pay rent if the current property is mortgaged
    /** Pay rent for landing on PROPERTY. */
    public void payRent(Utility property, int dice) {
        if (property.getOwner() == null || property.getOwner() == this) {
            throw new OwnershipException("Player does not have to pay rent on this property.");
        }
        payPlayer(property.getOwner(), property.getRent(dice));
    }

    /** Pay rent for landing on PROPERTY. */
    public void payRent(Property property) {
        if (property.getOwner() == null || property.getOwner() == this) {
            throw new OwnershipException("Player does not have to pay rent on this property.");
        }
        payPlayer(property.getOwner(), property.getRent());
    }

    // TODO: Write tests for both giveProperty methods.
    // TODO: Better way than checking that money is >= amount first?
    // TODO: Deal with case when player is given a mortgaged property.
    /** Gives PROPERTY to OTHER player for specified AMOUNT. */
    public void giveProperty(Property property, Player other, double amount) {
        if (property.getOwner() != this) {
            throw new OwnershipException("Player does not own this property.");
        }
        if (other.getMoney() >= amount) {
            property.changeOwnership(other);
        }
        other.payPlayer(this, amount);
    }

    // TODO: Write tests for mortgageProperty and unmortgageProperty
    /** Mortgages PROPERTY and gains the amount from mortgaging. */
    public void mortgageProperty(Property property) {
        if (property.getOwner() != this) {
            throw new OwnershipException("Player does not own this property.");
        }
        if (property.changeMortgageStatus(true)) {
            updateMoney(property.getMortgageValue());
            property.turnMortgaged = turn;
        }
    }

    /** Unmortages PROPERTY and pay the mortgage value plus interest. */
    public void unmortgageProperty(Property property) {
        if (property.getOwner() != this) {
            throw new OwnershipException("Player does not own this property.");
        }
        if (property.changeMortgageStatus(false)) {
            updateMoney(-1 * (property.getMortgageValue() + (.1 * turn - property.turnMortgaged)));
            property.turnMortgaged = 0;
        }
    }

    /** Calculates money player has at end of game buy selling all buildings and mortgaging all owned properties. */
    // TODO: Will currently assume that all properties don't have buildings on them. Later update sell building to allow uneven selling of buildings
    public double calculateWorth() {
        for (Property p : properties) {
            if (p.getNumBuildings() > 0) {
                p.updateBuildings(false, p.getNumBuildings());
            }
        }
        for (Property p : properties) {
            mortgageProperty(p);
        }
        return money;
    }
    /** Gives PROPERTY to OTHER player. */
    public void giveProperty(Property property, Player other) {
        giveProperty(property, other, 0);
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

    /** Get string representation of all properties. */
    public String getProperties() {
        String names = "";
        for (Property p : properties) {
            names += p.getName() + " ";
        }
        return names;
    }

    /** Returns the playerID of this player. */
    public int getPlayerID() {
        return playerID;
    }

}
