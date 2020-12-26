import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Utility extends Property {

    /** Current multiplier for calculating rent. */
    //private int multiplier;

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

    // TODO: More efficient way of determining which Utilities need to be updated and checked? Or should this be made static
    // TODO: To ensure that changeOwnership always works, will always return false for now, but want to improve this.
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

    // TODO: Possibly implement updateOwnerRent in Property by generalizing utiliteisOwned/railroadsOwned as totalOwned
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
    public void updateBuildings(boolean add) {
        throw new PropertyException("Buildings cannot be bought on this Property type.");
    }

    /** Returns the number of utilities owned. */
    public int getUtilitiesOwned() {
        return utilitiesOwned;
    }
    /** Returns the multiplier of this property. */
    /*public int getMultiplier() {
        return multiplier;
    }*/
}
