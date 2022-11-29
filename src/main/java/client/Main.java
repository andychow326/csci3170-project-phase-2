package client;

import config.Config;
import salessystem.SalesSystem;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        try {
            config.loadConfig();
            DatabaseClient dbClient = new DatabaseClient(config);
            SalesSystem ss = new SalesSystem(dbClient);
            ss.execute();
            dbClient.closeConnection();
            System.out.println("Sales System Terminated.");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
