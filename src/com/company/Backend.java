package com.company;
import java.math.BigInteger;
import java.sql.*;

public class Backend {
    private final String url = "jdbc:sqlite:database/harkkatyodb.db";

    /**
     * Muodostetaan yhteys tietokantaan.
     * Palauttaa yhteyden Connection tyyppisenä objektina
     * @return
     */
    public Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to the SQLite server succesfully!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        //System.out.println(conn);
        return conn;
    }

    /**
     * Creating a customer
     * @param id
     * @param name
     * @param date_of_birth
     */
    public void createCustomer(String id,
                                  String name,
                                  String date_of_birth){
        String sql = "INSERT INTO customer(id, name, date_of_birth) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, date_of_birth);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creating a new reservation
     * @param id
     * @param customer_id
     * @param place
     * @param address
     * @param start_date
     * @param end_date
     */
    public void createReservation(int id,
                                  String customer_id,
                                  String place,
                                  String address,
                                  String start_date,
                                  String end_date){
        String sql = "INSERT INTO reservations(id, customer_id, place, address, start_date, end_date) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, customer_id);
            pstmt.setString(3, place);
            pstmt.setString(4, address);
            pstmt.setString(5, start_date);
            pstmt.setString(6, end_date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tulostetaan kaikki asiakkaat
     */
    public void selectCustomer(){
        String sql = "SELECT * FROM customer;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            System.out.println(conn);
            System.out.println(rs);
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("date_of_birth"));
            }
        } catch (SQLException e) {
            System.out.println("Jokin meni pieleen...");
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Tulostetaan kaikki varaukset
     */
    public void selectReservation(){
        String sql = "SELECT * FROM reservations;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println("hmmm");
                System.out.println(rs.getInt("id"));
                System.out.println(rs.getString("customer_id"));
                System.out.println(rs.getString("place"));
                System.out.println(rs.getString("address"));
                System.out.println(rs.getString("start_date"));
                System.out.println(rs.getString("end_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Luodaan uusi taulukko
     * Tämä metodi oli tarpeellinen vain harkkatyön alussa...
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
