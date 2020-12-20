public class OwnershipException extends MonopolyException {
    public OwnershipException() {
        super("The property is already owned by another player.");
    }

    public OwnershipException(String msg) {
        super(msg);
    }
}
