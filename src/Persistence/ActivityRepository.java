/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author morte
 */
public class ActivityRepository {

    public static boolean storeActivity(String place, String description, String type, String startDate, String endDate, Boolean shared, Boolean entry, String title, int residentID, String creator) {
        Statement storeStatement;
        String sql = "INSERT INTO activity " + "VALUES ('" + title + "', '" + description + "', '" + type + "', '" + place + "', '" + startDate + "', '" + endDate + "', " + shared + ", " + entry + ", " + residentID + ", '" + creator + "' )";
        System.out.println(sql);
        try {
            storeStatement = Connector.getCon().createStatement();
            storeStatement.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // query til at hente id på aktiviteter forbundet til en resident
    public static ArrayList<Integer> getActivityIDs(int residentID) {
        String sql = "SELECT * FROM activity where residentid = " + residentID;
        PreparedStatement getIDs;
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
        }
        return activityIDs;
    }

    public static ArrayList<Object> getActivityInfo(int activityID) {
        String sql = "SELECT * FROM activity where id = " + activityID;
        PreparedStatement getIDs;
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
        }
        return activityInfo;
    }
    
    public static int getHighestID(){
        String sql = "SELECT id FROM activity";
        PreparedStatement getIDs;
        int highestID = 1;
        try{
            getIDs = Connector.getCon().prepareStatement(sql);
            // Query resultater hentes
            ResultSet result = getIDs.executeQuery();
            while(result.next()){
                if(result.getInt("id")>highestID){
                    highestID = result.getInt("id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return highestID;
    }

}
