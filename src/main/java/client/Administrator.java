package client;

import java.sql.SQLException;
import java.io.IOException;
import java.util.Scanner;

public class Administrator {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;

    //constructor
    public Administrator(DatabaseClient dbClient){
        this.db = dbClient;
        //start of the Administrator Operation
        System.out.println("Administrator Operation!!");
    }


}