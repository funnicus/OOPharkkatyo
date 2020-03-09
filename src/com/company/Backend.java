package com.company;
import java.math.BigInteger;
import java.sql.*;

public class Backend {
    private final String url = "jdbc:sqlite:C:/Users/juhku/Programs/OOP/OOPharkkatyo/database/harkkatyodb.db";

    public Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the SQLite server succesfully!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    public void createReservation(){

    }
    /**
     * Luodaan uusi taulukko
     */

    public void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:C:/Users/juhku/Programs/OOP/OOPharkkatyo/database/harkkatyodb.db";

        // SQL statement for creating a new table
        /*
        String sql = "CREATE TABLE IF NOT EXISTS customer (\n"
                + "    id TEXT PRIMARY KEY,\n"
                + "    name TEXT NOT NULL,\n"
                + "    date_of_birth TEXT NOT NULL\n"
                + ");";

         */
        String sql = "CREATE TABLE IF NOT EXISTS reservations (\n"
                + "    id INTEGER PRIMARY KEY NOT NULL,\n"
                + "    customer_id TEXT NOT NULL,\n"
                + "    place TEXT NOT NULL,\n"
                + "    address TEXT NOT NULL,\n"
                + "    start_date TEXT NOT NULL,\n"
                + "    end_date TEXT NOT NULL,\n"
                + "    FOREIGN KEY(customer_id) REFERENCES customer(id)\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
