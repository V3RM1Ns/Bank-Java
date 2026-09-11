package Models;

import Models.Enums.Type;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Transaction {
    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(1);

    private final int id;
    private final Type type;
    private final double amount;
    private final LocalDateTime date;

    public Transaction(Type type, double amount) {
        this.id = ID_GENERATOR.getAndIncrement();
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }


    public int getId() {
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

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}