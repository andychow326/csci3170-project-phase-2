package client;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import config.Config;
import migrator.Migrator;

public class DatabaseClient {
    public Connection connection;
    public Migrator migrator;

    public DatabaseClient(Config config) throws SQLException, FileNotFoundException {
        this.connection = DriverManager.getConnection(
                "jdbc:" + config.DatabaseURL,
                config.DatabaseUser,
                config.DatabasePassword);
        this.migrator = new Migrator(this);
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }
}
