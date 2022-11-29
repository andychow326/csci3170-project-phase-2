package client;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

public class SalesPersonOperation {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    // constructor
    public SalesPersonOperation(DatabaseClient dbClient) {
        this.db = dbClient;
        // start of the SalesPerson Operation
        System.out.println("SalesPerson Operation!!");
    }

}