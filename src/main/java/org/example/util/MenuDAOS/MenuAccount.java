package org.example.util.MenuDAOS;

import org.example.DAO.AccountDAO;
import org.example.model.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuAccount {

    private static final AccountDAO accountDAO = new AccountDAO();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" --- Account Menu ---");
            System.out.println("1. View all accounts");
            System.out.println("2. Add a new account");
            System.out.println("3. Update account balance");
            System.out.println("4. Delete an account");
            System.out.println("5. Go back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllAccounts();
                    break;
                case 2:
                    addAccount(scanner);
                    break;
                case 3:
                    updateAccountBalance(scanner);
                    break;
                case 4:
                    deleteAccount(scanner);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void viewAllAccounts() {
        try {
            List<Account> accounts = accountDAO.getAll();
            if (accounts.isEmpty()) {
                System.out.println("No accounts found.");
            } else {
                for (Account account : accounts) {
                    System.out.println(account);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching accounts: " + e.getMessage());
        }
    }

    private static void addAccount(Scanner scanner) {
        System.out.println("Enter customer ID:");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();

        System.out.println("Enter initial balance:");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Account account = new Account(0, customerId, accountNumber, balance);

        try {
            int id = accountDAO.save(account);
            if (id > 0) {
                System.out.println("Account added with ID: " + id);
            } else {
                System.out.println("Failed to add account.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding account: " + e.getMessage());
        }
    }

    private static void updateAccountBalance(Scanner scanner) {
        System.out.println("Enter account ID to update:");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Account account = accountDAO.get(accountId);
            if (account == null) {
                System.out.println("Account not found.");
                return;
            }

            System.out.println("Current balance: " + account.getBalance());
            System.out.println("Enter new balance:");
            double newBalance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            account.setBalance(newBalance);
            int rowsUpdated = accountDAO.update(account);
            if (rowsUpdated > 0) {
                System.out.println("Account balance updated successfully.");
            } else {
                System.out.println("Failed to update account balance.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating account balance: " + e.getMessage());
        }
    }

    private static void deleteAccount(Scanner scanner) {
        System.out.println("Enter account ID to delete:");
        int accountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Account account = accountDAO.get(accountId);
            if (account == null) {
                System.out.println("Account not found.");
                return;
            }

            int rowsDeleted = accountDAO.delete(account);
            if (rowsDeleted > 0) {
                System.out.println("Account deleted successfully.");
            } else {
                System.out.println("Failed to delete account.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting account: " + e.getMessage());
        }
    }
}
