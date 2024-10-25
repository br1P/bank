package org.example.util;

import org.example.MyBatis.Service.DepartmentService;
import org.example.model.Department;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class MenuDepartmentMB {

    private static final Logger logger = Logger.getLogger(MenuDepartmentMB.class.getName());
    private final DepartmentService departmentService;
    private final Scanner scanner;
    private final SqlSession sqlSession;

    public MenuDepartmentMB(DepartmentService departmentService, SqlSession sqlSession) {
        this.departmentService = departmentService;
        this.scanner = new Scanner(System.in);
        this.sqlSession = sqlSession;
    }

    public void showMenu() {
        while (true) {
            System.out.println("Department Menu:");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View All Departments");
            System.out.println("5. View Department by ID");
            System.out.println("6. Go Back to Main Menu");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    addDepartment();
                    break;
                case 2:
                    updateDepartment();
                    break;
                case 3:
                    deleteDepartment();
                    break;
                case 4:
                    viewAllDepartments();
                    break;
                case 5:
                    viewDepartmentById();
                    break;
                case 6:
                    return; // Regresar al menú principal
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void addDepartment() {
        System.out.print("Enter Department Name: ");
        String departmentName = scanner.next();

        Department department = new Department(0, departmentName); // ID will be auto-generated
        departmentService.createDepartment(department);
        sqlSession.commit(); // Commit the transaction
        System.out.println("Department added successfully: " + department);
    }

    private void updateDepartment() {
        System.out.print("Enter the Department ID you want to update: ");
        int departmentID = scanner.nextInt();

        Department department = departmentService.getDepartment(departmentID);
        if (department != null) {
            System.out.print("Enter new Department Name: ");
            String newDepartmentName = scanner.next();

            department.setDepartmentName(newDepartmentName);
            departmentService.updateDepartment(department);
            sqlSession.commit(); // Commit the transaction
            System.out.println("Department updated successfully: " + department);
        } else {
            System.out.println("Department with this ID was not found.");
        }
    }

    private void deleteDepartment() {
        System.out.print("Enter the Department ID you want to delete: ");
        int departmentID = scanner.nextInt();

        departmentService.deleteDepartment(departmentID);
        sqlSession.commit(); // Commit the transaction
        System.out.println("Department with ID " + departmentID + " deleted successfully.");
    }

    private void viewAllDepartments() {
        System.out.println("Fetching all departments...");
        List<Department> departments = departmentService.getAllDepartments();
        departments.forEach(department -> System.out.println(department));
    }

    private void viewDepartmentById() {
        System.out.print("Enter the Department ID you want to view: ");
        int departmentID = scanner.nextInt();

        Department department = departmentService.getDepartment(departmentID);
        if (department != null) {
            System.out.println("Department details: " + department);
        } else {
            System.out.println("Department with this ID was not found.");
        }
    }
}
