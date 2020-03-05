package com.company;
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
            System.out.println("Connected to the PostgreSQL server succesfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void statement(){
        //Yhdistetään tietokantaan connect-metodin avulla.
        Connection conn = this.connect();
        Statement stmt = null;
        try{
            //stmt muttujaa (tyyppiä Statement) käytetään nyt kyselyjen tekemiseen.
            stmt = conn.createStatement();
            //Valitaan kaikki informaatio mock_data_people taulukosta.
            //Toimii vain "test" tietokannalla, joka on olemassa vain
            // minun koneellani.
            ResultSet rs = stmt.executeQuery("SELECT * FROM mock_data_people;");
            while(rs.next()){
                //Tallennetaan nykyisen rivin sarakkeiden tiedot muuttujiin.
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                Date bdate = rs.getDate("bdate");
                int ssn = rs.getInt("ssn");
                //Tulostetaan kyselyn palauttamat arvot.
                System.out.println(fname);
                System.out.println(lname);
                System.out.println(bdate);
                System.out.println(ssn);
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
}
