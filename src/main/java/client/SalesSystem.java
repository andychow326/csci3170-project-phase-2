package client;

import java.sql.SQLException;
import java.util.Scanner;

public class SalesSystem {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    // constructor
    public SalesSystem(DatabaseClient dbClient) {
        this.db = dbClient;
    }

    public void execute() {
        System.out.println("Welcome to sales system!");
        displayMainMenu();
    }

    private void displayMainMenu() {
        Boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Main Menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");
            isExit = selectRole();
        }
    }

    private Boolean selectRole() {
        System.out.print("Enter Your Choice: ");
        int choice = 0;
        Boolean isExit = false;
        try {
            choice = input.nextInt();
        } catch (Exception e) {
            System.out.println("ERROR!! You did not input a integer!");
        }
        switch (choice) {
            case 1:
                Administrator admin = new Administrator(db);
                break;
            case 2:
                SalesPerson sales = new SalesPerson(db);
                break;
            case 3:
                Manager head = new Manager(db);
                break;
            case 4:
                isExit = true;
                break;
            default:
                System.out.println("ERROR!! Input must be within 1 to 4!");
                break;
        }
        return isExit;
    }
}
/*
 * here is the code to start the project
 * javac xxx.java
 * java -classpath ./mysql-jdbc.jar:./ xxx
 * Here xxx is your java code file name.
 */