package atm;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account acc) {
        accounts.put(acc.getAccountNumber(), acc);
    }

    public Account findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public boolean accountExists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    // simple sample data
    public static Bank sampleBank() {
        Bank b = new Bank();
        b.addAccount(new Account("1001", "Sagar Reddy", "1234", 50000));
        b.addAccount(new Account("1002", "Ramesh Kumar", "4321", 30000));
        b.addAccount(new Account("1003", "vasanth", "1111", 10000));
        b.addAccount(new Account("1004", "venkatesh", "1110", 80000));
        return b;
    }
}
