package Persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivityRepository {

    public static boolean storeActivity(String place, String description, String type, String startDate, String endDate, Boolean shared, Boolean entry, String title, int residentID, String creator) throws SQLException {
        Statement storeStatement = null;
        String sql = "INSERT INTO activity " + "VALUES ('" + title + "', '" + description + "', '" + type + "', '" + place + "', '" + startDate + "', '" + endDate + "', " + shared + ", " + entry + ", " + residentID + ", '" + creator + "' )";
        try {
            storeStatement = Connector.getCon().createStatement();
            storeStatement.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            storeStatement.close();
        }

        return false;
    }

    // query til at hente id på aktiviteter forbundet til en resident
    public static ArrayList<Integer> getActivityIDs(int residentID) throws SQLException {
        String sql = "SELECT * FROM activity where residentid = " + residentID;
        PreparedStatement getIDs = null;
        ArrayList<Integer> activityIDs = new ArrayList<>();

        try {
            getIDs = Connector.getCon().prepareStatement(sql);
            // Query resultater hentes
            ResultSet result = getIDs.executeQuery();
            while (result.next()) {
                activityIDs.add(result.getInt("id"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getIDs.close();
        }

        return activityIDs;
    }

    public static ArrayList<Object> getActivityInfo(int activityID) throws SQLException {
        String sql = "SELECT * FROM activity where id = " + activityID;
        PreparedStatement getIDs = null;
        ArrayList<Object> activityInfo = new ArrayList<>();

        try {
            getIDs = Connector.getCon().prepareStatement(sql);
            // Query resultater hentes
            ResultSet result = getIDs.executeQuery();
            if (result.next()) {
                activityInfo.add(result.getString("title"));
                activityInfo.add(result.getString("creator"));
                activityInfo.add(result.getString("place"));
                activityInfo.add(result.getString("start_time"));
                activityInfo.add(result.getString("end_time"));
                activityInfo.add(result.getString("description"));
                activityInfo.add(result.getString("type"));
                activityInfo.add(result.getBoolean("shared"));
                activityInfo.add(result.getBoolean("entry"));
                activityInfo.add(result.getInt("residentid"));
                activityInfo.add(result.getInt("id"));
            } else {
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            getIDs.close();
        }
        return activityInfo;
    }

    public static int getHighestID() throws SQLException {
        String sql = "SELECT id FROM activity";
        PreparedStatement getIDs = null;
        int highestID = 1;
        try {
            getIDs = Connector.getCon().prepareStatement(sql);
            // Query resultater hentes
            ResultSet result = getIDs.executeQuery();
            while (result.next()) {
                if (result.getInt("id") > highestID) {
                    highestID = result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            getIDs.close();
        }
        return highestID;
    }
    
    public static void deleteActivity (int id) throws SQLException{
        String sql = "DELETE FROM activity WHERE id = " + id;
        PreparedStatement deleteRows = null;
        try{
            deleteRows= Connector.getCon().prepareStatement(sql);
            deleteRows.execute();
        } catch (SQLException ex){
            ex.printStackTrace();
        } finally {
            deleteRows.close();
        }
    }

}
