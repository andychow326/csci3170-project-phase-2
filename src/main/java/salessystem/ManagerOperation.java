package salessystem;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

import client.DatabaseClient;

public class ManagerOperation extends BaseOperation {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    // constructor
    public ManagerOperation(DatabaseClient dbClient) {
        this.db = dbClient;
        // start of the Manager Operation
        System.out.println("Manager Operation!!");
    }

}