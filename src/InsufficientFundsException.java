public class InsufficientFundsException extends MonopolyException {
    public InsufficientFundsException() {
        super("Insufficient funds to complete this transaction.");
    }
}
