import java.util.HashSet;

public class Utility extends Property {

    /** The number of utilities owned by this property owner. */
    private int utilitiesOwned;

    /** Create an instance of a Utility. */
    private Utility(String name, double cost, double mortgageVal, double[] multiplierList) {
        this.name = name;
        this.cost = cost;
        this.type = "utility";
        this.mortgageValue = mortgageVal;
        this.rentList = multiplierList;
        this.rent = multiplierList[0];
        TYPESETS.get("utility").add(this);
    }

    // TODO: code improvement
    //  - priority 2
    //  - figure out if I do or don't want 0 to be the first number in rentList
    /** Creates all the Utilities for this game. */
    public static Utility[] createProperties(String[] names, double cost, double mortgageVal, double[] multiplierList) {
        if (TYPESETS.containsKey("utility")) {
            TYPESETS.get("utility").clear();
        } else {
            TYPESETS.put("utility", new HashSet<>());
        }
        Utility[] properties = new Utility[names.length];
        if (names.length + 1 != multiplierList.length) {
            throw new PropertyException("Improper arguments provided to create these Utilities.");
        }
        for (int i = 0; i < names.length; i += 1) {
            properties[i] = new Utility(names[i].toLowerCase(), cost, mortgageVal, multiplierList);
        }
        return properties;
    }

    /** Corrects the utilitiesOwned status of this property only. Returns true if a change was made, and false otherwise. */
    private boolean correctUtilitiesOwned() {
        int prevStatus = utilitiesOwned;
        utilitiesOwned = totalOwnedSet();
        return prevStatus == utilitiesOwned;
    }

    // TODO: code efficiency
    //  - priority 4
    //  - more efficient way of determining which Railroads need to be updated and checked? or make static?
    //  - to ensure that changeOwnership always works, will always return false for now, but want to improve this.
    /** Returns whether or not the utilitiesOwned status of this property was correct. Then corrects the status if necessary. */
    @Override
    protected boolean correctSetStatus() {
        boolean correct = correctUtilitiesOwned();
        for (Property p : TYPESETS.get(type)) {
            ((Utility) p).correctUtilitiesOwned();
        }
        // return correct;
        return false;
    }

    // TODO: generalization
    //  - priority 2
    //  - possibly implement updateOwnerRent in Property by generalizing utiliteisOwned/railroadsOwned as totalOwned
    @Override
    public void updateOwnerRent(boolean checked) {
        if (!checked) {
            correctSetStatus();
        }
        for (Property p : TYPESETS.get("utility")) {
            p.rent = p.rentList[((Utility) p).utilitiesOwned];
        }
    }

    @Override
    public void updateBuildings(boolean add, int num) {
        throw new PropertyException("Buildings cannot be bought on this Property type.");
    }

    @Override
    public void updateBuildings(boolean add) {
        throw new PropertyException("Buildings cannot be bought on this Property type.");
    }

    /** Returns the number of utilities owned. */
    public int getUtilitiesOwned() {
        return utilitiesOwned;
    }

    // TODO: code improvement
    //  - priority 3
    //  - temp solution. figure out better way to ask for dice number than having to create different method like this.
    /** Returns the rent after asking for the dice number. */
    public double getRent(int dice) {
        return rent * dice / 100;
    }
}
