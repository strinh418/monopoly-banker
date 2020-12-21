import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Property {

    /** Enum representing different types of properties. */
    public enum typeEnum {
        RED,
        GREEN,
        YELLOW,
        ORANGE,
        PURPLE,
        LIGHTBLUE,
        DARKBLUE,
        BROWN,
        RAILROAD,
        UTILITY
    }

    /** Type or color of this property. */
    protected typeEnum type;

    /** Sets of the same type or color property. */
    protected static final Map<typeEnum, Set<Property>> TYPESETS = new HashMap<>() {
        {
            put(typeEnum.RED, new HashSet<>());
            put(typeEnum.GREEN, new HashSet<>());
            put(typeEnum.YELLOW, new HashSet<>());
            put(typeEnum.ORANGE, new HashSet<>());
            put(typeEnum.PURPLE, new HashSet<>());
            put(typeEnum.LIGHTBLUE, new HashSet<>());
            put(typeEnum.DARKBLUE, new HashSet<>());
            put(typeEnum.BROWN, new HashSet<>());
            put(typeEnum.RAILROAD, new HashSet<>());
            put(typeEnum.UTILITY, new HashSet<>());
        }
    };

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

    /** Array of rents possible for the. */
    protected double[] rentList;

    /** Current rent for landing on this property. */
    protected double rent;

    /** Returns the number of properties in this set that the owner of this property has. */
    private int totalOwnedSet() {
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

    /** Returns whether or not the owner of this property also owns all the other properties of its set. */
    private boolean checkOwnFullSet() {
        if (totalOwnedSet() == TYPESETS.get(type).size()) {
            return true;
        }
        return false;
    }

    /** Update the current rent information for this property if necessary after ownership changes. */
    public abstract void updateOwnerRent();

    /** Change the ownership of the property to NEWOWNER, removing any current owner. */
    public void changeOwnership(Player newOwner) {
        if (owned) {
            owner.removePropertyList(this);
        }
        if (newOwner == null) {
            owned = false;
        } else {
            newOwner.addPropertyList(this);
            owned = true;
        }
        owner = newOwner;
        updateOwnerRent();
    }

    /** Changes the mortgage status based on WANTMORTGAGED. Returns true if a change was made. */
    public boolean changeMortgageStatus(boolean wantMortgaged) {
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

    /** Returns the cost of this property. */
    public double getCost() {
        return cost;
    }

    /** Returns the owner of this property. */
    public Player getOwner() {
        return owner;
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
