import java.util.ArrayList;
import java.util.List;

public class Utility extends Property {

    /** Current multiplier for calculating rent. */
    private int multiplier;

    /** Create an instance of a Utility. */
    private Utility(String name, double cost, double mortgageVal, double[] multiplierList) {
        this.name = name;
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rentList = multiplierList;
        this.rent = multiplierList[0];
        TYPESETS.get(typeEnum.UTILITY).add(this);
    }

    /** Creates all the Utilities for this game. */
    public static List<Utility> createProperties(String[] names, double cost, double mortgageVal, double[] multiplierList) {
        TYPESETS.get(typeEnum.UTILITY).clear();
        List<Utility> properties = new ArrayList<>();
        if (names.length + 1 != multiplierList.length) {
            throw new PropertyException("Improper arguments provided to create these Utilities.");
        }
        for (String name : names) {
            properties.add(new Utility(name, cost, mortgageVal, multiplierList));
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
