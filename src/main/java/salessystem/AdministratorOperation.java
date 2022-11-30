package salessystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.DatabaseClient;
import dao.CategoryDaoImpl;
import dao.ManufacturerDaoImpl;
import dao.PartDaoImpl;
import dao.SalespersonDaoImpl;
import dao.TransactionDaoImpl;
import dao.Dao.OrderDirection;
import model.Category;
import model.Manufacturer;
import model.Part;
import model.Salesperson;
import model.Transaction;

import java.text.ParseException;

public class AdministratorOperation extends BaseOperation {
    // constructor
    public AdministratorOperation(DatabaseClient dbClient) throws IOException {
        super(dbClient);
    }

    public void start() {
        displayAdminMenu();
    }

    private void displayAdminMenu() {
        boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");

            try {
                isExit = selectOp();
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

    private boolean selectOp() throws SQLException, IOException {
        System.out.print("Enter Your Choice: ");
        boolean isExit = false;

        int choice = getInputInteger();
        if (choice < 0) {
            return isExit;
        }

        switch (choice) {
            case 1:
                createTables();
                break;
            case 2:
                deleteTables();
                break;
            case 3:
                loadData();
                break;
            case 4:
                showContent();
                break;
            case 5:
                isExit = true;
                break;
            default:
                System.out.println("ERROR!! Input must be within 1 to 5!");
        }

        return isExit;
    }

    private void createTables() throws SQLException, IOException {
        System.out.print("Processing...");
        this.db.migrator.up();
        System.out.println("Done! Database is initialized!");
    }

    private void deleteTables() throws SQLException, IOException {
        System.out.print("Processing...");
        this.db.migrator.down();
        System.out.println("Done! Database is removed!");
    }

    private void loadData() throws SQLException, IOException {
        // Ask for source data folder path
        System.out.print("Type in the Source Data Folder Path: ");
        String folderName = inputReader.readLine();
        URL inputFileURL = getClass().getClassLoader().getResource(folderName);

        if (inputFileURL == null) {
            throw new FileNotFoundException("Error file does not exist");
        }

        String folderPath = inputFileURL.getFile();
        File inputFile = new File(folderPath);

        if (inputFile.isFile()) {
            throw new IllegalArgumentException("Error the input path is a file, please input a valid folder path.");
        }

        System.out.print("Processing...");
        boolean isError = false;
        List<String> errorFiles = new ArrayList<String>();
        try (
                InputStream input = getClass().getClassLoader().getResourceAsStream(folderName);
                InputStreamReader inputReader = new InputStreamReader(input);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            String fileName;
            while ((fileName = fileReader.readLine()) != null) {
                String absoluteFilePath = Paths.get(folderPath, fileName).toString();
                try {
                    this.processFileData(absoluteFilePath);
                } catch (SQLException e) {
                    System.out.println("Error excecuting insert query from file: " + absoluteFilePath);
                    System.out.println("Error code: " + e.getErrorCode());
                    System.out.println("SQL state: " + e.getSQLState());
                    System.out.println("Message: " + e.getMessage());
                    e.printStackTrace();
                    isError = true;
                    errorFiles.add(absoluteFilePath);
                } catch (IOException e) {
                    System.out.println("I/O Error");
                    e.printStackTrace();
                    isError = true;
                    errorFiles.add(absoluteFilePath);
                }
            }
        }

        if (isError) {
            System.out.println("Done! But some files cannot be processed!");
            System.out.println("Error files:");
            System.out.println(String.join(",\n", errorFiles));
            return;
        }

        System.out.println("Done! Data is inputted to the database!");
    }

    private void processFileData(String filepath) throws SQLException, IOException {
        File file = new File(filepath);
        if (file.isDirectory()) {
            System.out.printf("Not loaded %s: is a directory", file.getPath());
            return;
        }

        switch (file.getName()) {
            case "category.txt":
                insertCategoryDataFromFile(file.getPath());
                break;
            case "manufacturer.txt":
                insertManufacturerDataFromFile(file.getPath());
                break;
            case "part.txt":
                insertPartDataFromFile(file.getPath());
                break;
            case "salesperson.txt":
                insertSalespersonDataFromFile(file.getPath());
                break;
            case "transaction.txt":
                insertTransactionDataFromFile(file.getPath());
                break;
            default:
                System.out.printf("Invalid file: %s\n", file.getPath());
        }
    }

    private void insertCategoryDataFromFile(String filepath) throws SQLException, IOException {
        CategoryDaoImpl categoryDao = new CategoryDaoImpl(this.conn);
        List<Category> categories = new ArrayList<Category>();

        String line;
        try (
                FileInputStream fs = new FileInputStream(filepath);
                InputStreamReader inputReader = new InputStreamReader(fs);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            while ((line = fileReader.readLine()) != null) {
                Category category = Category.parseString(line);
                categories.add(category);
            }
        }
        categoryDao.addAll(categories);
    }

    private void insertManufacturerDataFromFile(String filepath) throws SQLException, IOException {
        ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl(this.conn);
        List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();

        String line;
        try (
                FileInputStream fs = new FileInputStream(filepath);
                InputStreamReader inputReader = new InputStreamReader(fs);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            while ((line = fileReader.readLine()) != null) {
                Manufacturer manufacturer = Manufacturer.parseString(line);
                manufacturers.add(manufacturer);
            }
        }
        manufacturerDao.addAll(manufacturers);
    }

    private void insertPartDataFromFile(String filepath) throws SQLException, IOException {
        PartDaoImpl partDaoImpl = new PartDaoImpl(this.conn);
        List<Part> parts = new ArrayList<Part>();

        String line;
        try (
                FileInputStream fs = new FileInputStream(filepath);
                InputStreamReader inputReader = new InputStreamReader(fs);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            while ((line = fileReader.readLine()) != null) {
                Part part = Part.parseString(line);
                parts.add(part);
            }
        }
        partDaoImpl.addAll(parts);
    }

    private void insertSalespersonDataFromFile(String filepath) throws SQLException, IOException {
        SalespersonDaoImpl salespersonDaoImpl = new SalespersonDaoImpl(this.conn);
        List<Salesperson> salespersons = new ArrayList<Salesperson>();

        String line;
        try (
                FileInputStream fs = new FileInputStream(filepath);
                InputStreamReader inputReader = new InputStreamReader(fs);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            while ((line = fileReader.readLine()) != null) {
                Salesperson salesperson = Salesperson.parseString(line);
                salespersons.add(salesperson);
            }
        }
        salespersonDaoImpl.addAll(salespersons);
    }

    private void insertTransactionDataFromFile(String filepath) throws SQLException, IOException {
        TransactionDaoImpl transactionDaoImpl = new TransactionDaoImpl(this.conn);
        List<Transaction> transactions = new ArrayList<Transaction>();

        String line;
        try (
                FileInputStream fs = new FileInputStream(filepath);
                InputStreamReader inputReader = new InputStreamReader(fs);
                BufferedReader fileReader = new BufferedReader(inputReader);) {
            while ((line = fileReader.readLine()) != null) {
                try {
                    Transaction transaction = Transaction.parseString(line);
                    transactions.add(transaction);
                } catch (ParseException e) {
                    System.out.println(
                            "Error parsing transaction date, this error should not be happening, assuming all data format is correct.");
                }
            }
        }
        transactionDaoImpl.addAll(transactions);
    }

    private void showContent() throws SQLException, IOException {
        System.out.print("Which table would you like to show: ");
        String choice = inputReader.readLine();

        if (!isValidTableName(choice)) {
            System.out.println(choice + " is not a table in database.");
            return;
        }

        try {
            System.out.println("Content of table category:");
            switch (choice) {
                case "category":
                    CategoryDaoImpl categoryDao = new CategoryDaoImpl(this.conn);
                    List<Category> categories = categoryDao
                            .orderBy(Category.ColumnKey.ID, OrderDirection.ASC)
                            .getAllCategories();

                    System.out.println("| cID | cName |");
                    categories.forEach(
                            category -> System.out.printf(
                                    "| %d | %s |\n",
                                    category.getID(), category.getName()));
                    break;
                case "manufacturer":
                    ManufacturerDaoImpl manufacturerDao = new ManufacturerDaoImpl(this.conn);
                    List<Manufacturer> manufacturers = manufacturerDao
                            .orderBy(Manufacturer.ColumnKey.ID, OrderDirection.ASC)
                            .getAllManufacturers();

                    System.out.println("| mID | mName | mAddress | mPhoneNumber |");
                    manufacturers.forEach(
                            manufacturer -> System.out.printf(
                                    "| %d | %s | %s | %d |\n",
                                    manufacturer.getID(),
                                    manufacturer.getName(),
                                    manufacturer.getAddress(),
                                    manufacturer.getPhoneNumber()));
                    break;
                case "part":
                    PartDaoImpl partDaoImpl = new PartDaoImpl(this.conn);
                    List<Part> parts = partDaoImpl
                            .orderBy(Part.ColumnKey.ID, OrderDirection.ASC)
                            .getAllParts();

                    System.out.println("| pID | pName | pPrice | mID | cID | pWarrantyPeriod | pAvailableQuantity |");
                    parts.forEach(part -> System.out.printf(
                            "| %d | %s | %d | %d | %d | %d | %d |\n",
                            part.getID(),
                            part.getName(),
                            part.getPrice(),
                            part.getManufacturerID(),
                            part.getCategoryID(),
                            part.getWarrantyPeriod(),
                            part.getAvailableQuantity()));
                    break;
                case "salesperson":
                    SalespersonDaoImpl salespersonDaoImpl = new SalespersonDaoImpl(this.conn);
                    List<Salesperson> salespersons = salespersonDaoImpl
                            .orderBy(Salesperson.ColumnKey.ID, OrderDirection.ASC)
                            .getAllSalespersons();

                    System.out.println("| sID | sName | sAddress | sPhoneNumber | sExperience |");
                    salespersons.forEach(
                            salesperson -> System.out.printf(
                                    "| %d | %s | %s | %d | %d\n",
                                    salesperson.getID(),
                                    salesperson.getName(),
                                    salesperson.getAddress(),
                                    salesperson.getPhoneNumber(),
                                    salesperson.getExperience()));
                    break;
                case "transaction":
                    TransactionDaoImpl transactionDao = new TransactionDaoImpl(this.conn);
                    List<Transaction> transactions = transactionDao
                            .orderBy(Transaction.ColumnKey.ID, OrderDirection.ASC)
                            .getAllTransactions();

                    System.out.println("| tID | pID | sID | tDate |");
                    transactions.forEach(
                            transaction -> System.out.printf(
                                    "| %d | %d | %d | %s |\n",
                                    transaction.getID(),
                                    transaction.getPartID(),
                                    transaction.getSalespersonID(),
                                    transaction.getDateString()));
                    break;
            }
        } catch (SQLException e) {
            System.out.println("\nERROR" + e);
            throw e;
        }
    }
}
