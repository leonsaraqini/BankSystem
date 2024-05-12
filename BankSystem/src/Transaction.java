public abstract class Transaction{
    private final double amount;
    private final int originatingId;
    private final int destinationId;
    private final TransactionReason reason;


    public Transaction(double amount, int originatingId, int destinationId, TransactionReason reason) {
        this.amount = amount;
        this.originatingId = originatingId;
        this.destinationId = destinationId;
        this.reason = reason;
    }

    abstract public double calculateAdditionalFee();
    abstract public TransactionType getType();

    public double calculateFullAmount(){
        return amount + calculateAdditionalFee();
    }

    public double getAmount() {
        return amount;
    }

    public TransactionReason getReason() {
        return reason;
    }

    public static Transaction getNewTransaction(TransactionType type, double amount, int originatingId, int destinationId, TransactionReason reason){

        if (type == TransactionType.FLAT_FEE) {
            return new FlatFeeTransaction(amount, originatingId, destinationId, reason);
        }else{
            return new PercentageFeeTransaction(amount, originatingId, destinationId, reason);
        }

    }

    private String getTransactionReasonName(){
        return originatingId != destinationId ? reason.name() : reason.name() + "FROM SAME ACCOUNT";
    }

    private double getFullAmountText(){
        return reason == TransactionReason.DEPOSIT ? getAmount() : calculateFullAmount();
    }

    @Override
    public String toString() {
        return String.format("* %.2f - Reason: %s, Type: %s *", getFullAmountText(), getTransactionReasonName(), getType().name());
    }
}
