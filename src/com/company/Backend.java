package com.company;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Backend {
    private final String url = "jdbc:postgresql://localhost/test";
    private final String user = "postgres";
    private final String password = "#354JK0020t";

    public Connection connect(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server succesfully");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
