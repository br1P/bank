package org.example.util;

import org.example.model.Customer;
import org.example.model.Person;
import org.example.model.Account; // Asegúrate de importar el modelo Account
import org.example.MyBatis.Service.CustomerService;
import org.example.MyBatis.Service.AccountService; // Importa AccountService
import org.example.MyBatis.Service.PersonService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Scanner;

public class MenuCustomerMB {

    private final CustomerService customerService;
    private final AccountService accountService;
    private final PersonService personService;
    private final Scanner scanner;
    private final SqlSession session; // Agregamos SqlSession

    public MenuCustomerMB(CustomerService customerService, AccountService accountService, PersonService personService, SqlSession session) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.personService = personService;
        this.scanner = new Scanner(System.in);
        this.session = session; // Inicializamos SqlSession
    }

    public void showMenu() {
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. Delete Customer");
            System.out.println("4. View All Customers");
            System.out.println("5. View Customer by ID");
            System.out.println("6. Go Back to Main Menu");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    updateCustomer();
                    break;
                case 3:
                    deleteCustomer();
                    break;
                case 4:
                    viewAllCustomers();
                    break;
                case 5:
                    viewCustomerById();
                    break;
                case 6:
                    return; // Regresar al menú principal
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void addCustomer() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();

        Account account = new Account(accountNumber, balance);
        accountService.createAccount(account);
        session.commit(); // Commit después de agregar la cuenta

        System.out.print("Enter Person Name: ");
        String name = scanner.next();
        System.out.print("Enter Person Last Name: ");
        String lastName = scanner.next();


        Person person = new Person(0, name, lastName);
        personService.createPerson(person);
        session.commit();


        Customer customer = new Customer(person, account.getAccountID());
        customerService.createCustomer(customer);
        session.commit();

        System.out.println("Customer added successfully: " + customer);
    }

    private void updateCustomer() {
        System.out.print("Enter the Customer ID you want to update: ");
        int customerID = scanner.nextInt();

        Customer customer = customerService.getCustomer(customerID);
        if (customer != null) {
            System.out.print("Enter new Account ID: ");
            int newAccountID = scanner.nextInt();


            if (accountService.getAccountById(newAccountID) == null) {
                System.out.println("The specified Account ID does not exist.");
                System.out.println("Fetching all accounts...");
                List<Account> accounts = accountService.getAllAccounts();
                accounts.forEach(account -> System.out.println(account));
                return;
            }

            System.out.print("Enter new Person Name: ");
            String newName = scanner.next();
            System.out.print("Enter new Person Last Name: ");
            String newLastName = scanner.next();


            Person person = new Person(customer.getPerson().getPersonID(), newName, newLastName);
            personService.updatePerson(person);
            session.commit();


            customer.setAccountID(newAccountID);
            customerService.updateCustomer(customer);
            session.commit();

            System.out.println("Customer updated successfully: " + customer);
        } else {
            System.out.println("Customer with this ID was not found.");
        }
    }

    private void deleteCustomer() {
        System.out.print("Enter the Customer ID you want to delete: ");
        int customerID = scanner.nextInt();

        customerService.deleteCustomer(customerID);
        session.commit();
        System.out.println("Customer with ID " + customerID + " deleted successfully.");
    }

    private void viewAllCustomers() {
        System.out.println("Fetching all customers...");
        List<Customer> customers = customerService.getAllCustomers();
        customers.forEach(customer -> System.out.println(customer));
    }

    private void viewCustomerById() {
        System.out.print("Enter the Customer ID you want to view: ");
        int customerID = scanner.nextInt();

        Customer customer = customerService.getCustomer(customerID);
        if (customer != null) {
            System.out.println("Customer details: " + customer);
        } else {
            System.out.println("Customer with this ID was not found.");
        }
    }
}
