import java.util.Set;

public class ColorProperty extends Property {

    /** The number of buildings existing on this property. */
    private int numBuildings;
    // TODO: Should I move this into Property abstract class?

    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Array of rents for the number of buildings on the property. */
    private double[] buildingRents;

    /** Create an instance of a ColorProperty. */
    public ColorProperty(String name, typeEnum color, double cost, double mortgageVal, double rent,
                         double buildingCost, double[] buildingRents)
    {
        this.name = name;
        this.type = color;
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rent = rent;
        this.defaultRent = rent;
        this.buildingCost = buildingCost;
        this.buildingRents = buildingRents;
        TYPESETS.get(color).add(this);
    }

    /** Update the current rent information based on the number of buildings and property set ownership. */
    // TODO: Continue working on calculating rent updates here.
    public void updateRent() {
        //double previousRent = rent;
        boolean hasFullSet;
        boolean needUpdate = false;
        if (checkOwnFullSet()) {
            hasFullSet = true;
            needUpdate = rent != 2 * defaultRent || numBuildings > 0;
        } else {
            hasFullSet = false;
            needUpdate = rent != defaultRent;
        }
        if (needUpdate) {
            Set<Property> fullSet = TYPESETS.get(type);
            if (hasFullSet) {
                for (Property p : fullSet) {

                }
            }
        }

        if (checkOwnFullSet()) {
            for (Property p : TYPESETS.get(type)) {
                if (((ColorProperty)p).numBuildings == 0) {

                }
            }
        }
    }
}
