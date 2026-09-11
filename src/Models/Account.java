package Models;

import Interfaces.BankOperation;
import Models.Enums.Type;

import java.util.ArrayList;
import java.util.Objects;

public class Account implements BankOperation {
    private final String accountNumber;
    private final Customer customer;
    private double balance;
    private ArrayList<Transaction> transactions;

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


        Transaction transaction=new Transaction(Type.Deposit,amount);
        transactions.add(transaction);
    }

    public void withdraw(double amount){
        if (amount<=0) throw new IllegalArgumentException("Withdraw amount have to be above 0!");
        if(amount>balance) throw new IllegalArgumentException("Not enught balanca");

        balance-=amount;


        Transaction transaction=new Transaction(Type.Withdraw,amount);
        transactions.add(transaction);
    }

    public void transfer(Account receiver,double amount){
        if (receiver == null) throw new IllegalArgumentException("Receiver account not found.");
        if (amount<=0) throw new IllegalArgumentException("Withdraw amount have to be above 0!");

        withdraw(amount);
        receiver.deposit(amount);

        Transaction transaction=new Transaction(Type.Transfer,amount);
        transactions.add(transaction);
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public void showBalance(){
        System.out.println("Current Balance:"+balance);
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public double getBalance(){
        return balance;
    }



}
