package org.example;


import org.example.util.MainMenu;
import org.example.util.XMLMenu;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option:");
        System.out.println("1. Database");
        System.out.println("2. XML");
        int dataSourceOption = scanner.nextInt();

        if (dataSourceOption == 1) {
            MainMenu mainMenu = new MainMenu();
            mainMenu.displayMainMenu();
        } else if (dataSourceOption == 2) {
            XMLMenu.showMenu();
        } else {
            System.out.println("Option not valid.");
        }



    }
}
