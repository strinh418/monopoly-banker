import java.util.HashSet;

public class Railroad extends Property {

    /** The number of railroads this property owner has. */
    private int railroadsOwned;

    /** Create an instance of a Railroad. */
    private Railroad(String name, double cost, double mortgageVal, double[] rentList) {
        this.name = name;
        this.type = "railroad";
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rent = rentList[0];
        this.rentList = rentList;
        TYPESETS.get("railroad").add(this);
    }

    // TODO: code improvement
    //  - priority 2
    //  - figure out if I do or don't want 0 to be the first number in rentList

    // TODO: testing
    //  - priority 3
    //  - write tests to make sure this works for the setup.
    /** Creates all the Railroads for a game. */
    public static Railroad[] createProperties(String[] names, double cost, double mortgageVal, double[] rentList) {
        if (TYPESETS.containsKey("railroad")) {
            TYPESETS.get("railroad").clear();
        } else {
            TYPESETS.put("railroad", new HashSet<>());
        }
        Railroad[] properties = new Railroad[names.length];
        if (names.length + 1 != rentList.length) {
            throw new PropertyException("Improper arguments provided to create these Railroads.");
        }
        for (int i = 0; i < names.length; i += 1) {
            properties[i] = new Railroad(names[i].toLowerCase(), cost, mortgageVal, rentList);
        }
        return properties;
    }

    /** Corrects the railroadsOwned status of this property only. Returns true if a change was made, and false otherwise. */
    private boolean correctRailroadsOwned() {
        int prevStatus = railroadsOwned;
        railroadsOwned = totalOwnedSet();
        return prevStatus == railroadsOwned;
    }

    // TODO: code efficiency
    //  - priority 4
    //  - more efficient way of determining which Railroads need to be updated and checked?
    //  - to ensure that changeOwnership always works, will always return false for now, but want to improve this.
    /** Returns whether or not the railroadsOwned status of this property was correct. Then corrects the status if necessary. */
    @Override
    protected boolean correctSetStatus() {
        boolean correct = correctRailroadsOwned();
        for (Property p : TYPESETS.get(type)) {
            ((Railroad) p).correctRailroadsOwned();
        }
        // return correct;
        return false;
    }

    // TODO: code efficiency
    //  - priority 4
    //  - just checks every railroad right now. update so only required railroads are checked?
    /** Updates the rent if necessary. If CHECKED, assumes that correctSetStatus was called prior and a change is needed.
     *  If not CHECKED, will call correctSetStatus() first and only update rent if incorrect status. */
    @Override
    public void updateOwnerRent(boolean checked) {
        if (!checked) {
            correctSetStatus();
        }
        for (Property p : TYPESETS.get("railroad")) {
            p.rent = p.rentList[((Railroad) p).railroadsOwned];
        }
    }

    @Override
    public void updateBuildings(boolean add) {
        throw new PropertyException("Buildings cannot be bought on this Property type.");
    }

    /** Returns the number of railroads owned by this property owner. */
    public int getRailroadsOwned() {
        return railroadsOwned;
    }
}
