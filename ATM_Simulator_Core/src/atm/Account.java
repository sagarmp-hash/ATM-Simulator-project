package atm;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private final String accountNumber;
    private String pin;
    private double balance;
    private final String holderName;
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, String holderName, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }

    public boolean checkPin(String inputPin) {
        return this.pin.equals(inputPin);
    }

    public void changePin(String newPin) {
        this.pin = newPin;
    }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be > 0");
        balance += amount;
        transactions.add(new Transaction("DEPOSIT", amount, balance));
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdraw amount must be > 0");
        if (amount > balance) return false;
        balance -= amount;
        transactions.add(new Transaction("WITHDRAW", amount, balance));
        return true;
    }

    public boolean transferTo(Account to, double amount) {
        if (to == null) throw new IllegalArgumentException("Destination account is null");
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be > 0");
        if (amount > balance) return false;
        balance -= amount;
        to.balance += amount;
        transactions.add(new Transaction("TRANSFER_OUT to:" + to.getAccountNumber(), amount, balance));
        to.transactions.add(new Transaction("TRANSFER_IN from:" + this.getAccountNumber(), amount, to.balance));
        return true;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }
}
