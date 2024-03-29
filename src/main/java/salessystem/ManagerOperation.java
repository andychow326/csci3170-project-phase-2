package salessystem;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import client.DatabaseClient;
import dao.ManufacturerRelationalDao;
import dao.PartRelationalDao;
import dao.SalespersonDao;
import dao.SalespersonRelationalDao;
import dao.Dao.OrderDirection;
import model.ManufacturerRelational;
import model.PartRelational;
import model.Salesperson;
import model.SalespersonRelational;

public class ManagerOperation extends BaseOperation {
    // Constructs a new ManagerOperation
    public ManagerOperation(DatabaseClient dbClient) {
        super(dbClient);
    }

    // The main function of the ManagerOperation
    public void start() {
        displayManagerMenu();
    }

    // Display the Manager menu
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
                handleSQLException(e);
            } catch (IllegalArgumentException e) {
                handleIllegalArgumentException(e);
            } catch (IOException e) {
                handleIOException(e);
            }
        }
    }

    // Logic for selecting each operation
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
                System.out.println("Error: Input must be within 1 to 5");
        }

        return isExit;
    }

    // List all salespersons by experience range and order by specific order
    private void listAllSalespersonsByExperience() throws SQLException, IOException {
        OrderDirection order = getListingOrder();
        SalespersonDao salespersonDao = new SalespersonDao(this.conn);
        List<Salesperson> salespersons = salespersonDao
                .orderBy(Salesperson.ColumnKey.EXPERIENCE, order)
                .getAll();

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

    // Ask for input for ordering
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
                    System.out.println("Error: Invalid Choice");
            }
        }
    }

    // Count the no. of sales record of each salesperson under a specific range on
    // years of experience
    private void countTransactionRecordsByExperience() throws SQLException, IOException {
        System.out.print("Type in the lower bound for years of experience: ");
        int lowerBound = getInputInteger();
        System.out.print("Type in the upper bound for years of experience: ");
        int upperBound = getInputInteger();

        SalespersonRelationalDao salespersonRelationalDao = new SalespersonRelationalDao(this.conn);
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

    // Show total sales value of each manufacturer
    private void showTotalSalesValueOfEachManufacturer() throws SQLException, IOException {
        ManufacturerRelationalDao manufacturerDao = new ManufacturerRelationalDao(this.conn);
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

    // Show the N most popular parts
    private void showNMostPopularParts() throws SQLException, IOException {
        System.out.print("Type in the number of parts: ");
        int n = getInputInteger();

        PartRelationalDao partRelationalDao = new PartRelationalDao(this.conn);
        List<PartRelational> parts = partRelationalDao
                .limit(n)
                .getAllPartsWithTransactionCount();

        System.out.println("| Part ID | Part Name | NO. of Transaction |");
        parts.forEach(
                part -> System.out.printf(
                        "| %d | %s | %d |\n",
                        part.getID(),
                        part.getName(),
                        part.getTransactionCount()));
        System.out.println("End of Query");
    }
}