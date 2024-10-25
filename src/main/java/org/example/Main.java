package org.example;


import org.apache.ibatis.session.SqlSession;
import org.example.JSON.JsonParser;
import org.example.MyBatis.DAOS.AccountMapper;
import org.example.MyBatis.DAOS.CustomerMapper;
import org.example.MyBatis.DAOS.PersonMapper;
import org.example.MyBatis.Service.AccountService;
import org.example.MyBatis.Service.CustomerService;
import org.example.MyBatis.Service.PersonService;
import org.example.model.Account;
import org.example.model.Banker;
import org.example.model.Customer;
import org.example.model.Person;
import org.example.util.MainMenu;
import org.example.util.MyBatisUtil;
import org.example.util.XMLMenu;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

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


        final String personJson = "src/main/java/org/example/JSON/Person.JSON";
        final String bankerJson = "src/main/java/org/example/JSON/Banker.JSON";
       //region PersonExample
        //this is for showing person with JSON and Jackson

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
        //region BankerExample
        /*
        JsonParser<Banker> bankerParser = new JsonParser<>(Banker.class);

        Banker banker1 = new Banker(201, person1, "L12345", 1, 50000);
        Banker banker2 = new Banker(202, person2, "L67890", 2, 55000);

        bankerParser.serialize(banker1, bankerJson);
        bankerParser.serialize(banker2, bankerJson);

        List<Banker> bankers = bankerParser.deserialize(bankerJson);
        for (Banker banker : bankers) {
            System.out.println(banker);
        } */
        //endregion BankerExample
        SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession();

        try {
            // Obtener mappers
            PersonMapper personMapper = session.getMapper(PersonMapper.class);
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
            PersonService personService = new PersonService(personMapper);
            AccountService accountService = new AccountService(accountMapper);
            CustomerService customerService = new CustomerService(customerMapper);

            // Ejemplo: Crear una nueva persona
            Person person = new Person(0, "Jim", "Harper"); // ID = 0 will be auto-generated
            personService.createPerson(person);
            session.commit();
            logger.log(Level.INFO, "Person succesfully added: {0}", person);

            // Ejemplo: Crear una nueva cuenta
            Account account = new Account(0, person.getPersonID(), "ACC3213456", 5000.00);
            accountService.createAccount(account);
            session.commit();
            logger.log(Level.INFO, "Account successfully created: {0}", account);

            // Ejemplo: Crear un nuevo cliente
            Customer customer = new Customer(0, person, account.getAccountID());
            customerService.createCustomer(customer);
            session.commit();
            logger.log(Level.INFO, "Client created: {0}", customer);

            // Mostrar todos los clientes
            List<Customer> customers = customerService.getAllCustomers();
            logger.info("List of clients:");
            for (Customer c : customers) {
                logger.log(Level.INFO, "{0}", c);
            }

        } catch (Exception e) {
            session.rollback();
            logger.log(Level.SEVERE, "Error : ", e);
        } finally {
            session.close();
        }
    }
}
