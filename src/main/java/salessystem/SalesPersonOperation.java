package salessystem;

import java.sql.SQLException;
import java.io.IOException;

import client.DatabaseClient;
import dao.Dao.OrderDirection;
import model.Manufacturer;
import model.ManufacturerColumnKey;
import model.Part;
import model.PartColumnKey;

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
        String criterion;
        String criterionValue;
        OrderDirection order;
        while (true) {
            System.out.println("Choose the Search Criterion:");
            System.out.println("1. Part Name");
            System.out.println("2. Manufacturer Name");

            try {
                criterion = selectSearchPartsCriterion();
                if (criterion.isEmpty()) {
                    continue;
                }
                criterionValue = enterSearchPartsKeyword();
                if (criterionValue == null) {
                    continue;
                }
                order = getSearchPartsOrder();

            } catch (IOException e) {

            }
        }
    }

    private String selectSearchPartsCriterion() throws IOException {
        System.out.print("Choose the search criterion: ");
        int choice = getInputOption();
        switch (choice) {
            case 1:
                return String.join(".", Part.TABLE_NAME, PartColumnKey.NAME.toString());
            case 2:
                return String.join(".", Manufacturer.TABLE_NAME, ManufacturerColumnKey.NAME.toString());
            default:
                return "";
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
                        return null;
                }

            } catch (IOException e) {

            }
        }
    }

    private void sellPartsOption() {

    }
}