package client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import config.Config;

public class DatabaseClient {
    public Connection connection;

    public DatabaseClient(Config config) throws SQLException {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:" + config.DatabaseURL,
                    config.DatabaseUser,
                    config.DatabasePassword);
        } catch (Exception e) {
            throw e;
        }
    }
}
