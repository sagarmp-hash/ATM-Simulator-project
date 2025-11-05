package atm;

import java.util.List;
import java.util.Scanner;

public class ATMMain {
    private static final Scanner sc = new Scanner(System.in);
    private static final Bank bank = Bank.sampleBank();
    private static Account currentAccount = null;

    public static void main(String[] args) {
        System.out.println("Welcome to the Core-Java ATM Simulator");

        while (true) {
            if (currentAccount == null) {
                showLoginMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n1) Login\n2) Exit");
        System.out.print("Select: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1": loginFlow(); break;
            case "2": System.out.println("Goodbye!"); System.exit(0); break;
            default: System.out.println("Invalid option");
        }
    }

    private static void loginFlow() {
        System.out.print("Enter account number: ");
        String accNo = sc.nextLine().trim();
        Account acc = bank.findAccount(accNo);
        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.print("Enter PIN: ");
        String pin = sc.nextLine().trim();
        if (!acc.checkPin(pin)) {
            System.out.println("Invalid PIN.");
            return;
        }
        currentAccount = acc;
        System.out.println("Login successful. Welcome, " + currentAccount.getHolderName() + "!");
    }

    private static void showUserMenu() {
        System.out.println("\n--- ATM Menu ---");
        System.out.println("1) Balance Enquiry");
        System.out.println("2) Withdraw");
        System.out.println("3) Deposit");
        System.out.println("4) Transfer");
        System.out.println("5) Transaction History");
        System.out.println("6) Change PIN");
        System.out.println("7) Logout");
        System.out.print("Choose: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1": doBalance(); break;
            case "2": doWithdraw(); break;
            case "3": doDeposit(); break;
            case "4": doTransfer(); break;
            case "5": showTransactions(); break;
            case "6": changePin(); break;
            case "7": logout(); break;
            default: System.out.println("Invalid choice");
        }
    }

    private static void doBalance() {
        System.out.printf("Current balance: %.2f\n", currentAccount.getBalance());
    }

    private static void doWithdraw() {
        try {
            System.out.print("Enter amount to withdraw: ");
            double amt = Double.parseDouble(sc.nextLine().trim());
            if (amt <= 0) { System.out.println("Enter a positive amount."); return; }
            if (currentAccount.withdraw(amt)) System.out.println("Please collect cash. New balance: " + currentAccount.getBalance());
            else System.out.println("Insufficient funds.");
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number.");
        }
    }

    private static void doDeposit() {
        try {
            System.out.print("Enter amount to deposit: ");
            double amt = Double.parseDouble(sc.nextLine().trim());
            if (amt <= 0) { System.out.println("Enter a positive amount."); return; }
            currentAccount.deposit(amt);
            System.out.println("Deposit successful. New balance: " + currentAccount.getBalance());
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number.");
        }
    }

    private static void doTransfer() {
        try {
            System.out.print("Enter destination account number: ");
            String dest = sc.nextLine().trim();
            if (!bank.accountExists(dest)) { System.out.println("Destination account not found."); return; }
            System.out.print("Enter amount to transfer: ");
            double amt = Double.parseDouble(sc.nextLine().trim());
            if (amt <= 0) { System.out.println("Enter a positive amount."); return; }
            Account to = bank.findAccount(dest);
            if (currentAccount.transferTo(to, amt)) System.out.println("Transfer successful. New balance: " + currentAccount.getBalance());
            else System.out.println("Insufficient funds.");
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid number.");
        }
    }

    private static void showTransactions() {
        List<Transaction> txs = currentAccount.getTransactions();
        if (txs.isEmpty()) { System.out.println("No transactions yet."); return; }
        System.out.println("\n--- Transaction History ---");
        for (Transaction t : txs) System.out.println(t);
    }

    private static void changePin() {
        System.out.print("Enter current PIN: ");
        String oldPin = sc.nextLine().trim();
        if (!currentAccount.checkPin(oldPin)) { System.out.println("Incorrect PIN."); return; }
        System.out.print("Enter new 4-digit PIN: ");
        String newPin = sc.nextLine().trim();
        if (!newPin.matches("\\d{4}")) { System.out.println("PIN must be 4 digits."); return; }
        currentAccount.changePin(newPin);
        System.out.println("PIN changed successfully.");
    }

    private static void logout() {
        System.out.println("Logging out " + currentAccount.getHolderName());
        currentAccount = null;
    }
}
