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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens
 */
public class Login extends Connector {

    PreparedStatement pre = null;
    ResultSet result = null;

    public boolean login(String username, String password) {
        super.getCon();
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";

        try {
            pre = con.prepareStatement(sql);
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
            pre = con.prepareStatement(sql);
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
        
    
}
