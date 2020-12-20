import java.util.HashMap;

public class ColorProperty extends Property {

    /** The number of buildings existing on this property. */
    private int numBuildings;

    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Array of rents for the number of buildings on the property. */
    private double[] buildingRents;
}
