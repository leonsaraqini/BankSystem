public class PercentageFeeTransaction extends Transaction{
    private static double PERCENTAGE_FEE = 0.05;

    public PercentageFeeTransaction(double amount, int originatingId, int destinationId, TransactionReason reason) {
        super(amount, originatingId, destinationId, reason);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.PERCENTAGE_FEE;
    }

    @Override
    public double calculateAdditionalFee() {
        return getAmount() * PERCENTAGE_FEE;
    }
}
