package org.example.util;

import org.example.MyBatis.Service.BankerService;
import org.example.MyBatis.Service.PersonService;
import org.example.MyBatis.Service.DepartmentService;
import org.example.model.Banker;
import org.example.model.Person;
import org.example.model.Department;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Scanner;

public class MenuBankerMB {
    private final BankerService bankerService;
    private final PersonService personService;
    private final DepartmentService departmentService;
    private final Scanner scanner;
    private final SqlSession session; // Agregado para la sesión

    public MenuBankerMB(BankerService bankerService, PersonService personService, DepartmentService departmentService, SqlSession session) {
        this.bankerService = bankerService;
        this.personService = personService;
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
        this.session = session; // Inicialización de la sesión
    }

    public void showMenu() {
        while (true) {
            System.out.println("Banker Menu:");
            System.out.println("1. Add Banker");
            System.out.println("2. View Banker");
            System.out.println("3. Update Banker");
            System.out.println("4. Delete Banker");
            System.out.println("5. List all Bankers");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addBanker();
                    break;
                case 2:
                    viewBanker();
                    break;
                case 3:
                    updateBanker();
                    break;
                case 4:
                    deleteBanker();
                    break;
                case 5:
                    listAllBankers();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void addBanker() {
        System.out.print("Enter Person Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Person Last Name: ");
        String lastName = scanner.nextLine();

        Person person = new Person(name, lastName);
        personService.createPerson(person);
        session.commit(); // Confirmar la sesión después de la creación de la persona

        List<Department> departments = departmentService.getAllDepartments();
        System.out.println("Available Departments:");
        for (Department department : departments) {
            System.out.println(department);
        }

        System.out.print("Select Department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter License Number: ");
        String licenseNumber = scanner.nextLine();
        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();

        Banker banker = new Banker();
        banker.setPerson(person);
        banker.setDepartmentID(departmentId);
        banker.setLicenseNumber(licenseNumber);
        banker.setSalary(salary);

        bankerService.createBanker(banker);
        session.commit(); // Confirmar la sesión después de la creación del banquero
        System.out.println("Banker added successfully: " + banker);
    }

    private void viewBanker() {
        System.out.print("Enter Banker ID: ");
        int bankerId = scanner.nextInt();
        Banker banker = bankerService.getBankerById(bankerId);
        if (banker != null) {
            System.out.println("Banker Details: " + banker);
        } else {
            System.out.println("Banker not found.");
        }
    }

    private void updateBanker() {
        System.out.print("Enter Banker ID to update: ");
        int bankerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Banker banker = bankerService.getBankerById(bankerId);
        if (banker != null) {
            System.out.print("Enter new Person Name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new Person Last Name: ");
            String newLastName = scanner.nextLine();
            System.out.print("Enter new Department ID: ");
            int newDepartmentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter new License Number: ");
            String newLicenseNumber = scanner.nextLine();
            System.out.print("Enter new Salary: ");
            double newSalary = scanner.nextDouble();

            Person updatedPerson = new Person(banker.getPerson().getPersonID(), newName, newLastName);
            banker.setPerson(updatedPerson);
            banker.setDepartmentID(newDepartmentId);
            banker.setLicenseNumber(newLicenseNumber);
            banker.setSalary(newSalary);

            bankerService.updateBanker(banker);
            session.commit(); // Confirmar la sesión después de la actualización
            System.out.println("Banker updated successfully: " + banker);
        } else {
            System.out.println("Banker not found.");
        }
    }

    private void deleteBanker() {
        System.out.print("Enter Banker ID to delete: ");
        int bankerId = scanner.nextInt();
        bankerService.deleteBanker(bankerId);
        session.commit(); // Confirmar la sesión después de la eliminación
        System.out.println("Banker deleted successfully.");
    }

    private void listAllBankers() {
        List<Banker> bankers = bankerService.getAllBankers();
        for (Banker banker : bankers) {
            System.out.println(banker);
        }
    }
}
