public abstract class Property {

    /** The owner of this property. Null if not currently owned. */
    private Player owner;

    /** Determines if the property is currently owned. */
    private boolean owned;

    /** Determines if the property is currently mortgaged. */
    private boolean mortgaged;

    /** The turn that this property was mortgaged. */
    private int turnMortgaged;

    /** The amount gained from mortgaging the property. */
    private double mortgageValue;

    /** The name of this property. */
    private String name;

    /** The initial cost to buy this property. */
    private double cost;

    /** Cost of rent for landing on this property. */
    private double rent;

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

    /** Sets the owner to player. */
    public void setOwner(Player player) {
        owner = player;
    }
}
