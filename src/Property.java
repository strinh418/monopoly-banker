import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Property {

    /** Type or color of this property. */
    protected String type;

    /** Sets of the same type or color property. */
    protected static final Map<String, Set<Property>> TYPESETS = new HashMap<>();

    /** The owner of this property. Null if not currently owned. */
    protected Player owner;

    /** Determines if the property is currently owned. */
    protected boolean owned;

    /** Determines if the property is currently mortgaged. */
    protected boolean mortgaged;

    /** The turn that this property was mortgaged. */
    protected int turnMortgaged;

    /** The amount gained from mortgaging the property. */
    protected double mortgageValue;

    /** The name of this property. */
    protected String name;

    /** The initial cost to buy this property. */
    protected double cost;

    /** Array of rents possible for the property. */
    protected double[] rentList;

    /** Current rent for landing on this property. */
    protected double rent;

    // TODO: code organization improvement
    //  - priority 1
    //  - consider moving instance variable into ColorProperty class
    //  - will have to adjust any instances where numBuildings are used for Railroad and Utilities
    /** The number of buildings existing on this property. */
    protected int numBuildings;

    // TODO: code organization improvement
    //  - priority 1
    //  - consider moving instance variable into ColorProperty class
    /** Cost of buying a building on this property. */
    protected double buildingCost;

    /** Returns the number of properties in this set that the owner of this property has. */
    protected int totalOwnedSet() {
        if (owner == null) {
            return 0;
        }
        int total = 0;
        for (Property p : TYPESETS.get(type)) {
            if (owner.equals(p.owner)) {
                total += 1;
            }
        }
        return total;
    }

    // TODO: code organization improvement
    //  - priority 2
    //  - consider moving into ColorProperty class since other Property classes do not use this
    /** Returns whether or not the owner of this property also owns all the other properties of its set. */
    protected boolean checkOwnFullSet() {
        if (totalOwnedSet() == TYPESETS.get(type).size()) {
            return true;
        }
        return false;
    }

    /** Returns whether or not the set status of this property was correct and then corrects it if needed. */
    protected abstract boolean correctSetStatus();

    /** Update the current rent information for this property after ownership changes. If CHECKED is false,
     *  double check that the set status is correct first. */
    public abstract void updateOwnerRent(boolean checked);

    /** Change the ownership of the property to NEWOWNER, removing any current owner. Updates rents accordingly */
    public void changeOwnership(Player newOwner) {
        if (owned) {
            owner.removePropertyList(this);
            owned = false;
        }
        if (newOwner != null) {
            newOwner.addPropertyList(this);
            owned = true;
        }
        owner = newOwner;
        if (!correctSetStatus()) {
            updateOwnerRent(true);
        }
    }

    // TODO: code organization improvement
    //  - priority 2
    //  - consider splitting the updateBuildings and changeMortgageStatus methods into two individual methods

    // TODO: urgent change
    //  - priority 5
    //  - add an additional parameter that determines whether uneven distribution of buildings is allowed
    //  - will be used to allow selling of all buildings at the end of the game
    //  - OR create a separate method sellAllBuildings that does not restrict distribution, additional changes in player
    /** Changes buildings status on property, if allowed, based on ADD. */
    public abstract void updateBuildings(boolean add);

    /** Changes the mortgage status based on WANTMORTGAGED. Returns true if a change was made. */
    public boolean changeMortgageStatus(boolean wantMortgaged) {
        if (!owned) {
            throw new OwnershipException("Property is not owned.");
        }
        if (wantMortgaged && !mortgaged) {
            mortgaged = true;
            return true;
        } else if (!wantMortgaged && mortgaged) {
            mortgaged = false;
            return true;
        }
        return false;
    }

    /** Returns whether this property is owned. */
    public boolean isOwned() {
        return owned;
    }

    /** Returns whether this property is mortgaged. */
    public boolean isMortgaged() {
        return mortgaged;
    }

    /** Returns the mortgageValue of this property. */
    public double getMortgageValue() {
        return mortgageValue;
    }

    /** Returns the cost of this property. */
    public double getCost() {
        return cost;
    }

    /** Returns the owner of this property. */
    public Player getOwner() {
        return owner;
    }

    /** Returns the current rent of this property. */
    public double getRent() {
        return rent;
    }

    /** Returns the name of this property. */
    public String getName() {
        return name;
    }

    /** Returns the number of buildings on this property. */
    public int getNumBuildings() {
        return numBuildings;
    }

    /** Returns the building cost of this property. */
    public double getBuildingCost() {
        return buildingCost;
    }

    /** Sets the owner. Using mostly for testing purposes. */
    public void setOwner(Player player) {
        owner = player;
        if (player == null) {
            owned = false;
        } else {
            owned = true;
        }
    }
}
