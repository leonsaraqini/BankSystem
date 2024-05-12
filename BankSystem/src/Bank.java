import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Bank{
    private String name;
    private Map<Integer,Account> accounts;
    private HashSet<Integer> accountIDs;

    public Bank(String name) {
        this.name = name;
        this.accounts = new HashMap<>();
        this.accountIDs = new HashSet<>();
    }

    public void makeTransaction(int originatingAccID, int destinationAccID, double amount, TransactionType type)
            throws NotEnoughtBalanceException{

        Account originating = accounts.get(originatingAccID);

        Account destination;

        if(!accountIDs.contains(destinationAccID)){

            destination = new Account(String.format("UnknownBankId_%d", destinationAccID));
            addAcount(destination);

        }else{
            destination= accounts.get(destinationAccID);
        }

        Transaction originatingTransaction =
                Transaction.getNewTransaction(type, amount, originatingAccID, destination.getId(), TransactionReason.WITHDRAWAL);

        if(!originating.hasEnoughBalance(originatingTransaction.calculateFullAmount()))
            throw new NotEnoughtBalanceException(originating.getName());

        Transaction resultingTransaction =
                Transaction.getNewTransaction(type, amount, originatingAccID, destination.getId(), TransactionReason.DEPOSIT);


        originating.addTransaction(originatingTransaction);
        destination.addTransaction(resultingTransaction);
    }

    public void makeTransaction(int accountID, double amount, TransactionType type, TransactionReason reason) throws NotEnoughtBalanceException {
        Account account = accounts.get(accountID);
        Transaction transaction = Transaction.getNewTransaction(type, amount, accountID, accountID, reason);

        if(reason != TransactionReason.DEPOSIT && !account.hasEnoughBalance(transaction.calculateFullAmount()))
            throw new NotEnoughtBalanceException(account.getName());

        account.addTransaction(transaction);
    }

    public Account getAccountFromId(int id) throws AccountNonExistentException {
        if(accountIDs.contains(id))
            return accounts.get(id);

        throw new AccountNonExistentException();
    }

    public void addAcount(Account account){
        accounts.put(account.getId(), account);
        accountIDs.add(account.getId());
    }

    public double getAllTransactionFees(){
        return accounts.values()
                .stream()
                .mapToDouble(a -> a.getAmountOfTransactionFees())
                .sum();
    }

    public double getAllTransfers(){
        return accounts.values()
                .stream()
                .mapToDouble(a -> a.getAmountOfTransfers())
                .sum();
    }

    public void getAllAccounts(){
        System.out.printf("Id  %-20s\n", "Name");

        accounts.values()
                .stream()
                .filter(a -> !a.getName().contains("UnknownBankId"))
                .forEach(System.out::println);
    }

    public void getAllAccounts(int id){
        System.out.printf("Id  %-20s\n", "Name");
        accounts.values()
                .stream()
                .filter(a -> a.getId() != id)
                .forEach(System.out::println);
    }
}
