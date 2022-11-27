package client;

import java.sql.SQLException;
import java.io.IOExeception;
import java.util.Scanner;

public class SalesPerson {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    //constructor
    public SalesPerson(DatabaseClient dbClient){
        this.db = dbClient;
        //start of the SalesPerson Operation
        System.out.println("SalesPerson Operation!!");
    }


}