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

public class UserManager {

    private static PreparedStatement pre = null;

    private static User currentUser;
    private static User currentResident;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserManager.currentUser = currentUser;
    }

    public static void setCurrentResident(User chosenResident) {
        UserManager.currentResident = chosenResident;
    }

    public static User getCurrentResident() {
        return UserManager.currentResident;
    }

    public static boolean login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? and password = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet result = pre.executeQuery();
            if (!result.next()) {
                return false;
            }

            currentUser = new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), result.getInt("roleid"), getRoleType(result.getInt("roleid")), result.getInt("id"));

        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static String getRoleType(int roleID) {

        String sql = "SELECT * FROM role WHERE id = " + roleID;

        try {
            pre = Connector.getCon().prepareStatement(sql);
            ResultSet result = pre.executeQuery();
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
        String sql = "SELECT * FROM role_to_permission WHERE role_id = " + roleID;

        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            System.out.println("Start loop");
            while (result.next()) {
                System.out.println("ran once");
                int temp = result.getInt("permisson_id");
                String tempInfo = getPermissionInfo(temp);
                permissions.add(new Permission(tempInfo, temp));
            }
            System.out.println("end loop");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //Returnere de fundne permissions
        return permissions;
    }

    private static String getPermissionInfo(int permissionID) {

        String sql = "SELECT * FROM permission where id = " + permissionID;

        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            if (result.next()) {
                return result.getString("name");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

//\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//////////////////////////////////////////////////////////////////////////////////////////////////
    //Finder residents tilhørende det givne userID fra metodens argument
    public static ArrayList<User> getResidents(int userID) {
        //Opretter ArrayList til at gemme residents
        ArrayList<User> residents = new ArrayList<>();

        String sql = "SELECT * FROM residents where care_worker_id = " + userID + " or social_worker_id = " + userID;

        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            while (result.next()) {
                System.out.println("Added User from worker");
                residents.add(getUser(result.getInt("resident_id")));
            }
            return residents;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Opretter en user ud fra en beboers id
    private static User getUser(int residentID) {
        String sql = "SELECT * FROM users WHERE id = " + residentID;
        //Opretter User til at gemme brugerens oplysninger
        User user = null;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                user = new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), result.getInt("roleid"), getRoleType(result.getInt("roleid")), result.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

///////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ADMIN METHODS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
//////////////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            ResultSet result = pre.executeQuery();

            while (result.next()) {
                users.add(new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), result.getInt("roleid"), getRoleType(result.getInt("roleid")), result.getInt("id")));
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void createUser() {

    }

    public static String deleteUser(int user_id) {
        String sql = "Delete from users where id = " + user_id;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {

        }
        return "Brugeren er slettet fra Vaults DataBase. Copyright(2019)";

    }

}
