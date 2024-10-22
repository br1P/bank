package org.example;


import org.example.JSON.JsonParser;
import org.example.model.Banker;
import org.example.model.Person;
import org.example.util.MainMenu;
import org.example.util.XMLMenu;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
       //region oldMenu
        /* Scanner scanner = new Scanner(System.in);
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

        */
        //endregion oldmenu


        final String personJson = "C:\\Users\\bruno\\Desktop\\Bank2\\src\\main\\java\\org\\example\\JSON\\Person.JSON";
        final String bankerJson = "C:\\Users\\bruno\\Desktop\\Bank2\\src\\main\\java\\org\\example\\JSON\\Banker.JSON";
       //region PersonExample

        //JsonParser<Person> jsonParser = new JsonParser<>(Person.class);


        Person person1 = new Person(101, "John", "Doe");
       // jsonParser.serialize(person1, personJson);

        Person person2 = new Person(102, "Chris", "Phrat");
        //jsonParser.serialize(person2, personJson);


//        List<Person> persons = jsonParser.deserialize(personJson);
//        for (Person person : persons) {
//            System.out.println(person);
//        }
        //endregion personExample

        JsonParser<Banker> bankerParser = new JsonParser<>(Banker.class);
        // Create some Banker objects using the Person objects
        Banker banker1 = new Banker(201, person1, "L12345", 1, 50000);
        Banker banker2 = new Banker(202, person2, "L67890", 2, 55000);

        // Serialize new Banker objects
        bankerParser.serialize(banker1, bankerJson);
        bankerParser.serialize(banker2, bankerJson);

        // Deserialize and display all Banker objects
        List<Banker> bankers = bankerParser.deserialize(bankerJson);
        for (Banker banker : bankers) {
            System.out.println(banker);
        }
    }
}
