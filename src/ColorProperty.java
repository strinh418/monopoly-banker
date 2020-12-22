import java.util.Set;

public class ColorProperty extends Property {

    /** The number of buildings existing on this property. */
    private int numBuildings;
    // TODO: Should I move this into Property abstract class?

    /** Cost of buying a building on this property. */
    private double buildingCost;

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

    /** Returns whether or not the inFullSet status of this property was correct. Then corrects the status if necessary. */
    @Override
    protected boolean correctSetStatus() {
        boolean prevStatus = inFullSet;
        inFullSet = checkOwnFullSet();
        if (prevStatus != inFullSet) {
            for (Property p : TYPESETS.get(type)) {
                p.inFullSet = inFullSet;
            }
        }
        return prevStatus == inFullSet;
    }

    /** Update the current rent information based on the number of buildings and property set ownership. If CHECKED is
     *  false, checks the set status first. */
    // TODO: Current rules are that buildings cannot be bought unless a full set is owned
    @Override
    public void updateOwnerRent(boolean checked) {
        if (!checked) {
            correctSetStatus();
        }
        int multiplier = 1;
        if (inFullSet) {
            multiplier = 2;
        }
        for (Property p : TYPESETS.get(type)) {
            p.rent = p.rentList[0] * multiplier;
        }
    }

    @Override
    public boolean changeMortgageStatus(boolean wantMortgaged) {
        return false;
    }

    /** Returns whether or not this property has a full set. */
    public boolean getInFullSet() {
        return inFullSet;
    }
}
