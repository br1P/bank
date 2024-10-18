package org.example.util;

import org.example.DAO.BankerDAO;
import org.example.DAO.CustomerDAO;
import org.example.DAO.DepartmentDAO;
import org.example.util.ConnectionPool;

import java.util.Scanner;

public class MainMenu {

    public void displayMainMenu() {
        Scanner scanner = new Scanner(System.in);

        //  DAOs instances here
        BankerDAO bankerDAO = new BankerDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        DepartmentDAO departmentDAO = new DepartmentDAO();

        // menu instances
        MenuPerson menuPerson = new MenuPerson();
        MenuCustomer menuCustomer = new MenuCustomer();
        MenuBanker menuBanker = new MenuBanker(scanner, bankerDAO, departmentDAO);
        MenuDepartment menuDepartment = new MenuDepartment();
        MenuAccount menuAccount = new MenuAccount();


        while (true) {
            System.out.println("Select a table to work with:");
            System.out.println("1. Person");
            System.out.println("2. Customer");
            System.out.println("3. Banker");
            System.out.println("4. Department");
            System.out.println("5. Account");
            System.out.println("6. Exit");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    MenuPerson.showMenu();
                    break;
                case 2:
                    menuCustomer.showMenu();
                    break;
                case 3:
                    menuBanker.showMenu();
                    break;
                case 4:

                    MenuDepartment.showMenu();
                    break;
                case 5:

                    MenuAccount.showMenu();
                    break;
                case 6:
                    System.out.println("Leaving...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Mistakes where made...Try again");
            }
        }
    }
}
