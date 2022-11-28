package migrator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;

import client.DatabaseClient;

public class Migrator {
    public final static String SCHEMA_FILENAME = "schema.sql";
    public final static char QUERY_SEPARATOR = ';';
    private DatabaseClient dbClient;

    public Migrator(DatabaseClient dbClient) {
        this.dbClient = dbClient;
    }

    public static enum MigrationType {
        UP {
            public String getKeyString() {
                return "-- +migrate Up";
            }
        },
        DOWN {
            public String getKeyString() {
                return "-- +migrate Down";
            }
        };

        public abstract String getKeyString();

        public boolean isKeyString(String s) {
            for (MigrationType type : MigrationType.values()) {
                if (type.getKeyString().equals(s)) {
                    return true;
                }
            }
            return false;
        }

        public boolean isLineValid(String s) {
            if (isKeyString(s)) {
                return getKeyString().equals(s);
            }
            return true;
        }
    }

    public void up() throws SQLException, IOException {
        this.migrate(MigrationType.UP);
    }

    public void down() throws SQLException, IOException {
        this.migrate(MigrationType.DOWN);
    }

    private Statement loadSchema(MigrationType type) throws SQLException, IOException {
        Statement statement;
        String line;
        StringBuffer query = new StringBuffer();
        boolean isHandlingInvalidLine = false;

        InputStream input = getClass().getClassLoader().getResourceAsStream(SCHEMA_FILENAME);
        if (input == null) {
            throw new FileNotFoundException("Error loading schema file");
        }
        BufferedReader schemaReader = new BufferedReader(new InputStreamReader(input));

        statement = this.dbClient.getConnection().createStatement();
        while ((line = schemaReader.readLine()) != null) {
            if (isHandlingInvalidLine) {
                if (type.isKeyString(line) && type.getKeyString().equals(line)) {
                    isHandlingInvalidLine = false;
                }
                continue;
            }
            if (!type.isLineValid(line)) {
                isHandlingInvalidLine = true;
                continue;
            }
            if (this.isCommentLine(line)) {
                continue;
            }

            query.append(line);
            if (this.isQueryEnd(line)) {
                statement.addBatch(query.toString());
                query.setLength(0);
            }
        }

        return statement;
    }

    private int[] migrate(MigrationType type) throws SQLException, IOException {
        Statement statement = this.loadSchema(type);
        int[] updateCounts = statement.executeBatch();
        return updateCounts;
    }

    // Currently only supports two kinds of comment patterns
    private boolean isCommentLine(String line) {
        return line.startsWith("--") || line.startsWith("#");
    }

    // The query should be ended if the string contains the query separator
    private boolean isQueryEnd(String line) {
        return line.indexOf(QUERY_SEPARATOR) != -1;
    }
}
