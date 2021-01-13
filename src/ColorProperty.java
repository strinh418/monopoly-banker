import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ColorProperty extends Property {
    // TODO: clarity improvement
    //  - priority 3
    //  - give more specific exception messages (e.g. when property is already owned, specify who owns it.)

    // TODO: generalization improvement
    //  - priority 1
    //  - generalize the max number of buildings that can exist on a ColorProperty. For now, only allows 5.

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

    /** Creates all the ColorProperties of a given color. Assumes mortgages are half the costs. */
    public static ColorProperty[] createProperties(String color, String[] names, double[] costs, double buildingCost,
                                                   double[][] rentLists) {
        double[] mortgages = new double[costs.length];
        for (int i = 0; i < costs.length; i += 1) {
            mortgages[i] = costs[i] / 2;
        }
        return createProperties(color, names, costs, mortgages, buildingCost, rentLists);
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

    // TODO: testing
    //  - priority 4
    //  - write tests to check that changing ownership doesn't work when there are buildings
    //  - make sure there are tests for changing ownership of mortgaged properties
    /** Change the ownership of the property to NEWOWNER, removing any current owner. Updates rents accordingly */
    @Override
    public void changeOwnership(Player newPlayer) {
        if (inFullSet) {
            for (Property p: TYPESETS.get(type)) {
                if (p.numBuildings > 0) {
                    throw new PropertyException("Buildings exist on a property of this type.");
                }
            }
        }
        super.changeOwnership(newPlayer);
    }

    // TODO: testing
    //  - priority 2
    //  - write tests for this new updateBuildings method
    /** Adds building(s) if ADD and if possible, else subtracts. Allows uneven distribution if NUM != 1. */
    @Override
    public void updateBuildings(boolean add, int num) {
        if (!inFullSet) {
            throw new OwnershipException("Owner does not have the full set.");
        }
        int adder = num;
        if (!add) {
            adder *= -1;
        }
        if (numBuildings + adder > 5 || numBuildings + adder < 0) {
            throw new PropertyException("Number of buildings must be between 0 and 5.");
        }
        if (adder == 1 || adder == -1) {
            for (Property p : TYPESETS.get(type)) {
                if ((add && numBuildings > p.numBuildings) || (!add && numBuildings < p.numBuildings)) {
                    throw new PropertyException("Houses must be evenly distributed amongst properties.");
                }
                if (p.mortgaged) {
                    throw new PropertyException("Property of this type is mortgaged.");
                }
            }
        }
        numBuildings += adder;
        rent = rentList[numBuildings];
    }

    // TODO: code improvement
    //  - priority 3
    //  - currently assumes that inFullSet will already be correct and updated before calling. Add check?
    /** Adds a building to this property if ADD and if possible. Takes away a building if not ADD and if possible */
    @Override
    public void updateBuildings(boolean add) {
        updateBuildings(add, 1);
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
