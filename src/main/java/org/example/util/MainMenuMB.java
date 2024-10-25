package org.example.util;

import org.apache.ibatis.session.SqlSession;
import org.example.MyBatis.DAOS.BankerMapper;
import org.example.MyBatis.DAOS.PersonMapper;
import org.example.MyBatis.DAOS.AccountMapper;
import org.example.MyBatis.DAOS.CustomerMapper;
import org.example.MyBatis.DAOS.DepartmentMapper;

import org.example.MyBatis.Service.PersonService;
import org.example.MyBatis.Service.CustomerService;
import org.example.MyBatis.Service.BankerService;
import org.example.MyBatis.Service.AccountService;
import org.example.MyBatis.Service.DepartmentService;
import org.example.util.MenuDAOS.MenuAccount;
import org.example.util.MenuDAOS.MenuPerson;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuMB {

    private static final Logger logger = Logger.getLogger(MainMenuMB.class.getName());

    public void displayMainMenu() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            // mappers
            PersonMapper personMapper = session.getMapper(PersonMapper.class);
            CustomerMapper customerMapper = session.getMapper(CustomerMapper.class);
            BankerMapper bankerMapper = session.getMapper(BankerMapper.class);
            AccountMapper accountMapper = session.getMapper(AccountMapper.class);
            DepartmentMapper departmentMapper = session.getMapper(DepartmentMapper.class);

            // Services
            PersonService personService = new PersonService(personMapper);
            CustomerService customerService = new CustomerService(customerMapper);
            BankerService bankerService = new BankerService(bankerMapper);
            AccountService accountService = new AccountService(accountMapper);
            DepartmentService departmentService = new DepartmentService(departmentMapper);

            // Menus
            MenuPersonMB menuPerson = new MenuPersonMB(personService, session);
            MenuCustomerMB menuCustomer = new MenuCustomerMB(customerService, accountService, personService, session);
            MenuBankerMB menuBanker = new MenuBankerMB(bankerService, personService, departmentService, session);
            MenuAccountMB menuAccount = new MenuAccountMB(accountService, session);
            MenuDepartmentMB menuDepartment = new MenuDepartmentMB(departmentService, session);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Select an option:");
                System.out.println("1. Person Menu");
                System.out.println("2. Customer Menu");
                System.out.println("3. Banker Menu");
                System.out.println("4. Account Menu");
                System.out.println("5. Department Menu");
                System.out.println("6. Exit");

                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        menuPerson.showMenu();
                        session.commit(); // Commit después de las operaciones en el menú de personas
                        break;
                    case 2:
                        menuCustomer.showMenu();
                        session.commit(); // Commit después de las operaciones en el menú de clientes
                        break;
                    case 3:
                        menuBanker.showMenu();
                        session.commit(); // Commit después de las operaciones en el menú de banqueros
                        break;
                    case 4:
                        menuAccount.showMenu();
                        session.commit(); // Commit después de las operaciones en el menú de cuentas
                        break;
                    case 5:
                        menuDepartment.showMenu();
                        session.commit(); // Commit después de las operaciones en el menú de departamentos
                        break;
                    case 6:
                        logger.info("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        logger.warning("Invalid option, please try again.");
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occurred while running the menu: ", e);
            // Aquí puedes realizar un rollback si lo deseas
        }
    }
}
