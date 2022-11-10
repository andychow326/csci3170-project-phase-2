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
    }
}
