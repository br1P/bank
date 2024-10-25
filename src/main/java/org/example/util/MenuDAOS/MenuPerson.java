package org.example.util.MenuDAOS;


import org.example.DAO.PersonDAO;
import org.example.model.Person;

import java.sql.SQLException;
import java.util.Scanner;


public class MenuPerson {

    private static PersonDAO personDAO = new PersonDAO();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n---  Person ---");
            System.out.println("1. add Person");
            System.out.println("2. update Person");
            System.out.println("3. show 1 Person");
            System.out.println("4. delete Person");
            System.out.println("5. Go Back");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addPerson();
                    break;
                case 2:
                    updatePerson();
                    break;
                case 3:
                    showPerson();
                    break;
                case 4:
                    deletePerson();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Whoops, not valid option.");
            }
        }
    }

    private static void addPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("name:");
        String name = scanner.nextLine();
        System.out.println("lastname:");
        String lastName = scanner.nextLine();


        Person person = new Person(name, lastName);

        try {

            int personId = personDAO.insert(person);
            System.out.println("Person added. ID: " + personId);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updatePerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Id for updating:");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {

            Person person = personDAO.get(id);
            if (person != null) {
                System.out.println("add new name (actual: " + person.getName() + "):");
                String name = scanner.nextLine();
                System.out.println("add new lastname (actual: " + person.getLastName() + "):");
                String lastName = scanner.nextLine();


                person.setName(name);
                person.setLastName(lastName);


                personDAO.update(person);
                System.out.println("Person updated.");
            } else {
                System.out.println("Persona not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    private static void showPerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("choose a person id:");
        int id = scanner.nextInt();

        try {

            Person person = personDAO.get(id);
            if (person != null) {
                System.out.println(person);
            } else {
                System.out.println("Person not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deletePerson() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose person IDs to delete:");
        int id = scanner.nextInt();

        try {

            Person person = personDAO.get(id);
            if (person != null) {

                System.out.println("¿Are you sure you want to delete " + person.getName() + " " + person.getLastName() + "? (S/N)");
                String confirm = scanner.next();
                if (confirm.equalsIgnoreCase("S")) {
                    // Eliminar la persona usando el objeto person
                    personDAO.delete(person);
                    System.out.println("Person deleted.");
                } else {
                    System.out.println("Elimination canceled.");
                }
            } else {
                System.out.println("Persona not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
