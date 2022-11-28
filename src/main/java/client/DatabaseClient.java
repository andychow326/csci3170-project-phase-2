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
        } catch (SQLException e) {
            throw e;
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("ERROR! Cannot close the connection");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
