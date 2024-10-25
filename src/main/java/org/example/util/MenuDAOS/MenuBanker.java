package org.example.util.MenuDAOS;

import org.example.DAO.BankerDAO;
import org.example.DAO.DepartmentDAO;
import org.example.model.Banker;
import org.example.model.Department;
import org.example.model.Person;
import org.example.util.ConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuBanker {
    private final Scanner scanner;
    private final BankerDAO bankerDAO;
    private final DepartmentDAO departmentDAO;

    public MenuBanker(Scanner scanner, BankerDAO bankerDAO, DepartmentDAO departmentDAO) {
        this.scanner = scanner;
        this.bankerDAO = bankerDAO;
        this.departmentDAO = departmentDAO;
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n--- Banker Menu ---");
            System.out.println("1. Create Banker");
            System.out.println("2. List Bankers");
            System.out.println("3. List Departments");
            System.out.println("4. delete Banker");
            System.out.println("5. update Banker");
            System.out.println("6. exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // this clean the buffer

            switch (option) {
                case 1:
                    createBanker();
                    break;

                case 2:
                    listBankers();
                    break;

                case 3:
                    listDepartments();
                    break;

                case 4:
                    deleteBanker();
                    break;
                case 5:
                    updateBanker();
                    break;
                case 6:
                    return;
                    //exitProgram();


                default:
                    System.out.println("Option was not valid. Try again please.");
            }
        }
    }

    private void createBanker() {
        try {

            System.out.println("Add name: ");
            String name = scanner.nextLine();
            System.out.println("Add lastname: ");
            String lastName = scanner.nextLine();


            Person person = new Person(name, lastName);


            System.out.println("\n--- List of Departments ---");
            List<Department> departments = departmentDAO.getAll();
            for (Department department : departments) {
                System.out.println(department.getDepartmentID() + ": " + department.getDepartmentName());
            }


            System.out.println("Add department id for the banker: ");
            int departmentID = scanner.nextInt();
            scanner.nextLine(); // clean buffer

            // Validate dept
            Department selectedDepartment = departmentDAO.get(departmentID);
            if (selectedDepartment == null) {
                System.out.println("Department not found. Try again.");
                return;
            }

            // Ask aditional info here
            System.out.println("Add number of banker licence : ");
            String licenseNumber = scanner.nextLine();
            System.out.println("Add salary: ");
            double salary = scanner.nextDouble();

            // Crear el Banker
            Banker banker = new Banker(person, licenseNumber, departmentID, salary);

            // Insert el Banker en la base de datos
            bankerDAO.insert(banker);

            System.out.println("Banker created succesfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listBankers() {
        // Bank list here
        try {
            List<Banker> bankers = bankerDAO.getAll();
            System.out.println("\n--- List of Bankers ---");
            for (Banker banker : bankers) {
                System.out.println(banker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listDepartments() {
        // Dept list here
        try {
            List<Department> departmentsList = departmentDAO.getAll();
            System.out.println("\n--- List of Departments ---");
            for (Department department : departmentsList) {
                System.out.println(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void exitProgram() {
        // exit
        System.out.println("Leaving banker...");
        ConnectionPool.shutdown();
        scanner.close();
        System.exit(0);
    }
    // Método para eliminar un Banker
    public void deleteBanker() {
        try {
            System.out.println("Please specify bankers ID to delete: ");
            listBankers();
            int bankerID = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            // Obtener el Banker a eliminar
            Banker banker = bankerDAO.get(bankerID);
            if (banker != null) {
                bankerDAO.delete(banker);
                System.out.println("Banker deleted.");
            } else {
                System.out.println("Banker not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // update Banker
    public void updateBanker() {
        try {
            System.out.println("Write Banker IDs to update: ");
            listBankers();

            int bankerID = scanner.nextInt();
            scanner.nextLine(); // cleans the buffer

            // get the banker first here
            Banker banker = bankerDAO.get(bankerID);
            if (banker != null) {
                System.out.println("Add the new license number (actual one: " + banker.getLicenseNumber() + "): ");
                String newLicenseNumber = scanner.nextLine();
                banker.setLicenseNumber(newLicenseNumber);

                System.out.println("Add the new Salary (actual one: " + banker.getSalary() + "): ");
                double newSalary = scanner.nextDouble();
                banker.setSalary(newSalary);

                // update the dbb
                bankerDAO.update(banker);
                System.out.println("Banker updated successfully.");
            } else {
                System.out.println("Banker not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
