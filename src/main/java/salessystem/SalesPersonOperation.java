package salessystem;

import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import client.DatabaseClient;
import dao.PartRelationalDaoImpl;
import dao.Dao.OrderDirection;
import model.ColumnKey;
import model.ManufacturerColumnKey;
import model.PartColumnKey;
import model.PartRelational;

public class SalesPersonOperation extends BaseOperation {
    // constructor
    public SalesPersonOperation(DatabaseClient dbClient) {
        super(dbClient);
    }

    public void start() {
        displaySalesMenu();
    }

    private void displaySalesMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Operations for salesperson menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Search for parts");
            System.out.println("2. Sell a part");
            System.out.println("3. Return to the main menu");

            try {
                isExit = selectOperation();
            } catch (SQLException e) {

            } catch (IOException e) {

            }
        }
    }

    private boolean selectOperation() throws SQLException, IOException {
        System.out.print("Enter Your Choice: ");
        boolean isExit = false;

        int choice = getInputOption();
        if (choice < 0) {
            return isExit;
        }

        switch (choice) {
            case 1:
                searchPartsOption();
                break;
            case 2:
                sellPartsOption();
                break;
            case 3:
                isExit = true;
                break;
            default:
                System.out.println("ERROR!! Input must be within 1 to 5!");
        }

        return isExit;
    }

    private void searchPartsOption() {
        ColumnKey criterion;
        String criterionValue;
        OrderDirection order;
        System.out.println("Choose the Search Criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");

        try {
            criterion = selectSearchPartsCriterion();
            criterionValue = enterSearchPartsKeyword();
            order = getSearchPartsOrder();
            showSearchPartsContent(criterion, criterionValue, order);
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

    private ColumnKey selectSearchPartsCriterion() throws IOException {
        while (true) {
            System.out.print("Choose the search criterion: ");
            int choice = getInputOption();
            switch (choice) {
                case 1:
                    return PartColumnKey.NAME;
                case 2:
                    return ManufacturerColumnKey.NAME;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    private String enterSearchPartsKeyword() throws IOException {
        System.out.print("Type in the Search Keyword: ");
        String input = getInputString();
        return input;
    }

    private OrderDirection getSearchPartsOrder() {
        while (true) {
            System.out.println("Choosing ordering:");
            System.out.println("1. By price, ascending order");
            System.out.println("2. By price, descending order");

            try {
                System.out.print("Choose the search criterion: ");
                int choice = getInputOption();
                switch (choice) {
                    case 1:
                        return OrderDirection.ASC;
                    case 2:
                        return OrderDirection.DESC;
                    default:
                        System.out.println("Invalid Choice");
                }

            } catch (IOException e) {

            }
        }
    }

    private void showSearchPartsContent(ColumnKey searchKey, String searchValue, OrderDirection order)
            throws SQLException {
        PartRelationalDaoImpl partRelationalDao = new PartRelationalDaoImpl(this.conn);
        List<PartRelational> parts = partRelationalDao
                .where(searchKey)
                .like(searchValue)
                .orderBy(PartColumnKey.PRICE, order)
                .getAllParts();

        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
        parts.forEach(
                part -> System.out.printf(
                        "| %d | %s | %s | %s | %d | %d | %d |\n",
                        part.getID(),
                        part.getName(),
                        part.getManufacturer().getName(),
                        part.getCategory().getName(),
                        part.getAvailableQuantity(),
                        part.getWarrantyPeriod(),
                        part.getPrice()));
        System.out.println("End of Query");
    }

    private void sellPartsOption() {
    }
}