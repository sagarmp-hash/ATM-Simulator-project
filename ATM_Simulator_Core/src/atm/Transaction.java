package atm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String type;
    private final double amount;
    private final double postBalance;
    private final LocalDateTime timestamp;

    public Transaction(String type, double amount, double postBalance) {
        this.type = type;
        this.amount = amount;
        this.postBalance = postBalance;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s | %-18s | %10.2f | bal: %10.2f", timestamp.format(fmt), type, amount, postBalance);
    }
}
