import Models.Account;
import Models.Bank;
import Models.Customer;
import Models.Records.Email;
import Models.Records.Phone;
import Models.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public final class Main {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    private Main() {
    }

    public static void main(String[] args) {
        Customer ali = new Customer(
                "Hebib Ramanzanov",
                new Email("ali.valiyev@example.com"),
                new Phone("+9945012345678")
        );
        Customer leyla = new Customer(
                "Leyla Aliyeva",
                new Email("leyla.hasanli@example.com"),
                new Phone("+9945023456789")
        );

        Bank bank = new Bank("Bank System");
        bank.createAccount(ali);
        bank.createAccount(leyla);

        try (Scanner scanner = new Scanner(System.in)) {
            runMenu(scanner, bank);
        }
    }

    private static void runMenu(Scanner scanner, Bank bank) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt(scanner, "Seciminiz: ");

            try {
                switch (choice) {
                    case 1 -> deposit(scanner, bank.getAccounts());
                    case 2 -> withdraw(scanner, bank.getAccounts());
                    case 3 -> transfer(scanner, bank.getAccounts());
                    case 4 -> showBalances(bank.getAccounts());
                    case 5 -> showAccountInfo(bank.getAccounts());
                    case 6 -> showIdList(bank.getAccounts());
                    case 7 -> showTransactions(scanner, bank.getAccounts());
                    case 0 -> {
                        running = false;
                        System.out.println(GREEN + "Proqram baglandi." + RESET);
                    }
                    default -> System.out.println(RED + "Yanlis secim etdiniz." + RESET);
                }
            } catch (IllegalArgumentException exception) {
                System.out.println(RED + "Xeta: " + exception.getMessage() + RESET);
            }
        }
    }

    private static void printMenu() {
    System.out.println(BOLD + CYAN + "\n========== BANK SYSTEM ==========" + RESET);
    System.out.println(YELLOW + "1." + RESET + " Pul yatir");
    System.out.println(YELLOW + "2." + RESET + " Pul cixar");
    System.out.println(YELLOW + "3." + RESET + " Transfer et");
    System.out.println(YELLOW + "4." + RESET + " Butun balanslara bax");
    System.out.println(YELLOW + "5." + RESET + " Hesab melumatlarina bax");
    System.out.println(YELLOW + "6." + RESET + " Hesab ID-lerini goster");
    System.out.println(YELLOW + "7." + RESET + " Transaction tarixcesine bax");
    System.out.println(YELLOW + "0." + RESET + " Cixis");
}

    private static void deposit(Scanner scanner, List<Account> accounts) {
    Account account = selectAccount(scanner, accounts, "Pul yatirilacaq hesab");
    double amount = readAmount(scanner);
    System.out.print("Aciqlama (bos qala biler): ");
    String description = scanner.nextLine().trim();

    if (description.isBlank()) {
        account.deposit(amount);
    } else {
        account.deposit(amount, description);
    }

    System.out.println(GREEN + "Pul ugurla yatirildi." + RESET);
}

    private static void withdraw(Scanner scanner, List<Account> accounts) {
    Account account = selectAccount(scanner, accounts, "Pul cixarilacaq hesab");
    account.withdraw(readAmount(scanner));
    System.out.println(GREEN + "Pul ugurla cixarildi." + RESET);
}

    private static void transfer(Scanner scanner, List<Account> accounts) {
    Account sender = selectAccount(scanner, accounts, "Gonderen hesab");
    Account receiver = selectAccount(scanner, accounts, "Alan hesab");

    if (sender == receiver) {
        throw new IllegalArgumentException("Gonderen ve alan hesab eyni ola bilmez.");
    }

    double amount = readAmount(scanner);
    String description = readText(scanner, "Transfer aciqlamasi: ");
    sender.transfer(receiver, amount, description);
    System.out.println(GREEN + "Transfer ugurla tamamlandi." + RESET);
}

    private static Account selectAccount(Scanner scanner, List<Account> accounts, String title) {
    System.out.println(BLUE + "\n" + title + ":" + RESET);
    showIdList(accounts);

    while (true) {
        int accountNumber = readInt(scanner, "Hesab nomresi: ");
        if (accountNumber >= 1 && accountNumber <= accounts.size()) {
            return accounts.get(accountNumber - 1);
        }
        System.out.println(RED + "Bu nomreye uygun hesab tapilmadi." + RESET);
    }
}

    private static void showBalances(List<Account> accounts) {
    System.out.println(BLUE + "\n--- Balanslar ---" + RESET);
    for (Account account : accounts) {
        System.out.printf(
                "%s | %s | %.2f AZN%n",
                account.getAccountNumber(),
                account.getCustomer().getName(),
                account.getBalance()
        );
    }
}

    private static void showAccountInfo(List<Account> accounts) {
    System.out.println(BLUE + "\n--- Hesab melumatlari ---" + RESET);
    for (Account account : accounts) {
        Customer customer = account.getCustomer();
        System.out.printf(
                "Hesab ID: %s%nSahib ID: %s%nAd: %s%nEmail: %s%nTelefon: %s%nBalans: %.2f AZN%n%n",
                account.getAccountNumber(),
                customer.getId(),
                customer.getName(),
                customer.getEmail().value(),
                customer.getPhone().value(),
                account.getBalance()
        );
    }
}

    private static void showIdList(List<Account> accounts) {
    for (int index = 0; index < accounts.size(); index++) {
        Account account = accounts.get(index);
        System.out.printf(
                "%d) %s%n",
                index + 1,
                account.getCustomer().getName()
        );
    }
}

    private static void showTransactions(Scanner scanner, List<Account> accounts) {
    Account account = selectAccount(scanner, accounts, "Transactionlarina baxilacaq hesab");
    List<Transaction> transactions = account.getTransactions();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    System.out.println(BLUE + "\n--- Transaction tarixcesi ---" + RESET);
    if (transactions.isEmpty()) {
        System.out.println(YELLOW + "Bu hesabda hele transaction yoxdur." + RESET);
        return;
    }

    for (Transaction transaction : transactions) {
        String description = transaction.getDescription();
        System.out.printf(
                "%s | %s | %.2f AZN | Hesab sahibi: %s%s%n",
                transaction.getDate().format(formatter),
                transaction.getType(),
                transaction.getAmount(),
                account.getCustomer().getName(),
                description == null || description.isBlank()
                        ? ""
                        : " | Aciqlama: " + description
        );
    }
}

    private static int readInt(Scanner scanner, String message) {
    while (true) {
        System.out.print(message);
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            System.out.println(RED + "Tam eded daxil edin." + RESET);
        }
    }
}

    private static double readAmount(Scanner scanner) {
    while (true) {
        System.out.print("Mebleg: ");
        String input = scanner.nextLine().trim().replace(',', '.');
        try {
            double amount = Double.parseDouble(input);
            if (amount <= 0) {
                throw new NumberFormatException();
            }
            return amount;
        } catch (NumberFormatException exception) {
            System.out.println(RED + "Musbet mebleg daxil edin." + RESET);
        }
    }
}

    private static String readText(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }
}
