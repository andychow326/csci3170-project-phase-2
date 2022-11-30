package salessystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
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

    public BaseOperation(DatabaseClient dbClient) {
        this.db = dbClient;
        this.conn = dbClient.getConnection();
    }

    public static final String[] TABLES = {
            Category.TABLE_NAME,
            Manufacturer.TABLE_NAME,
            Part.TABLE_NAME,
            Salesperson.TABLE_NAME,
            Transaction.TABLE_NAME
    };

    public static boolean isValidTableName(String tableName) {
        return Arrays.stream(TABLES).anyMatch(x -> x.equals(tableName));
    }

    public static boolean isValidOption(String s) {
        return s.matches("^[0-9]*$");
    }

    public static int getInputOption() throws IOException {
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

    public static String getInputString() throws IOException {
        String input;
        input = inputReader.readLine();
        if (input.isEmpty()) {
            System.out.println("Please enter a non empty string");
            return null;
        }
        return input;
    }
}
