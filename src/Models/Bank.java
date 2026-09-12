package Models;

import Utils.BankUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Bank {
    private final String id;
    private final String name;
    private final List<Account> accounts;

    public Bank(String name) {
        this.id = BankUtils.generateId();
        this.name = validateName(name);
        this.accounts = new ArrayList<>();
    }

    public Bank() {
        this("Bank");
    }

    public void addAccount(Account account) {
        Objects.requireNonNull(account, "Account cannot be null.");

        if (account.getBank() != null && account.getBank() != this) {
            throw new IllegalArgumentException("Account already belongs to another bank.");
        }
        if (accounts.contains(account)) {
            throw new IllegalArgumentException("Account is already registered in this bank.");
        }

        account.assignBank(this);
        accounts.add(account);
    }

    public Account createAccount(Customer customer) {
        Account account = new Account(customer);
        addAccount(account);
        return account;
    }

    public void removeAccount(Account account) {
        Objects.requireNonNull(account, "Account cannot be null.");
        if (!accounts.remove(account)) {
            throw new IllegalArgumentException("Account does not belong to this bank.");
        }
        account.assignBank(null);
    }

    public Account findAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "Account number cannot be null.");
        return accounts.stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    private static String validateName(String name) {
        Objects.requireNonNull(name, "Bank name cannot be null.");
        String trimmedName = name.trim();
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be empty.");
        }
        return trimmedName;
    }
}
