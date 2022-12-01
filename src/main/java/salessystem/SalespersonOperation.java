package salessystem;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;

import client.DatabaseClient;
import dao.PartDao;
import dao.PartRelationalDao;
import dao.TransactionDao;
import dao.Dao.OrderDirection;
import model.BaseModel;
import model.Manufacturer;
import model.Part;
import model.PartRelational;
import model.Transaction;

public class SalespersonOperation extends BaseOperation {
    // Constructs a new SalespersonOperation
    public SalespersonOperation(DatabaseClient dbClient) {
        super(dbClient);
    }

    // The main function of the SalespersonOperation
    public void start() {
        displaySalesMenu();
    }

    // Display the Salesperson menu
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
                searchPartsOption();
                break;
            case 2:
                sellPartOption();
                break;
            case 3:
                isExit = true;
                break;
            default:
                System.out.println("Error: Input must be within 1 to 5");
        }

        return isExit;
    }

    // Perform search parts operation
    private void searchPartsOption() throws SQLException, IOException {
        BaseModel.ColumnKey criterion;
        String criterionValue;
        OrderDirection order;

        criterion = selectSearchPartsCriterion();
        criterionValue = enterSearchPartsKeyword();
        order = getSearchPartsOrder();
        showSearchPartsContent(criterion, criterionValue, order);
    }

    // Ask for input search criterion
    private BaseModel.ColumnKey selectSearchPartsCriterion() throws IOException {
        while (true) {
            System.out.println("Choose the Search Criterion:");
            System.out.println("1. Part Name");
            System.out.println("2. Manufacturer Name");

            System.out.print("Choose the search criterion: ");
            int choice = getInputInteger();
            switch (choice) {
                case 1:
                    return Part.ColumnKey.NAME;
                case 2:
                    return Manufacturer.ColumnKey.NAME;
                default:
                    System.out.println("Error: Invalid Choice");
            }
        }
    }

    // Ask for input search keyword
    private String enterSearchPartsKeyword() throws IOException {
        System.out.print("Type in the Search Keyword: ");
        String input = getInputString();
        return input;
    }

    // Ask for input search order
    private OrderDirection getSearchPartsOrder() throws IOException {
        while (true) {
            System.out.println("Choosing ordering:");
            System.out.println("1. By price, ascending order");
            System.out.println("2. By price, descending order");

            System.out.print("Choose the search criterion: ");
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

    // Show the search parts content
    private void showSearchPartsContent(BaseModel.ColumnKey searchKey, String searchValue, OrderDirection order)
            throws SQLException {
        PartRelationalDao partRelationalDao = new PartRelationalDao(this.conn);
        List<PartRelational> parts = partRelationalDao
                .where(searchKey)
                .like(searchValue)
                .orderBy(Part.ColumnKey.PRICE, order)
                .getAll();

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

    // Perform sell part operation
    private void sellPartOption() throws SQLException, IOException {
        int partID = enterSellPartID();
        int salespersonID = enterSellPartSalespersonID();

        PartDao partDao = new PartDao(this.conn);
        Part part = partDao.get(partID);
        if (part == null) {
            System.out.println("Error: Part not found");
            return;
        }
        if (part.getAvailableQuantity() > 0) {
            sellPart(partDao, part, salespersonID);
            return;
        }

        System.out.println("Error: The part is not available");
    }

    // Ask for input sell part id
    private int enterSellPartID() throws IOException {
        int input = -1;
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Enter The Part ID: ");
            input = getInputInteger();
            if (input >= 0) {
                isExit = true;
            }
        }
        return input;
    }

    // Ask for input the salesperson id that sell the part
    private int enterSellPartSalespersonID() throws IOException {
        int input = -1;
        boolean isExit = false;
        while (!isExit) {
            System.out.print("Enter The Salesperson ID: ");
            input = getInputInteger();
            if (input >= 0) {
                isExit = true;
            }
        }
        return input;
    }

    // Sell the part with the corresponding salesperson and display the status of
    // current part
    private void sellPart(PartDao partDao, Part part, int salespersonID) throws SQLException {
        TransactionDao transactionDao = new TransactionDao(this.conn);
        int newPrimaryKey = transactionDao.getNewPrimaryKey(Transaction.ColumnKey.ID, Transaction.TABLE_NAME);
        Date currentDate = transactionDao.getCurrentDate();

        Transaction transaction = new Transaction(
                newPrimaryKey,
                part.getID(),
                salespersonID,
                currentDate);
        transactionDao.add(transaction);
        part.setAvailableQuantity(part.getAvailableQuantity() - 1);
        partDao.update(part);

        System.out.printf("Product: %s(id: %d) Remaining Quantity: %d\n",
                part.getName(), part.getID(), part.getAvailableQuantity());
    }
}