/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Domain.DiaryModule.Entry;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jens
 */
public class DiaryRepository {

    static String dato;
    static String desription;
    static int residentID;
    static PreparedStatement pre;

    private static int getResidentID() {
        residentID = UserManager.getCurrentResident().getID();

        return residentID;
    }

    public static void storeEntry(Entry entry) {

        String description = entry.getEntryDescription();
        String dato = entry.getDate().toString();
        String entryid = UUID.randomUUID().toString();
        String sql = "insert into entry(description, dato, residentid, entryid) values (?,?,?,?)";

        try {
            pre = Connector.getCon().prepareStatement(sql);

            pre.setString(1, description);
            pre.setString(2, dato);
            pre.setInt(3, getResidentID());
            pre.setString(4, entryid);

            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with store entry");
        }

    }

    public static void deleteEntry(Entry entry) {

        String sql = "DELETE FROM entry WHERE entryid = '" + entry.getEntryID() + "'";

        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with deleting the entry");
        }

    }

    public static void updateEntry(Entry entry) {

        String sql = "UPDATE entry SET description = ?, dato = ? WHERE entryid = '" + entry.getEntryID() + "'";

        try {
            pre = Connector.getCon().prepareStatement(sql);
             pre.setString(1, entry.getEntryDescription());
            pre.setString(2, entry.getDate().toString());
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with updating the entry");
        }

    }

    public static ArrayList<Entry> getEntrys(int residentID) {

        String sql = "select * from entry where residentid = " + residentID;
        Entry myEntry;
        ArrayList<Entry> myList = new ArrayList<>();
        System.out.println("getEntrys");

        try {
            pre = Connector.getCon().prepareStatement(sql);
            ResultSet result = pre.executeQuery();

            while (result.next()) {
                myEntry = new Entry(LocalDate.parse(result.getString("dato")), result.getString("description"), result.getString("entryid"));
                //UserManager.getCurrentUser().getResidentDiary().getMap().put(result.getInt("entryid"), myEntry);
                myList.add(myEntry);

            }
            return myList;
        } catch (SQLException e) {
        }
        return null;

    }

}
