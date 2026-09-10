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

    public void deposit(double amount){
        if (amount<=0) throw new IllegalArgumentException("Deposit amount have to be above 0!");
        balance+=amount;
    }

    public void withdraw(double amount){
        if (amount<=0) throw new IllegalArgumentException("Withdraw amount have to be above 0!");
        if(amount>balance) throw new IllegalArgumentException("Not enught balanca");
        balance-=amount;
    }

    public void transfer(String accountNumber,double amount){

    }



}
