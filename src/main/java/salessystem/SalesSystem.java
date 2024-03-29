package salessystem;

import java.io.IOException;

import client.DatabaseClient;

public class SalesSystem extends BaseOperation {
    // Constructs a new SalesSystem
    public SalesSystem(DatabaseClient dbClient) {
        super(dbClient);
    }

    // The starting point of the SalesSystem
    public void execute() {
        System.out.println("Welcome to sales system!");
        displayMainMenu();
    }

    // Display the main menu for chooing operations
    private void displayMainMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Main Menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Operations for administrator");
            System.out.println("2. Operations for salesperson");
            System.out.println("3. Operations for manager");
            System.out.println("4. Exit this program");

            try {
                isExit = selectRole();
            } catch (IOException e) {
                handleIOException(e);
            }
        }
    }

    // Logic for selecting each operation
    private boolean selectRole() throws IOException {
        System.out.print("Enter Your Choice: ");
        boolean isExit = false;

        int choice = getInputInteger();
        if (choice < 0) {
            return isExit;
        }

        switch (choice) {
            case 1:
                AdministratorOperation admin = new AdministratorOperation(db);
                admin.start();
                break;
            case 2:
                SalespersonOperation sales = new SalespersonOperation(db);
                sales.start();
                break;
            case 3:
                ManagerOperation head = new ManagerOperation(db);
                head.start();
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
