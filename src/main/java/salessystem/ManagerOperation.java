package salessystem;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import client.DatabaseClient;
import dao.ManufacturerRelationalDaoImpl;
import dao.SalespersonDaoImpl;
import dao.SalespersonRelationalDaoImpl;
import dao.Dao.OrderDirection;
import model.ManufacturerRelational;
import model.Salesperson;
import model.SalespersonRelational;

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
                .orderBy(Salesperson.ColumnKey.EXPERIENCE, order)
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
        System.out.print("Type in the lower bound for years of experience: ");
        int lowerBound = getInputInteger();
        System.out.print("Type in the upper bound for years of experience: ");
        int upperBound = getInputInteger();

        SalespersonRelationalDaoImpl salespersonRelationalDao = new SalespersonRelationalDaoImpl(this.conn);
        List<SalespersonRelational> salespersons = salespersonRelationalDao
                .orderBy(SalespersonRelational.ColumnKey.ID, OrderDirection.DESC)
                .getAllSalespersonsByExperienceRangeWithTransactionCount(
                        lowerBound, upperBound);

        System.out.println("Transaction Record:");
        System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
        salespersons.forEach(
                saleperson -> System.out.printf(
                        "| %d | %s | %d | %d |\n",
                        saleperson.getID(),
                        saleperson.getName(),
                        saleperson.getExperience(),
                        saleperson.getTransactionCount()));
        System.out.println("End of Query");
    }

    private void showTotalSalesValueOfEachManufacturer() throws SQLException, IOException {
        ManufacturerRelationalDaoImpl manufacturerDao = new ManufacturerRelationalDaoImpl(this.conn);
        List<ManufacturerRelational> manufacturers = manufacturerDao.getAllManufacturersWithTotalSalesValue();

        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");
        manufacturers.forEach(
                manufacturer -> System.out.printf(
                        "| %d | %s | %d |\n",
                        manufacturer.getID(),
                        manufacturer.getName(),
                        manufacturer.getTotalSalesValue()));
        System.out.println("End of Query");
    }

    private void showNMostPopularParts() throws SQLException, IOException {
    }
}