import java.lang.reflect.Type;
import java.util.*;

public class Main {
    public static void welcomeMessage(){
        System.out.println("\\WELCOME TO THE BANK SYSTEM APP, HOPE YOU ENJOY IT :)/");
    }

    public static void options(){
        System.out.println("\nEnter one of the options avaliable: \n\n" +
                "- Create a bank (1),\n" + "- Create an account (2),\n" +
                "- Perform transaction (3)\n" + "- Check transactions for an account (4)\n" +
                "- Check balance of an account (5)\n" + "- See list of accounts (6)\n" +
                "- Check bank total transaction fee amount (7)\n" + "- Check bank total transfer amount (8)\n\n" +
                "If you want to exit the app press 0");
    }

    public static void invalidNumberMessage() {
        System.out.println("\n !!! Invalid Option !!!\n");
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Bank bank = null;

        welcomeMessage();

        while(true){
            Thread.sleep(4000);
            options();

            int i = -1;

            try {

                i = Integer.valueOf(scanner.next());
                Thread.sleep(2000);

                if(i == 0) break;

                if (i > 8) {
                    invalidNumberMessage();
                    continue;
                }

                if (i > 1 && bank == null) {
                    System.out.println("\n!!! Create a bank first! !!!\n");
                    continue;
                }

                if (i == 1) {
                    if (bank != null) continue;

                    System.out.println("Enter a name of the bank:");

                    bank = new Bank(scanner.next());

                    bank.addAcount(new Account("John"));
                    bank.addAcount(new Account("Jane"));

                    Thread.sleep(1000);
                    System.out.println("||| Success! New bank created |||");
                }

                if (i == 2) {
                    System.out.println("Enter name for account:");

                    Account account = new Account(scanner.next());

                    bank.addAcount(account);

                    Thread.sleep(1000);
                    System.out.println("\n||| Success! New account created |||\n");
                }

                if (i == 3) {
                    System.out.println("From which account: ");
                    bank.getAllAccounts();

                    Thread.sleep(2000);

                    try {

                        Account account = bank.getAccountFromId(Integer.valueOf(scanner.next()));
                        Thread.sleep(2000);

                        System.out.println("Enter amount:");
                        int amount = Integer.valueOf(scanner.next());

                        Thread.sleep(2000);

                        TransactionType type;
                        TransactionReason reason = TransactionReason.WITHDRAWAL;

                        System.out.println("To which account:\n- Personal (1)\n- Another account(2)");

                        int typeOfAccount = Integer.valueOf(scanner.next());
                        Thread.sleep(2000);

                        if(typeOfAccount < 1 || typeOfAccount > 2){
                            invalidNumberMessage();
                            continue;
                        }

                        int destination = 0;

                        if (typeOfAccount == 1) {

                            System.out.println("Choose what reason:\n- Withdraw (1)\n- Deposit (2)");

                            int typeOfReason = Integer.valueOf(scanner.next());
                            Thread.sleep(2000);


                            if(typeOfReason < 1 || typeOfReason > 2){
                                invalidNumberMessage();
                                continue;
                            }

                            if (typeOfReason == 2) {
                                reason = TransactionReason.DEPOSIT;
                            }

                        } else {
                            System.out.println("Choose one of the following accounts to transfer:");

                            bank.getAllAccounts(account.getId());

                            System.out.println("Or write another id from a different Bank");

                            destination = Integer.valueOf(scanner.next());
                            Thread.sleep(2000);
                        }

                        System.out.println("Choose what type of transaction:\n- Flat Fee (1)\n- Percentage Fee (2)");

                        int typeOfTransaction = Integer.valueOf(scanner.next());
                        Thread.sleep(2000);

                        if(typeOfTransaction < 1 || typeOfTransaction > 2){
                            invalidNumberMessage();
                            continue;
                        }

                        if (typeOfTransaction == 1)
                            type = TransactionType.FLAT_FEE;
                        else
                            type = TransactionType.PERCENTAGE_FEE;

                        if (typeOfAccount == 1) {
                            bank.makeTransaction(account.getId(), amount, type, reason);
                        } else {
                            bank.makeTransaction(account.getId(), destination, amount, type);
                        }

                        System.out.println("\n||| Success! Transaction has been made |||\n");

                    } catch (NotEnoughtBalanceException | AccountNonExistentException e) {

                        System.out.println(e.getMessage());
                    }
                }

                if (i == 4) {
                    System.out.println("From which account: ");
                    bank.getAllAccounts();

                    try{
                        Account account = bank.getAccountFromId(Integer.valueOf(scanner.next()));
                        Thread.sleep(2000);

                        account.getAllTransactions();
                    }catch (AccountNonExistentException e){
                        System.out.println(e.getMessage());
                    }


                }

                if (i == 5) {
                    System.out.println("From which account: ");
                    bank.getAllAccounts();

                    try{
                        Account account = bank.getAccountFromId(Integer.valueOf(scanner.next()));
                        Thread.sleep(2000);

                        System.out.println(String.format("The balance of %s is: $%.2f", account.getName(), account.getBalance()));
                    }catch (AccountNonExistentException e){
                        System.out.println(e.getMessage());
                    }

                }

            }catch (NumberFormatException e){
                invalidNumberMessage();
            }

            if(i == 6){
                System.out.println("List Of All Accounts: \n");
                bank.getAllAccounts();
            }

            if(i == 7){
                System.out.println("Total amount of transaction fees: $" + bank.getAllTransactionFees());
            }

            if(i == 8){
                System.out.println("Total amount of transfers fees: $" + bank.getAllTransfers());
            }
        }

        System.out.println("Thank you for your time :)");
    }
}