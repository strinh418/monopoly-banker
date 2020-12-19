import java.util.HashMap;

public class ColorProperty extends Property {

    /** Enum representing different colors for the properties. */
    public enum colorEnum {
        RED,
        GREEN,
        YELLOW,
        ORANGE,
        PURPLE,
        LIGHTBLUE,
        DARKBLUE,
        BROWN
    }

    /** The number of cards in a current color set. */
    private HashMap<colorEnum, Integer> colorSet;

    /** Color of this property. */
    private colorEnum color;

    /** The number of buildings existing on this property. */
    private int numBuildings;

    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Array of rents for the number of buildings on the property. */
    private double[] buildingRents;
}
