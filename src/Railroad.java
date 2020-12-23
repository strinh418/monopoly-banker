public class Railroad extends Property {

    /** The number of railroads this property owner has. */
    private int railroadsOwned;

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

    /** Returns whether or not the railroadsOwned status of this property was correct. Then corrects the status if necessary. */
    // TODO: Need to fix this to properly
    @Override
    protected boolean correctSetStatus() {
        int prevStatus = railroadsOwned;
        railroadsOwned = totalOwnedSet();
        if (prevStatus != railroadsOwned) {
            for (Property p : TYPESETS.get(type)) {
                if (p.owner.equals(owner)) {
                    ((Railroad) p).railroadsOwned = railroadsOwned;
                }
            }
        }
        return prevStatus == railroadsOwned;
    }

    @Override
    public void updateOwnerRent(boolean checked) {

    }

    @Override
    public void updateBuildings(boolean add) {

    }

    /** Returns the number of railroads owned by this property owner. */
    public int getRailroadsOwned() {
        return railroadsOwned;
    }
}
