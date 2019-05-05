/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Domain.User.Permission;
import Domain.User.Role;
import Domain.User.User;
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
public class UserManager {

    private static PreparedStatement pre = null;
    private static ResultSet result = null;

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserManager.currentUser = currentUser;
    }

    public static boolean login(String username, String password) throws SQLException {
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
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentUser = new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), result.getInt("roleid"), getRoleName(result.getInt("roleid")));
        return true;
    }

    private static String getRoleName(int roleID) {

        String sql = "SELECT * FROM role WHERE id = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            String roleToString = String.valueOf(roleID);
            pre.setString(1, roleToString);

            result = pre.executeQuery();
            if (result.next()) {
                return result.getString("name");
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //Henter permissions som en rolle har. Argumentet er ID'et på den rolle som undersøges
    public static ArrayList<Permission> getPermissions(int roleID) {
        //Opretter ArrayList til at gemme permissions
        ArrayList<Permission> permissions = new ArrayList<>();

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
                int temp = result.getInt("permission_id");
                String tempInfo = getPermissionInfo(temp);
                permissions.add(new Permission(tempInfo,temp));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Returnere de fundne permissions
        return permissions;
    }

    private static String getPermissionInfo(int permissionID) {

        String sql = "SELECT * FROM permission where id=?";

        try {
            //Indsætter permissionID i det forrige statement
            pre = Connector.getCon().prepareStatement(sql);

            String roleToString = String.valueOf(permissionID);
            pre.setString(1, roleToString);

            //Query resultater hentes
            result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                return result.getString("name");

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
