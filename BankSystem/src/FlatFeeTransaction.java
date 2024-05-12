public class FlatFeeTransaction extends Transaction{
    private static int FLAT_FEE = 10;

    public FlatFeeTransaction(double amount, int originatingId, int destinationId, TransactionReason reason) {
        super(amount, originatingId, destinationId, reason);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.FLAT_FEE;
    }

    @Override
    public double calculateAdditionalFee() {
        return FLAT_FEE;
    }
}
