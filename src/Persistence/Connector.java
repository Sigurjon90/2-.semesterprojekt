package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Connector {

    Connection con = null;
    final String connect = "jdbc:postgresql://balarama.db.elephantsql.com:5432/epkbcztc";
    final String user = "epkbcztc";
    final String pass = "0zO85qYObwO066EbP2g0PcZvwTOJ7pMR";

    public Connection getCon() {
         try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("a");
            con = DriverManager.getConnection(connect, user, pass);
            System.out.println("12");
            Statement state = con.createStatement();
            ResultSet result = state.executeQuery("SELECT * FROM User LIMIT 100");

            while (result.next()) {
                System.out.println(result.getString("first_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

}
