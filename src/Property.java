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
}
