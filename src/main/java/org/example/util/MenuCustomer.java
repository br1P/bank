package org.example.util;


import org.example.DAO.CustomerDAO;
import org.example.DAO.AccountDAO;
import org.example.model.Customer;
import org.example.model.Person;
import org.example.model.Account;
import org.example.util.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuCustomer {
    private final CustomerDAO customerDAO;
    private final AccountDAO accountDAO;

    public MenuCustomer() {
        customerDAO = new CustomerDAO();
        accountDAO = new AccountDAO();
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Customer ---");
            System.out.println("1. Create Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. List all Customers");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createCustomer(scanner);
                    break;
                case 2:
                    updateCustomer(scanner);
                    break;
                case 3:
                    deleteCustomer(scanner);
                    break;
                case 4:
                    listCustomers();
                    break;
                case 5:
                    System.out.println("Leaving menu...");
                    ConnectionPool.shutdown();
                    return;
                default:
                    System.out.println("Option not valid. Try again...");
            }
        }
    }

    private void createCustomer(Scanner scanner) {
        try {

            System.out.println("Client name:");
            String name = scanner.nextLine();
            System.out.println("Client lastname:");
            String lastName = scanner.nextLine();
            Person person = new Person(name, lastName);


            System.out.println("\n--- Creating new account ---");
            System.out.println("Choose an account number:");
            String accountNumber = scanner.nextLine();
            System.out.println("Balance:");
            double initialBalance = scanner.nextDouble();
            scanner.nextLine();


            Account newAccount = new Account(accountNumber, initialBalance);
            int newAccountID = accountDAO.insert(newAccount);
            newAccount.setAccountID(newAccountID);


            Customer customer = new Customer(person, newAccountID);
            customerDAO.insert(customer);

            System.out.println("Customer and account created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateCustomer(Scanner scanner) {
        try {

            System.out.println("\n--- Customers ---");
            List<Customer> customers = customerDAO.getAll();
            for (Customer customer : customers) {
                System.out.println(customer.getCustomerID() + ": " + customer.getPerson().getName() + " " + customer.getPerson().getLastName());
            }


            System.out.println("Select Customer Id to update:");
            int customerID = scanner.nextInt();
            scanner.nextLine();

            Customer customer = customerDAO.get(customerID);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }


            System.out.println("\n--- list of accounts ---");
            List<Account> accounts = accountDAO.getAll();
            for (Account account : accounts) {
                System.out.println(account.getAccountID() + ": " + account.getAccountNumber() + " - Balance: " + account.getBalance());
            }
            System.out.println("add clients new acc number:");
            int newAccountID = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            customer.setAccountID(newAccountID);
            customerDAO.update(customer);
            System.out.println("Customer updated!.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomer(Scanner scanner) {
        try {

            System.out.println("\n---  Customers ---");
            List<Customer> customers = customerDAO.getAll();
            for (Customer customer : customers) {
                System.out.println(customer.getCustomerID() + ": " + customer.getPerson().getName() + " " + customer.getPerson().getLastName());
            }


            System.out.println("Please select customer id to delete:");
            int customerID = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            Customer customer = customerDAO.get(customerID);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }

            customerDAO.delete(customer);
            System.out.println("Customer delted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listCustomers() {
        try {
            List<Customer> customers = customerDAO.getAll();
            System.out.println("\n--- Customers ---");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
