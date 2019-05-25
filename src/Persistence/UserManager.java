package Persistence;

import Domain.User.Permission;

import Domain.User.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManager {

    private static PreparedStatement pre = null;

    private static User currentUser;
    public static User currentResident;

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
            
            while (result.next()) {
                
                int temp = result.getInt("permisson_id");
                String tempInfo = getPermissionInfo(temp);
                permissions.add(new Permission(tempInfo, temp));
            }
            

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
                
                residents.add(getUser(result.getInt("resident_id")));
            }
            return residents;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Opretter en user ud fra en beboers id
    public static User getUser(int residentID) {
        String sql = "SELECT * FROM users WHERE id = " + residentID;
        //Opretter User til at gemme brugerens oplysninger
        User user = null;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                
                user = new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), getRoleType(result.getInt("roleid")), result.getInt("id"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public static User getCareworkerFromResidents(int residentID) {
        String sql = "SELECT * FROM residents WHERE resident_id = " + residentID;
        //Opretter User til at gemme brugerens oplysninger
        User user = null;

        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                user = getUser(result.getInt("care_worker_id"));
                //user = new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), result.getInt("roleid"), getRoleType(result.getInt("roleid")), result.getInt("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public static User getSocialworkerFromResidents(int residentID) {
        String sql = "SELECT * FROM residents WHERE resident_id = " + residentID;
        //Opretter User til at gemme brugerens oplysninger
        User user = null;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            ResultSet result = pre.executeQuery();
            //Tilføjer rollens permissions til Arraylisten
            if (result.next()) {
                user = getUser(result.getInt("social_worker_id"));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

    public static int getUserIDByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            //Query resultater hentes
            pre.setString(1, username);
            ResultSet result = pre.executeQuery();

            if (result.next()) {
                return result.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<User> getAllUsersWithRoleID(int roleID) {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users where roleid=?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setInt(1, roleID);
            ResultSet result = pre.executeQuery();

            while (result.next()) {
                users.add(new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), getRoleType(result.getInt("roleid")), result.getInt("id")));
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
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

    public static ArrayList<User> getAllUsersWithOutRoleID() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            ResultSet result = pre.executeQuery();

            while (result.next()) {
                users.add(new User(result.getString("first_name"), result.getString("last_name"), result.getString("username"), result.getString("password"), getRoleType(result.getInt("roleid")), result.getInt("id")));
            }
            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void createUser() {

    }

    public static String deleteUserFromResidents(int user_id) {
        String sql = "Delete from residents where resident_id = " + user_id;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            
            ex.printStackTrace();

        }
        return "Brugeren er slettet fra residents";

    }

    public static String deleteUserFromUsers(int user_id) {
        String sql = "Delete from users where id = " + user_id;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            
            ex.printStackTrace();

        }
        return "Brugeren er slettet fra users";

    }

    public static String createUserInUsers(User user) throws SQLException {

        String sql = "insert into users(first_name,last_name,roleid,username,password) values (?,?,?,?,?)";

        if (!isUsernameRegistrered(user.getUsername())) {

            try {

                pre = Connector.getCon().prepareStatement(sql);
                pre.setString(1, user.getFirstName());
                pre.setString(2, user.getLastName());
                pre.setInt(3, user.getRoleid());
                pre.setString(4, user.getUsername());
                pre.setString(5, user.getPassword());

                pre.executeUpdate();
                pre.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            return "Brugeren er blevet oprettet";
        } else {
            
            return "Brugernavnet er optaget";
        }

    }

    public static String createUserInResidents(int socialWorkerID, int careWorkerID, int residentID) throws SQLException {

        String sql = "insert into residents(social_worker_id,care_worker_id,resident_id) values (?,?,?)";

        try {

            pre = Connector.getCon().prepareStatement(sql);
            pre.setInt(1, socialWorkerID);
            pre.setInt(2, careWorkerID);
            pre.setInt(3, residentID);

            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return "Brugeren er blevet oprettet";

    }

    public static boolean isUsernameRegistrered(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setString(1, username);

            ResultSet result = pre.executeQuery();
            if (result.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void updateUserInUsers(String firstname, String lastname, String username, int roleID, int userID) {
        String SQL = "update users set first_name =?, last_name =?,roleid=?, username=? where id =?";
        try {
            pre = Connector.getCon().prepareStatement(SQL);
            pre.setString(1, firstname);
            pre.setString(2, lastname);
            pre.setString(4, username);
            pre.setInt(3, roleID);
            pre.setInt(5, userID);
            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public static void updateUserInResidents(int userID, int careworkerID, int socialworkerID) {
        String SQL = "update residents set social_worker_id =?, care_worker_id =? where resident_id =?";
        try {
            pre = Connector.getCon().prepareStatement(SQL);
            pre.setInt(1, socialworkerID);
            pre.setInt(2, careworkerID);
            pre.setInt(3, userID);

            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public static void updateSocialWorkerOnResident(int userID, int socialworkerID) {
        String SQL = "update residents set social_worker_id =? where resident_id =?";
        try {
            pre = Connector.getCon().prepareStatement(SQL);
            pre.setInt(1, socialworkerID);
            pre.setInt(2, userID);

            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void updateCareWorkerOnResident(int userID, int careworkerID) {
        String SQL = "update residents set care_worker_id =? where resident_id =?";
        try {
            pre = Connector.getCon().prepareStatement(SQL);

            pre.setInt(1, careworkerID);
            pre.setInt(2, userID);

            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }

    }

    public static void updateUserPassword(String password, int userID) {
        String SQL = "update users set password =? where id =?";
        try {
            pre = Connector.getCon().prepareStatement(SQL);
            pre.setString(1, password);
            pre.setInt(2, userID);
            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }

}
