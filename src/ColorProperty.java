import java.util.Set;

public class ColorProperty extends Property {

    /** The number of buildings existing on this property. */
    private int numBuildings;
    // TODO: Should I move this into Property abstract class?

    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Determines if this property's owner has a full set. */
    private boolean inFullSet;
    // TODO: Should I move this into Property abstract class?

    /** Create an instance of a ColorProperty. */
    public ColorProperty(String name, typeEnum color, double cost, double mortgageVal,
                         double buildingCost, double[] rentList)
    {
        this.name = name;
        this.type = color;
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rent = rentList[0];
        this.buildingCost = buildingCost;
        this.rentList = rentList;
        TYPESETS.get(color).add(this);
    }

    /** Update the current rent information based on the number of buildings and property set ownership. */
    // Update Cases:
    // Was a full set and a card in the set was given away, so the rents are no longer doubled
    // Was not a full set and now every card in the set should be doubled
    // Was a full set and buildings were added to the property
    // TODO: Current rules are that buildings cannot be bought unless a full set is owned
    @Override
    public void updateOwnerRent() {

    }
}
