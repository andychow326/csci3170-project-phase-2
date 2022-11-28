package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Administrator {
    private Scanner input = new Scanner(System.in);
    private DatabaseClient db;
    public Connection conn;
    String[] tables = { "category", "manufacturer", "part", "salesperson", "transaction" };

    // constructor
    public Administrator(DatabaseClient dbClient) {
        this.db = dbClient;
        this.conn = dbClient.connection;
        // start of the Administrator Operation
        // System.out.println("Administrator Operation!!");
        displayAdminMenu();
    }

    private void displayAdminMenu() {
        Boolean isExit = false;
        while (!isExit) {
            System.out.println("\n-----Operations for administrator menu-----");
            System.out.println("\nWhat kinds of operation would you like to perform?");
            System.out.println("1. Create all tables");
            System.out.println("2. Delete all tables");
            System.out.println("3. Load from datafile");
            System.out.println("4. Show content of a table");
            System.out.println("5. Return to the main menu");
            isExit = selectOp();
        }
    }

    private Boolean selectOp() {
        System.out.print("Enter Your Choice: ");
        int choice = 0;
        Boolean isExit = false;
        try {
            choice = input.nextInt();
        } catch (Exception e) {
            System.out.println("ERROR!! You did not input a integer!");
            return isExit;
        }
        try {
            switch (choice) {
                case 1:
                    createTables();
                    break;
                case 2:
                    deleteTables();
                    break;
                case 3:
                    loadData();
                    break;
                case 4:
                    showContent();
                    break;
                case 5:
                    isExit = true;
                    break;
                default:
                    System.out.println("ERROR!! Input must be within 1 to 4!");
                    break;
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
                System.out.println("ERROR!! " + e1.getMessage());
            }
            System.out.println("ERROR!! " + e.getMessage());
        }
        return isExit;
    }

    private void createTables() throws SQLException {
        System.out.print("Processing...");

        // for rollback
        conn.setAutoCommit(false);

        // create sql statement
        Statement stmt = conn.createStatement();

        // create schema string
        String category = "CREATE TABLE IF NOT EXISTS category (cID INTEGER PRIMARY KEY, cNAME VARCHAR(20) UNIQUE NOT NULL)";
        String manufacturer = "CREATE TABLE IF NOT EXISTS manufacturer (mID INTEGER PRIMARY KEY, mNAME VARCHAR(20) NOT NULL, mAddress VARCHAR(50) NOT NULL, mPhoneNumber INTEGER NOT NULL)";
        String part = "CREATE TABLE IF NOT EXISTS part (pID INTEGER PRIMARY KEY, pName VARCHAR(20) NOT NULL, pPrice INTEGER NOT NULL, mID INTEGER NOT NULL, cID INTEGER NOT NULL, pWarrantyPeriod INTEGER NOT NULL, pAvailableQuantity INTEGER NOT NULL, FOREIGN KEY (mID) REFERENCES manufacturer (mID) ON DELETE CASCADE, FOREIGN KEY (cID) REFERENCES category (cID) ON DELETE CASCADE)";
        String salesperson = "CREATE TABLE IF NOT EXISTS salesperson (sID INTEGER PRIMARY KEY, sName VARCHAR(20) NOT NULL, sAddress VARCHAR(50) NOT NULL, sPhoneNumber INTEGER NOT NULL, sExperience INTEGER NOT NULL)";
        String transaction = "CREATE TABLE IF NOT EXISTS transaction (tID INTEGER PRIMARY KEY, pID INTEGER NOT NULL, sID INTEGER NOT NULL, tDate DATE NOT NULL, FOREIGN KEY(pID) REFERENCES part(pID) ON DELETE CASCADE, FOREIGN KEY(sID) REFERENCES salesperson(sID) ON DELETE CASCADE)";

        try {
            // create the tables in db
            stmt.executeUpdate(category);
            stmt.executeUpdate(manufacturer);
            stmt.executeUpdate(part);
            stmt.executeUpdate(salesperson);
            stmt.executeUpdate(transaction);

            // commit the update done
            conn.commit();

            System.out.print("Done! ");
            System.out.print("DataBase is initialized!");
        } catch (Exception e) {
            System.out.println("\nERROR" + e);
            throw e;
        }
    }

    private void deleteTables() throws SQLException {
        System.out.print("Processing...");

        // for rollback
        conn.setAutoCommit(false);

        // create sql statement
        Statement stmt = conn.createStatement();

        // create schema string
        String delete = "DROP TABLES IF EXISTS ";

        try {
            foreignKCheck(false);
            // create the tables in db
            for (String table : tables) {
                stmt.executeUpdate(delete + table);
            }
            foreignKCheck(true);
            // commit the update done
            conn.commit();

            System.out.print("Done! ");
            System.out.print("DataBase is removed!");
        } catch (Exception e) {
            System.out.println("\nERROR" + e);
            throw e;
        }
    }

    private void loadData() throws SQLException {
        // Ask for source data folder path
        System.out.print("Type in the Source Data Folder Path: ");
        String path;
        Boolean isExit = false;
        File folder;
        try {
            String rm = input.nextLine();
            String in = input.nextLine();
            File file = new File("");
            String curdir = file.getAbsolutePath();
            folder = new File(curdir + "/" + in);
            path = curdir + "/" + in;
        } catch (Exception e) {
            System.out.println("ERROR!! You should input a path!");
            return;
        }

        System.out.print("Processing...");

        // for rollback
        conn.setAutoCommit(false);

        try {
            foreignKCheck(false);
            for (File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    System.out.println(fileEntry.getName() + "is a directory");
                } else {
                    switch (fileEntry.getName()) {
                        case "category.txt":
                            insertCat(path + "/category.txt");
                            break;
                        case "manufacturer.txt":
                            insertManu(path + "/manufacturer.txt");
                            break;
                        case "part.txt":
                            insertPart(path + "/part.txt");
                            break;
                        case "salesperson.txt":
                            insertSalesP(path + "/salesperson.txt");
                            break;
                        case "transaction.txt":
                            insertTrans(path + "/transaction.txt");
                            break;
                        default:
                            System.out.println("\nERROR " + fileEntry.getName() + " is not a table.");
                            break;
                    }
                }
            }
            foreignKCheck(true);
            // commit the update done
            conn.commit();

            System.out.print("Done! ");
            System.out.print("Data is inputted to the database!");
        } catch (Exception e) {
            System.out.println("\nERROR" + e);
            throw e;
        }
    }

    private void insertCat(String filepath) throws SQLException {
        // create sql prepared statement
        PreparedStatement catStmt = conn.prepareStatement("insert into category values (?,?)");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] record = line.split("\t");
                catStmt.setInt(1, Integer.parseInt(record[0]));
                catStmt.setString(2, record[1]);
                try {
                    catStmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("\nERROR" + e);
        }
    }

    private void insertManu(String filepath) throws SQLException {
        // create sql prepared statement
        PreparedStatement manuStmt = conn.prepareStatement("insert into manufacturer values (?,?,?,?)");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] record = line.split("\t");
                manuStmt.setInt(1, Integer.parseInt(record[0]));
                manuStmt.setString(2, record[1]);
                manuStmt.setString(3, record[2]);
                manuStmt.setInt(4, Integer.parseInt(record[3]));
                try {
                    manuStmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("\nERROR" + e);
        }
    }

    private void insertPart(String filepath) throws SQLException {
        // create sql prepared statement
        PreparedStatement partStmt = conn.prepareStatement("insert into part values (?,?,?,?,?,?,?)");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] record = line.split("\t");
                partStmt.setInt(1, Integer.parseInt(record[0]));
                partStmt.setString(2, record[1]);
                partStmt.setInt(3, Integer.parseInt(record[2]));
                partStmt.setInt(4, Integer.parseInt(record[3]));
                partStmt.setInt(5, Integer.parseInt(record[4]));
                partStmt.setInt(6, Integer.parseInt(record[5]));
                partStmt.setInt(7, Integer.parseInt(record[6]));
                try {
                    partStmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("\nERROR" + e);
        }
    }

    private void insertSalesP(String filepath) throws SQLException {
        // create sql prepared statement
        PreparedStatement salesPStmt = conn.prepareStatement("insert into salesperson values (?,?,?,?,?)");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] record = line.split("\t");
                salesPStmt.setInt(1, Integer.parseInt(record[0]));
                salesPStmt.setString(2, record[1]);
                salesPStmt.setString(3, record[2]);
                salesPStmt.setInt(4, Integer.parseInt(record[3]));
                salesPStmt.setInt(5, Integer.parseInt(record[4]));
                try {
                    salesPStmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("\nERROR" + e);
        }
    }

    private void insertTrans(String filepath) throws SQLException {
        // create sql prepared statement
        PreparedStatement transStmt = conn.prepareStatement("insert into transaction values (?,?,?,?)");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                String[] record = line.split("\t");
                transStmt.setInt(1, Integer.parseInt(record[0]));
                transStmt.setInt(2, Integer.parseInt(record[1]));
                transStmt.setInt(3, Integer.parseInt(record[2]));

                final String oldformat = "dd/MM/yyyy";
                // final String newformat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(oldformat);
                Date d = null;
                try {
                    d = new java.sql.Date(sdf.parse(record[3]).getTime()); // potential problem
                } catch (Exception e) {
                    System.out.println("\nERROR! the format is not in dd/MM/yyyy !");
                }
                transStmt.setDate(4, d);

                try {
                    transStmt.execute();
                } catch (SQLException e) {
                    throw e;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.print("\nERROR" + e);
        }
    }

    private void showContent() throws SQLException {
        System.out.print("Which table would you like to show: ");
        String rm = input.nextLine();
        String choice = input.nextLine();
        Boolean inTables = false;
        for (String table : tables) {
            if (choice.equals(table)) {
                inTables = true;
            }
        }
        if (!inTables) {
            System.out.println(choice + " is not a table in database.");
            return;
        }

        // for rollback
        conn.setAutoCommit(false);

        // create sql statement
        Statement stmt = conn.createStatement();

        try {
            // create the tables in db
            System.out.println("Content of table category:");
            ResultSet rs;
            String show;
            switch (choice) {
                case "category":
                    // create schema string
                    show = "SELECT * FROM " + choice + " ORDER BY cID";
                    rs = stmt.executeQuery(show);
                    System.out.println("|cID|cName|");
                    while (rs.next()) {
                        int id = rs.getInt("cID");
                        String name = rs.getString("cName");
                        System.out.println("|" + id + "|" + name + "|");
                    }
                    break;
                case "manufacturer":
                    // create schema string
                    show = "SELECT * FROM " + choice + " ORDER BY mID";
                    rs = stmt.executeQuery(show);
                    System.out.println("|mID|mName|mAddress|mPhoneNumber|");
                    while (rs.next()) {
                        int id = rs.getInt("mID");
                        String name = rs.getString("mName");
                        String addr = rs.getString("mAddress");
                        int pno = rs.getInt("mPhoneNumber");
                        System.out.println("|" + id + "|" + name + "|" + addr + "|" + pno + "|");
                    }
                    break;
                case "part":
                    // create schema string
                    show = "SELECT * FROM " + choice + " ORDER BY pID";
                    rs = stmt.executeQuery(show);
                    System.out.println("|pID|pName|pPrice|mID|cID|pWarrantyPeriod|pAvailableQuantity|");
                    while (rs.next()) {
                        int id = rs.getInt("pID");
                        String name = rs.getString("pName");
                        int price = rs.getInt("pPrice");
                        int mid = rs.getInt("mID");
                        int cid = rs.getInt("cID");
                        int wp = rs.getInt("pWarrantyPeriod");
                        int aq = rs.getInt("pAvailableQuantity");
                        System.out.println("|" + id + "|" + name + "|" + price + "|" + mid + "|" + cid + "|" + wp + "|"
                                + aq + "|");
                    }
                    break;
                case "salesperson":
                    // create schema string
                    show = "SELECT * FROM " + choice + " ORDER BY sID";
                    rs = stmt.executeQuery(show);
                    System.out.println("|sID|sName|sAddress|sPhoneNumber|sExperience|");
                    while (rs.next()) {
                        int id = rs.getInt("sID");
                        String name = rs.getString("sName");
                        String addr = rs.getString("sAddress");
                        int pno = rs.getInt("sPhoneNumber");
                        int exp = rs.getInt("sExperience");
                        System.out.println("|" + id + "|" + name + "|" + addr + "|" + pno + "|" + exp + "|");
                    }
                    break;
                case "transaction":
                    // create schema string
                    show = "SELECT * FROM " + choice + " ORDER BY tID";
                    rs = stmt.executeQuery(show);
                    System.out.println("|tID|pID|sID|tDate");
                    while (rs.next()) {
                        int id = rs.getInt("tID");
                        int pid = rs.getInt("pID");
                        int sid = rs.getInt("sID");
                        String date = rs.getString("tDate");
                        System.out.println("|" + id + "|" + pid + "|" + sid + "|" + date + "|");
                    }
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            System.out.println("\nERROR" + e);
            throw e;
        }
    }

    private void foreignKCheck(Boolean flag) throws SQLException {
        Statement stmt = conn.createStatement();
        if (flag) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=1");
        } else {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        }
        // stmt.close();
    }
}
