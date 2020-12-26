import java.util.ArrayList;
import java.util.List;

public class Railroad extends Property {

    /** The number of railroads this property owner has. */
    private int railroadsOwned;

    // TODO: Make this private after fixing the tests.
    /** Create an instance of a Railroad. */
    public Railroad(String name, double cost, double mortgageVal, double[] rentList) {
        this.name = name;
        this.type = typeEnum.RAILROAD;
        this.cost = cost;
        this.mortgageValue = mortgageVal;
        this.rent = rentList[0];
        this.rentList = rentList;
        TYPESETS.get(typeEnum.RAILROAD).add(this);
    }

    // TODO: Figure out if I do or don't want 0 to be the first number in rentList
    // TODO: Write tests to make sure this works.
    /** Creates all the Railroads for a game. */
    public static List<Railroad> createProperties(String[] names, double cost, double mortgageVal, double[] rentList) {
        TYPESETS.get(typeEnum.RAILROAD).clear();
        List<Railroad> properties = new ArrayList<>();
        if (names.length + 1 != rentList.length) {
            throw new PropertyException("Improper arguments provided to create these Railroads.");
        }
        for (String name : names) {
            properties.add(new Railroad(name, cost, mortgageVal, rentList));
        }
        return properties;
    }

    /** Corrects the railroadsOwned status of this property only. Returns true if a change was made, and false otherwise. */
    private boolean correctRailroadsOwned() {
        int prevStatus = railroadsOwned;
        railroadsOwned = totalOwnedSet();
        return prevStatus == railroadsOwned;
    }

    // TODO: More efficient way of determining which Railroads need to be updated and checked?
    /** Returns whether or not the railroadsOwned status of this property was correct. Then corrects the status if necessary. */
    @Override
    protected boolean correctSetStatus() {
        boolean correct = correctRailroadsOwned();
        for (Property p : TYPESETS.get(type)) {
            ((Railroad) p).correctRailroadsOwned();
        }
        return correct;
    }

    // TODO: Just checks every railroad right now. Update so only required railroads are checked?
    /** Updates the rent if necessary. If CHECKED, assumes that correctSetStatus was called prior and a change is needed.
     *  If not CHECKED, will call correctSetStatus() first and only update rent if incorrect status. */
    @Override
    public void updateOwnerRent(boolean checked) {
        if (!checked) {
            correctSetStatus();
        }
        for (Property p : TYPESETS.get(typeEnum.RAILROAD)) {
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
