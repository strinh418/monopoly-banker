import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ColorProperty extends Property {
    // TODO: Give more specific exception messages (e.g. when property is already owned, specify who owns it.)
    // TODO: Generalize the max number of buildings that can exist on a ColorProperty. For now, only allows 5.
    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Determines if this property's owner has a full set. */
    private boolean inFullSet;

    /** Create an instance of a ColorProperty. */
    private ColorProperty(String name, String color, double cost, double mortgageVal,
                         double buildingCost, double[] rentList) {
        this.name = name;
        this.type = color;
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rent = rentList[0];
        this.buildingCost = buildingCost;
        this.rentList = rentList;
        TYPESETS.get(color).add(this);
    }

    // TODO: Update tests to ensure this constructor works.
    /** Creates all the ColorProperties of a given color. */
    public static ColorProperty[] createProperties(String color, String[] names, double[] costs, double[] mortgages,
                                                double buildingCost, double[][] rentLists) {
        color = color.toLowerCase();
        if (TYPESETS.containsKey(color)) {
            TYPESETS.get(color).clear();
        } else {
            TYPESETS.put(color, new HashSet<>());
        }
        ColorProperty[] properties = new ColorProperty[names.length];
        if (names.length != costs.length || names.length != mortgages.length) {
            throw new PropertyException("Improper arguments provided to create these ColorProperties.");
        }
        for (double[] list : rentLists) {
            if (list.length != 6) {
                throw new PropertyException("Improper arguments provided to create these ColorProperties.");
            }
        }
        for (int i = 0; i < names.length; i += 1) {
            properties[i] = new ColorProperty(names[i].toLowerCase(), color, costs[i], mortgages[i], buildingCost, rentLists[i]);
        }
        return properties;
    }

    /** Returns whether or not the inFullSet status of this property was correct. Then corrects the status if necessary. */
    @Override
    protected boolean correctSetStatus() {
        boolean prevStatus = inFullSet;
        inFullSet = checkOwnFullSet();
        if (prevStatus != inFullSet) {
            for (Property p : TYPESETS.get(type)) {
                ((ColorProperty) p).inFullSet = inFullSet;
            }
        }
        return prevStatus == inFullSet;
    }

    // TODO: Current rules are that buildings cannot be bought unless a full set is owned
    /** Update the current rent information based on the number of buildings and property set ownership. If CHECKED is
     *  false, checks the set status first. */
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

    // TODO: Currently assumes that inFullSet will already be correct and updated before calling. Add check?
    /** Adds a building to this property if ADD and if possible. Takes away a building if not ADD and if possible */
    @Override
    public void updateBuildings(boolean add) {
        if (!inFullSet) {
            throw new OwnershipException("Owner does not have the full set.");
        }
        int adder = 1;
        if (!add) {
            adder = -1;
        }
        if (numBuildings + adder > 5 || numBuildings + adder < 0) {
            throw new PropertyException("Number of buildings must be between 0 and 5.");
        }
        for (Property p : TYPESETS.get(type)) {
            if ((add && numBuildings > p.numBuildings) || (!add && numBuildings < p.numBuildings)) {
                throw new PropertyException("Houses must be evenly distributed amongst properties.");
            }
        }
        if (add) {
            numBuildings += 1;
        } else {
            numBuildings -= 1;
        }
        rent = rentList[numBuildings];
    }

    /** Changes the mortgage status of a ColorProperty based on WANTMORTGAGED. Does not allow mortgaging of a property
     *  if buildings exist on any property of the color group. */
    @Override
    public boolean changeMortgageStatus(boolean wantMortgaged) {
        if (wantMortgaged && inFullSet) {
            for (Property p: TYPESETS.get(type)) {
                if (p.numBuildings > 0) {
                    throw new PropertyException("Buildings exist on a property of this type.");
                }
            }
        }
        return super.changeMortgageStatus(wantMortgaged);
    }

    /** Returns whether or not this property has a full set. */
    public boolean getInFullSet() {
        return inFullSet;
    }
}
