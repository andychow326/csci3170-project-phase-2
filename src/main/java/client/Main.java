package client;

import config.Config;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        try {
            config.loadConfig();
            DatabaseClient dbClient = new DatabaseClient(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SalesSystem ss = new SalesSystem(dbClient);
        ss.execute();
        try {
            if (dbClient.connection != null){
                dbClient.connection.close();
            }
        } catch (SQLException e){
            System.out.println("ERROR! Cannot close the connection");
        }
        System.out.println("Sales System Terminated.");
        System.exit(0);
    }
}
