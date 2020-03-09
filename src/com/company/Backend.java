package com.company;
import java.math.BigInteger;
import java.sql.*;

public class Backend {
    private final String url = "jdbc:postgresql://localhost/test";
    private final String user = "postgres";
    private final String password = "#354JK0020t";

    public Connection connect(){
        Connection conn = null;
        try {
            //Class.forNamen tarpeellisuudesta minulla ei ole
            //harmainta aavistusta...
            Class.forName("org.postgresql.Driver");
            //Yhdistetään tietokantaan edellä määriteltyjen muuttujien
            //avulla ja käsitellään virheet mikäli niitä tulee...
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server succesfully!");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public void showCustomers(){
        //Yhdistetään tietokantaan connect-metodin avulla.
        Connection conn = this.connect();
        Statement stmt = null;
        try{
            System.out.println("OOO");
            //stmt muttujaa (tyyppiä Statement) käytetään nyt kyselyjen tekemiseen.
            stmt = conn.createStatement();
            //Valitaan kaikki informaatio mock_data_people taulukosta.
            //Toimii vain "test" tietokannalla, joka on olemassa vain
            // minun koneellani.
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer;");
            while(rs.next()){
                //Tallennetaan nykyisen rivin sarakkeiden tiedot muuttujiin.
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date bdate = rs.getDate("date_of_birth");
                //Tulostetaan kyselyn palauttamat arvot.
                System.out.println(id);
                System.out.println(name);
                System.out.println(bdate);
                System.out.println("=============");
            }
            //En tiiä mikä on näiden virka...
            rs.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e){
            System.out.println("Jokin meni pieleen...");
            System.out.println(e);
            //System.exit(0);
        }
    }
    public String returnCustomer(int id){
        //Yhdistetään tietokantaan connect-metodin avulla.
        Connection conn = this.connect();
        Statement stmt = null;
        String name = null;
        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM customer WHERE " + id + "=id;");
            while(rs.next()){
                name = rs.getString("name");
                //Tulostetaan kyselyn palauttamat arvot.
                System.out.println(name);
                System.out.println("=============");
            }
            //En tiiä mikä on näiden virka...
            rs.close();
            stmt.close();
            conn.close();
            return name;
        }
        catch(Exception e){
            System.out.println("Jokin meni pieleen...");
            System.out.println(e);
            return null;
        }
    }
    public void returnReservation(int id){
        //Yhdistetään tietokantaan connect-metodin avulla.
        Connection conn = this.connect();
        Statement stmt = null;

        int idr = -1;
        String customer_id;
        String place = null;
        String address = null;
        Date start_date = null;
        Date end_date = null;

        try{
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM reservations WHERE " + id + "=id;");
            while(rs.next()){
                idr = rs.getInt("id");
                customer_id = rs.getString("customer_id");
                place = rs.getString("name");
                address = rs.getString("address");
                start_date = rs.getDate("start_date");
                end_date = rs.getDate("end_date");
                //Tulostetaan kyselyn palauttamat arvot.
                System.out.println(place);
                System.out.println("=============");
            }
            //Reservation r = new Reservation(place, customer_id, idr, start_date, end_date);
            //En tiiä mikä on näiden virka...
            rs.close();
            stmt.close();
            conn.close();
            //return r;
        }
        catch(Exception e){
            System.out.println("Jokin meni pieleen...");
            System.out.println(e);
            //return null;
        }
    }
}
