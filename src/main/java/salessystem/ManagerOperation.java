package salessystem;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

import client.DatabaseClient;

public class ManagerOperation extends BaseOperation {
    // constructor
    public ManagerOperation(DatabaseClient dbClient) {
        super(dbClient);
        // start of the Manager Operation
        System.out.println("Manager Operation!!");
    }

    public void start() {

    }
}