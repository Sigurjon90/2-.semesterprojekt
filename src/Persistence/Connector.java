package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    private static Connection con;
    private final String connect = "jdbc:postgresql://balarama.db.elephantsql.com:5432/epkbcztc";
    private final String user = "epkbcztc";
    private final String pass = "0zO85qYObwO066EbP2g0PcZvwTOJ7pMR";

    public Connection setupConnection() {

        try {
            con = DriverManager.getConnection(connect, user, pass);
            System.out.println("Connected to the PostgreSQL server successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    
    public static Connection getCon() {
        return con;
    }

}
