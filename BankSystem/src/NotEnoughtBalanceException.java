public class NotEnoughtBalanceException extends Exception{
    public NotEnoughtBalanceException(String account) {
        super(String.format("The account of: %s doesn't have enough funds to perform the transaction!!!", account));
    }
}
