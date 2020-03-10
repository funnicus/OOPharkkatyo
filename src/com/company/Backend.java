package com.company;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
    public void createReservation(String id,
                                  String customer_id,
                                  String place,
                                  String address,
                                  String start_date,
                                  String end_date){
        String sql = "INSERT INTO reservations(id, customer_id, place, address, start_date, end_date) VALUES(?,?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
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
     * Päivitetään varaus tällä metodilla.
     * @param id
     * @param customer_id
     * @param place
     * @param address
     * @param start_date
     * @param end_date
     */
    public void updateReservation(String id,
                                  String customer_id,
                                  String place,
                                  String address,
                                  String start_date,
                                  String end_date) {
        String sql = "UPDATE reservations SET place = ? , "
                + "address = ? , "
                + "start_date = ? , "
                + "end_date = ? "
                + "WHERE id = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, place);
            pstmt.setString(2, address);
            pstmt.setString(3, start_date);
            pstmt.setString(4, end_date);
            pstmt.setString(5, id);
            // update
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
    public ArrayList<Reservation> selectReservation(Customer customer){

        String sql = "SELECT * FROM reservations WHERE customer_id=\"" + customer.getId() + "\";";

        ArrayList<Reservation> list = new ArrayList<>();

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {

                String id = rs.getString("id");
                String customer_id = rs.getString("customer_id");
                String place = rs.getString("place");
                String address = rs.getString("address");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");

                LocalDateTime startTime = null, endTime = null;
                try {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
                    startTime = LocalDateTime.parse(start_date, formatter);
                    endTime = LocalDateTime.parse(end_date, formatter);
                } catch (Exception e) {

                }

                ReservationTarget rt = new ReservationTarget(place, address, "reservationTarget");
                Reservation r = new Reservation(id, rt, customer, id, startTime, endTime);
                list.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
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
