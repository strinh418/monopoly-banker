public class ColorProperty extends Property {

    /** Cost of buying a building on this property. */
    private double buildingCost;

    /** Determines if this property's owner has a full set. */
    private boolean inFullSet;

    /** Create an instance of a ColorProperty. */
    public ColorProperty(String name, typeEnum color, double cost, double mortgageVal,
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
