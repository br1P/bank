package org.example.util;



import org.example.DAO.DepartmentDAO;
import org.example.model.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuDepartment {

    private static final DepartmentDAO departmentDAO = new DepartmentDAO();

    public static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(" --- Department Menu ---");
            System.out.println("1. View all departments");
            System.out.println("2. Add a new department");
            System.out.println("3. Update a department");
            System.out.println("4. Delete a department");
            System.out.println("5. Go back");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllDepartments();
                    break;
                case 2:
                    addDepartment(scanner);
                    break;
                case 3:
                    updateDepartment(scanner);
                    break;
                case 4:
                    deleteDepartment(scanner);
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void viewAllDepartments() {
        try {
            List<Department> departments = departmentDAO.getAll();
            if (departments.isEmpty()) {
                System.out.println("No departments found.");
            } else {
                for (Department department : departments) {
                    System.out.println(department);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching departments: " + e.getMessage());
        }
    }

    private static void addDepartment(Scanner scanner) {
        System.out.println("Enter department name:");
        String departmentName = scanner.nextLine();

        Department department = new Department(0, departmentName); // ID 0 as it's auto-generated

        try {
            int id = departmentDAO.save(department);
            if (id != -1) {
                System.out.println("Department added with ID: " + id);
            } else {
                System.out.println("Failed to add department.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
        }
    }

    private static void updateDepartment(Scanner scanner) {
        System.out.println("Enter department ID to update:");
        int departmentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Department department = departmentDAO.get(departmentID);
            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            System.out.println("Current name: " + department.getDepartmentName());
            System.out.println("Enter new name:");
            String newName = scanner.nextLine();
            department.setDepartmentName(newName);

            int rowsUpdated = departmentDAO.update(department);
            if (rowsUpdated > 0) {
                System.out.println("Department updated successfully.");
            } else {
                System.out.println("Failed to update department.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating department: " + e.getMessage());
        }
    }

    private static void deleteDepartment(Scanner scanner) {
        System.out.println("Enter department ID to delete:");
        int departmentID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            Department department = departmentDAO.get(departmentID);
            if (department == null) {
                System.out.println("Department not found.");
                return;
            }

            int rowsDeleted = departmentDAO.delete(department);
            if (rowsDeleted > 0) {
                System.out.println("Department deleted successfully.");
            } else {
                System.out.println("Failed to delete department.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting department: " + e.getMessage());
        }
    }
}
