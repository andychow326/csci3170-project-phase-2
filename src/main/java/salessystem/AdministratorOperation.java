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
import dao.CategoryDao;
import dao.ManufacturerDao;
import dao.PartDao;
import dao.SalespersonDao;
import dao.TransactionDao;
import dao.Dao.OrderDirection;
import model.Category;
import model.Manufacturer;
import model.Part;
import model.Salesperson;
import model.Transaction;

import java.text.ParseException;

public class AdministratorOperation extends BaseOperation {
    // Constructs a new AdministratorOperation
    public AdministratorOperation(DatabaseClient dbClient) {
        super(dbClient);
    }

    // The main function of AdministratorOperation
    public void start() {
        displayAdminMenu();
    }

    // Display the Administrator manu
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
                handleSQLException(e);
            } catch (IllegalArgumentException e) {
                handleIllegalArgumentException(e);
            } catch (FileNotFoundException e) {
                handleFileNotFoundException(e);
            } catch (IOException e) {
                handleIOException(e);
            }
        }
    }

    // Logic for selecting each operation
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
                System.out.println("Error: Input must be within 1 to 5");
        }

        return isExit;
    }

    // Create all tables
    private void createTables() throws SQLException, FileNotFoundException, IOException {
        System.out.print("Processing...");
        this.db.migrator.up();
        System.out.println("Done! Database is initialized!");
    }

    // Delete all tables
    private void deleteTables() throws SQLException, FileNotFoundException, IOException {
        System.out.print("Processing...");
        this.db.migrator.down();
        System.out.println("Done! Database is removed!");
    }

    // Load data from source folder
    private void loadData() throws SQLException, FileNotFoundException, IOException {
        // Ask for source data folder path
        System.out.print("Type in the Source Data Folder Path: ");
        String folderName = inputReader.readLine();
        URL inputFileURL = getClass().getClassLoader().getResource(folderName);

        if (inputFileURL == null) {
            throw new FileNotFoundException("Error: File does not exist");
        }

        String folderPath = inputFileURL.getFile();
        File inputFile = new File(folderPath);

        if (inputFile.isFile()) {
            throw new IllegalArgumentException("Error: The input path is a file, please input a valid folder path");
        }

        System.out.print("Processing...");
        boolean isError = false;
        // A collection of error files
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
                    handleSQLException(e);
                    isError = true;
                    errorFiles.add(absoluteFilePath);
                } catch (IOException e) {
                    handleIOException(e);
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

    // Process a file content and perform insertions
    private void processFileData(String filepath) throws SQLException, IOException {
        File file = new File(filepath);
        if (file.isDirectory()) {
            System.out.printf("Error: %s is a directory", file.getPath());
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
                System.out.printf("Error: Invalid file %s\n", file.getPath());
        }
    }

    // Insert the category data to the database
    private void insertCategoryDataFromFile(String filepath) throws SQLException, IOException {
        CategoryDao categoryDao = new CategoryDao(this.conn);
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

    // Insert the manufacturer data to the database
    private void insertManufacturerDataFromFile(String filepath) throws SQLException, IOException {
        ManufacturerDao manufacturerDao = new ManufacturerDao(this.conn);
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

    // Insert the part data to the database
    private void insertPartDataFromFile(String filepath) throws SQLException, IOException {
        PartDao partDaoImpl = new PartDao(this.conn);
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

    // Insert the salesperson data to the database
    private void insertSalespersonDataFromFile(String filepath) throws SQLException, IOException {
        SalespersonDao salespersonDaoImpl = new SalespersonDao(this.conn);
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

    // Insert the transaction data to the database
    private void insertTransactionDataFromFile(String filepath) throws SQLException, IOException {
        TransactionDao transactionDaoImpl = new TransactionDao(this.conn);
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

    // Show content of the corresponding database table
    private void showContent() throws SQLException, IOException {
        System.out.print("Which table would you like to show: ");
        String choice = inputReader.readLine();

        if (!isValidTableName(choice)) {
            System.out.println("Error: " + choice + " is not a table in database.");
            return;
        }

        System.out.println("Content of table category:");
        switch (choice) {
            case "category":
                CategoryDao categoryDao = new CategoryDao(this.conn);
                List<Category> categories = categoryDao
                        .orderBy(Category.ColumnKey.ID, OrderDirection.ASC)
                        .getAll();

                System.out.println("| cID | cName |");
                categories.forEach(
                        category -> System.out.printf(
                                "| %d | %s |\n",
                                category.getID(), category.getName()));
                break;
            case "manufacturer":
                ManufacturerDao manufacturerDao = new ManufacturerDao(this.conn);
                List<Manufacturer> manufacturers = manufacturerDao
                        .orderBy(Manufacturer.ColumnKey.ID, OrderDirection.ASC)
                        .getAll();

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
                PartDao partDaoImpl = new PartDao(this.conn);
                List<Part> parts = partDaoImpl
                        .orderBy(Part.ColumnKey.ID, OrderDirection.ASC)
                        .getAll();

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
                SalespersonDao salespersonDaoImpl = new SalespersonDao(this.conn);
                List<Salesperson> salespersons = salespersonDaoImpl
                        .orderBy(Salesperson.ColumnKey.ID, OrderDirection.ASC)
                        .getAll();

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
                TransactionDao transactionDao = new TransactionDao(this.conn);
                List<Transaction> transactions = transactionDao
                        .orderBy(Transaction.ColumnKey.ID, OrderDirection.ASC)
                        .getAll();

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

    }
}
