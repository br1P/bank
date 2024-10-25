package org.example.util;

import org.example.model.Person;
import org.example.MyBatis.Service.PersonService;
import org.apache.ibatis.session.SqlSession;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPersonMB {

    private static final Logger logger = Logger.getLogger(MenuPersonMB.class.getName());
    private final PersonService personService;
    private final Scanner scanner;
    private final SqlSession session; // Agregado para la sesión

    public MenuPersonMB(PersonService personService, SqlSession session) {
        this.personService = personService;
        this.scanner = new Scanner(System.in);
        this.session = session; // Inicialización de la sesión
    }

    public void showMenu() {
        while (true) {
            System.out.println("Person Menu:");
            System.out.println("1. Add Person");
            System.out.println("2. Update Person");
            System.out.println("3. Delete Person");
            System.out.println("4. View All Persons");
            System.out.println("5. View Person by ID");
            System.out.println("6. Go Back to Main Menu");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addPerson();
                    break;
                case 2:
                    updatePerson();
                    break;
                case 3:
                    deletePerson();
                    break;
                case 4:
                    viewAllPersons();
                    break;
                case 5:
                    viewPersonById();
                    break;
                case 6:
                    return; // Regresar al menú principal
                default:
                    logger.warning("Invalid option, please try again.");
            }
        }
    }

    private void addPerson() {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        Person person = new Person(0, name, lastName); // ID will be auto-generated
        personService.createPerson(person);
        session.commit(); // Confirmar la sesión después de la creación
        System.out.println("Person added successfully: " + person);
    }

    private void updatePerson() {
        System.out.print("Enter the ID of the person you want to update: ");
        int id = scanner.nextInt();

        Person person = personService.getPersonById(id);
        if (person != null) {
            System.out.print("Enter new name: ");
            String newName = scanner.next();
            System.out.print("Enter new last name: ");
            String newLastName = scanner.next();

            person.setName(newName);
            person.setLastName(newLastName);

            personService.updatePerson(person);
            session.commit(); // Confirmar la sesión después de la actualización
            System.out.println("Person updated successfully: " + person);
        } else {
            logger.warning("Person with this ID was not found.");
        }
    }

    private void deletePerson() {
        System.out.print("Enter the ID of the person you want to delete: ");
        int id = scanner.nextInt();

        personService.deletePerson(id);
        session.commit(); // Confirmar la sesión después de la eliminación
        System.out.println("Person with ID " + id + " deleted successfully.");
    }

    private void viewAllPersons() {
        System.out.println("Fetching all persons...");
        personService.getAllPersons().forEach(person -> System.out.println(person));
    }

    private void viewPersonById() {
        System.out.print("Enter the ID of the person you want to view: ");
        int id = scanner.nextInt();

        Person person = personService.getPersonById(id);
        if (person != null) {
            System.out.println("Person details: " + person);
        } else {
            logger.warning("Person with this ID was not found.");
        }
    }
}
