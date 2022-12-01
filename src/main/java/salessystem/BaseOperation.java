package salessystem;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import client.DatabaseClient;
import model.Category;
import model.Manufacturer;
import model.Part;
import model.Salesperson;
import model.Transaction;

public class BaseOperation {
    protected static BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
    protected DatabaseClient db;
    protected Connection conn;

    // Constructs a new BaseOperation
    public BaseOperation(DatabaseClient dbClient) {
        this.db = dbClient;
        this.conn = dbClient.getConnection();
    }

    // All tables in the database
    public static final String[] TABLES = {
            Category.TABLE_NAME,
            Manufacturer.TABLE_NAME,
            Part.TABLE_NAME,
            Salesperson.TABLE_NAME,
            Transaction.TABLE_NAME
    };

    // Check if the input is the valid table name which is in the TABLES array
    public static boolean isValidTableName(String tableName) {
        return Arrays.stream(TABLES).anyMatch(x -> x.equals(tableName));
    }

    // Check if the input is the valid integer
    public static boolean isValidOption(String s) {
        return s.matches("^[0-9]*$");
    }

    // Ask for input an integer and perform validation
    public static int getInputInteger() throws IOException {
        int choice;
        try {
            String input = inputReader.readLine();
            if (input.isEmpty() || !isValidOption(input)) {
                System.out.println("Please enter a valid number");
                return -1;
            }

            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return -1;
        }

        return choice;
    }

    // Ask for input a string and perform validation
    public static String getInputString() throws IOException {
        String input;
        input = inputReader.readLine();
        if (input.isEmpty()) {
            System.out.println("Please enter a non empty string");
            return null;
        }
        return input;
    }

    public static void handleSQLException(SQLException e) {
        System.out.println("Error excecuting SQL query: " + e.getMessage());
    }

    public static void handleIOException(IOException e) {
        System.out.println("I/O Error");
        e.printStackTrace();
    }

    public static void handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }

    public static void handleFileNotFoundException(FileNotFoundException e) {
        System.out.println(e.getMessage());
    }
}
