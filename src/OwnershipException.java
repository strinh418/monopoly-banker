public class OwnershipException extends MonopolyException {
    public OwnershipException() {
        super("The property is already owned.");
    }
}
