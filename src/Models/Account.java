package Models;

import java.util.Objects;

public class Account {
    private String accountNumber;
    private Customer customer;
    private double balance;

    public Account(String accountNumber, Customer customer) {
        Objects.requireNonNull(accountNumber, "Account number cannot be null.");

        String trimmedNumber = accountNumber.trim();
        if (trimmedNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }

        this.accountNumber = trimmedNumber;
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null.");
        this.balance = 0.0;
    }
}
