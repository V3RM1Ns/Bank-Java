import Models.Account;
import Models.Customer;
import Models.Records.Email;
import Models.Records.Phone;
import Models.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

final String RESET = "\u001B[0m";
final String RED = "\u001B[31m";
final String GREEN = "\u001B[32m";
final String YELLOW = "\u001B[33m";
final String BLUE = "\u001B[34m";
final String CYAN = "\u001B[36m";
final String BOLD = "\u001B[1m";

void main() {
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

    List<Account> accounts = List.of(
            new Account(ali),
            new Account(leyla)
    );

    try (Scanner scanner = new Scanner(System.in)) {
        runMenu(scanner, accounts);
    }
}

void runMenu(Scanner scanner, List<Account> accounts) {
    boolean running = true;

    while (running) {
        printMenu();
        int choice = readInt(scanner, "Seciminiz: ");

        try {
            switch (choice) {
                case 1 -> deposit(scanner, accounts);
                case 2 -> withdraw(scanner, accounts);
                case 3 -> transfer(scanner, accounts);
                case 4 -> showBalances(accounts);
                case 5 -> showAccountInfo(accounts);
                case 6 -> showIdList(accounts);
                case 7 -> showTransactions(scanner, accounts);
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

void printMenu() {
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

void deposit(Scanner scanner, List<Account> accounts) {
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

void withdraw(Scanner scanner, List<Account> accounts) {
    Account account = selectAccount(scanner, accounts, "Pul cixarilacaq hesab");
    account.withdraw(readAmount(scanner));
    System.out.println(GREEN + "Pul ugurla cixarildi." + RESET);
}

void transfer(Scanner scanner, List<Account> accounts) {
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

Account selectAccount(Scanner scanner, List<Account> accounts, String title) {
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

void showBalances(List<Account> accounts) {
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

void showAccountInfo(List<Account> accounts) {
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

void showIdList(List<Account> accounts) {
    for (int index = 0; index < accounts.size(); index++) {
        Account account = accounts.get(index);
        System.out.printf(
                "%d) %s%n",
                index + 1,
                account.getCustomer().getName()
        );
    }
}

void showTransactions(Scanner scanner, List<Account> accounts) {
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

int readInt(Scanner scanner, String message) {
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

double readAmount(Scanner scanner) {
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

String readText(Scanner scanner, String message) {
    System.out.print(message);
    return scanner.nextLine().trim();
}
