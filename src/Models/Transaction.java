package Models;

import Models.Enums.Type;
import Utils.BankUtils;

import java.time.LocalDateTime;

public class Transaction {
    private final String id;
    private final Type type;
    private final double amount;
    private final LocalDateTime date;
    private String description;

    public Transaction(Type type, double amount) {
        this(type, amount, null);
    }

    public Transaction(Type type, double amount, String description) {
        this.id = BankUtils.generateId();
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}