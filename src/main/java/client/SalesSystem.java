package client;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

public class SalesSystem {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    // constructor
    public SalesSystem(DatabaseClient dbClient) {
        this.db = dbClient;
    }

    public void execute() throws IOException {
        System.out.println("Welcome to sales system!");
        displayMainMenu();
    }

    private void displayMainMenu() throws IOException {
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

    private Boolean selectRole() throws IOException {
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
                AdministratorOperation admin = new AdministratorOperation(db);
                break;
            case 2:
                SalesPersonOperation sales = new SalesPersonOperation(db);
                break;
            case 3:
                ManagerOperation head = new ManagerOperation(db);
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
