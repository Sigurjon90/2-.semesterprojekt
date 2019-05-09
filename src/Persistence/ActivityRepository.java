/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author morte
 */
public class ActivityRepository {

    static boolean storeActivity(String place, String description, String type, String startDate, String endDate, Boolean shared, Boolean entry, String title, int activityID, int residentID) {
        Statement storeStatement;
        String sql = "INSERT INTO activity " + "VALUES ('" + title + "', '" + description + "', '" + type + "', '" + place + "', '" + startDate + "', '" + endDate + "', " + shared + ", " + entry + ", " + residentID + ", " + activityID + ")";
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

}
