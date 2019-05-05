/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens
 */
public class Login{

    PreparedStatement pre = null;
    ResultSet result = null;

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            result = pre.executeQuery();
            if (!result.next()) {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    
    public int getRoleid(String username, String password){
        
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            result = pre.executeQuery();
            if (result.next()) {
            return result.getInt("roleid");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    return 0;
    }
    
    //Henter permissions som en rolle har. Argumentet er ID'et på den rolle som undersøges
    public ArrayList<Integer> getPermissions(int roleID){
        //Opretter ArrayList til at gemme permissions
        ArrayList<Integer> permissions = new ArrayList<Integer>();
        //Statement der henter de permissions der tilhører en rolle
        String sql = "SELECT * FROM role_to_permission WHERE role_id = ?";

        try {
            //Indsætter roleID i det forrige statement
            pre = Connector.getCon().prepareStatement(sql);
            String roleToString = String.valueOf(roleID);
            pre.setString(1, roleToString);
            //Query resultater hentes
            result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                permissions.add(result.getInt("permissionID"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Returnere de fundne permissions
        return permissions;
    }
        
    
}
