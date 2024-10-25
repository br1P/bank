package org.example.util;

import org.example.model.Account;
import org.example.MyBatis.Service.AccountService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Scanner;

public class MenuAccountMB {

    private final AccountService accountService;
    private final Scanner scanner;
    private final SqlSession sqlSession;

    public MenuAccountMB(AccountService accountService, SqlSession sqlSession) {
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
        this.sqlSession = sqlSession;
    }

    public void showMenu() {
        while (true) {
            System.out.println("Account Menu:");
            System.out.println("1. Add Account");
            System.out.println("2. Update Account");
            System.out.println("3. Delete Account");
            System.out.println("4. View All Accounts");
            System.out.println("5. View Account by ID");
            System.out.println("6. Go Back to Main Menu");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addAccount();
                    break;
                case 2:
                    updateAccount();
                    break;
                case 3:
                    deleteAccount();
                    break;
                case 4:
                    viewAllAccounts();
                    break;
                case 5:
                    viewAccountById();
                    break;
                case 6:
                    return; // Regresar al menú principal
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void addAccount() {
        System.out.print("Enter Customer ID: ");
        int customerID = scanner.nextInt();
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter Balance: ");
        double balance = scanner.nextDouble();

        Account account = new Account(0, customerID, accountNumber, balance); // ID will be auto-generated
        accountService.createAccount(account);
        sqlSession.commit(); // Commit the transaction
        System.out.println("Account added successfully: " + account);
    }

    private void updateAccount() {
        System.out.print("Enter the Account ID you want to update: ");
        int accountID = scanner.nextInt();

        Account account = accountService.getAccountById(accountID);
        if (account != null) {
            System.out.print("Enter new Customer ID: ");
            int newCustomerID = scanner.nextInt();
            System.out.print("Enter new Account Number: ");
            String newAccountNumber = scanner.next();
            System.out.print("Enter new Balance: ");
            double newBalance = scanner.nextDouble();

            account.setCustomerID(newCustomerID);
            account.setAccountNumber(newAccountNumber);
            account.setBalance(newBalance);

            accountService.updateAccount(account);
            sqlSession.commit(); // Commit the transaction
            System.out.println("Account updated successfully: " + account);
        } else {
            System.out.println("Account with this ID was not found.");
        }
    }

    private void deleteAccount() {
        System.out.print("Enter the Account ID you want to delete: ");
        int accountID = scanner.nextInt();

        accountService.deleteAccount(accountID);
        sqlSession.commit(); // Commit the transaction
        System.out.println("Account with ID " + accountID + " deleted successfully.");
    }

    private void viewAllAccounts() {
        System.out.println("Fetching all accounts...");
        List<Account> accounts = accountService.getAllAccounts();
        accounts.forEach(account -> System.out.println(account));
    }

    private void viewAccountById() {
        System.out.print("Enter the Account ID you want to view: ");
        int accountID = scanner.nextInt();

        Account account = accountService.getAccountById(accountID);
        if (account != null) {
            System.out.println("Account details: " + account);
        } else {
            System.out.println("Account with this ID was not found.");
        }
    }
}
