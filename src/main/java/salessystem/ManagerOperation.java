package salessystem;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import client.DatabaseClient;
import dao.SalespersonDaoImpl;
import dao.Dao.OrderDirection;
import model.Salesperson;
import model.SalespersonColumnKey;

public class ManagerOperation extends BaseOperation {
    // constructor
    public ManagerOperation(DatabaseClient dbClient) {
        super(dbClient);
        // start of the Manager Operation
        System.out.println("Manager Operation!!");
    }

    public void start() {
        displayManagerMenu();
    }

    private void displayManagerMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Operations for manager menu----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println(
                    "2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular parts");
            System.out.println("5. Return to the main menu");

            try {
                isExit = selectOperation();
            } catch (SQLException e) {
                System.out.println("Error excecuting SQL query");
                System.out.println("Error code: " + e.getErrorCode());
                System.out.println("SQL state: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("I/O Error");
                e.printStackTrace();
            }
        }
    }

    private boolean selectOperation() throws SQLException, IOException {
        System.out.print("Enter Your Choice: ");
        boolean isExit = false;

        int choice = getInputInteger();
        if (choice < 0) {
            return isExit;
        }

        switch (choice) {
            case 1:
                listAllSalespersonsByExperience();
                break;
            case 2:
                countTransactionRecordsByExperience();
                break;
            case 3:
                showTotalSalesValueOfEachManufacturer();
                break;
            case 4:
                showNMostPopularParts();
                break;
            case 5:
                isExit = true;
                break;

            default:
                System.out.println("ERROR!! Input must be within 1 to 5!");
        }

        return isExit;
    }

    private void listAllSalespersonsByExperience() throws SQLException, IOException {
        OrderDirection order = getListingOrder();
        SalespersonDaoImpl salespersonDao = new SalespersonDaoImpl(this.conn);
        List<Salesperson> salespersons = salespersonDao
                .orderBy(SalespersonColumnKey.EXPERIENCE, order)
                .getAllSalespersons();

        System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
        salespersons.forEach(
                salesperson -> System.out.printf(
                        "| %d | %s | %d | %d |\n",
                        salesperson.getID(),
                        salesperson.getName(),
                        salesperson.getPhoneNumber(),
                        salesperson.getExperience()));
        System.out.println("End of Query");
    }

    private OrderDirection getListingOrder() throws IOException {
        while (true) {
            System.out.println("Choosing ordering:");
            System.out.println("1. By ascending order");
            System.out.println("2. By descending order");

            System.out.print("Choose the list order: ");
            int choice = getInputInteger();
            switch (choice) {
                case 1:
                    return OrderDirection.ASC;
                case 2:
                    return OrderDirection.DESC;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private void countTransactionRecordsByExperience() throws SQLException, IOException {
    }

    private void showTotalSalesValueOfEachManufacturer() throws SQLException, IOException {
    }

    private void showNMostPopularParts() throws SQLException, IOException {
    }
}