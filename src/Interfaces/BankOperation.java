package Interfaces;

import Models.Account;

public interface BankOperation {

    void deposit(double amount);

    void withdraw(double amount);

    void transfer(Account receiver, double amount);
}