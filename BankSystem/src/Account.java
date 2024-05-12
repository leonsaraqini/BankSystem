import java.util.ArrayList;
import java.util.List;

public class Account{
    private static int ID_TRACKER = 1;
    private final int Id;
    private final String name;
    private double balance;
    private List<Transaction> transactions;

    public Account(String name) {
        this.Id = ID_TRACKER++;
        this.name = name;
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        this.calculateBalance();
    }

    public void calculateBalance() {
        balance = 0;

        transactions.forEach(t -> {
            if(t.getReason() == TransactionReason.WITHDRAWAL)
                balance -= t.calculateFullAmount();
            else
                balance += t.getAmount();
        });
    }

    public boolean hasEnoughBalance(double amount){
        return (balance - amount) >= 0;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void getAllTransactions() {
        transactions.forEach(System.out::println);
    }

    public double getAmountOfTransfers(){
        return transactions.stream()
                .mapToDouble(t -> t.getAmount())
                .sum();
    }

    public double getAmountOfTransactionFees(){
        return transactions.stream()
                .mapToDouble(t -> t.calculateAdditionalFee())
                .sum();
    }

    @Override
    public String toString() {
        return String.format("%2d) %-20s", Id, name, balance);
    }
}
