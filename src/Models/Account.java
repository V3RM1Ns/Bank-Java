package Models;

import Interfaces.BankOperation;
import Models.Enums.Type;
import Utils.BankUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Account implements BankOperation {
    private final String accountNumber;
    private final Customer customer;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(Customer customer) {
        this.accountNumber = BankUtils.generateId();
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null.");
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount){
        if (amount<=0) throw new IllegalArgumentException("Deposit amount have to be above 0!");

        balance+=amount;


        Transaction transaction=new Transaction(Type.Deposit,amount);
        transactions.add(transaction);
    }
    public void deposit(double amount,String desc){
        if (amount<=0) throw new IllegalArgumentException("Deposit amount have to be above 0!");

        balance+=amount;

        Transaction transaction=new Transaction(Type.Deposit,amount,desc);
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
        transfer(receiver, amount, null);
    }

    public void transfer(Account receiver, double amount, String description){
        if (receiver == null) throw new IllegalArgumentException("Receiver account not found.");
        if (amount<=0) throw new IllegalArgumentException("Withdraw amount have to be above 0!");

        withdraw(amount);
        String transferDescription = "Gonderen: " + customer.getName();
        if (description != null && !description.isBlank()) {
            transferDescription += " | " + description.trim();
        }
        receiver.deposit(amount, transferDescription);

        Transaction transaction = new Transaction(Type.Transfer, amount, transferDescription);
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

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }


}
