import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Utility extends Property {

    /** Current multiplier for calculating rent. */
    private int multiplier;

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

    @Override
    protected boolean correctSetStatus() {
        return false;
    }

    @Override
    public void updateOwnerRent(boolean checked) {

    }

    @Override
    public void updateBuildings(boolean add) {

    }
}
