package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public String DatabaseURL = "";
    public String DatabaseUser = "";
    public String DatabasePassword = "";

    public void loadConfig() throws IOException {
        final String propFilename = "config.properties";
        InputStream input;

        input = getClass().getClassLoader().getResourceAsStream(propFilename);
        Properties prop = new Properties();

        // Load the config file
        prop.load(input);

        // Save the config value in to the Config object
        this.setDatabaseURL(prop.getProperty("db.url", ""));
        this.setDatabaseUser(prop.getProperty("db.user", ""));
        this.setDatabasePassword(prop.getProperty("db.password", ""));

        input.close();
    }

    public void setDatabaseURL(String databaseURL) {
        this.DatabaseURL = databaseURL;
    }

    public void setDatabaseUser(String databaseUser) {
        this.DatabaseUser = databaseUser;
    }

    public void setDatabasePassword(String databasePassword) {
        this.DatabasePassword = databasePassword;
    }

    public String getDatabaseURL() {
        return this.DatabaseURL;
    }

    public String getDatabaseUser() {
        return this.DatabaseUser;
    }

    public String getDatabasePassword() {
        return this.DatabasePassword;
    }
}
