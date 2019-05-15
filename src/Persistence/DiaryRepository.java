package Persistence;

import Domain.DiaryModule.Entry;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
        //  String entryid = UUID.randomUUID().toString();
        String sql = "insert into entry(description, dato, residentid) values (?,?,?)";
        
        try {
            pre = Connector.getCon().prepareStatement(sql);
            
            pre.setString(1, description);
            pre.setString(2, dato);
            pre.setInt(3, getResidentID());
            
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with store entry");
        }
    }
    public static int makeEntryid (){
        String sql = "select max(id) from entry";
         int resultOfId = 0;
        try {
            pre = Connector.getCon().prepareStatement(sql);
            
            ResultSet result = pre.executeQuery();
            resultOfId = result.getInt("id");

            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with store entry");
        }
        return resultOfId;
        
    }

    public static void storeEntryFile(Entry entry) {
        int entry_id = entry.getid();
        System.out.println("entty.getid: " +entry.getid()+" entty_id: " + entry_id);
        ArrayList<File> listOfFiles = new ArrayList<>(entry.getFiles());
        System.out.println("List of files: "+listOfFiles);
        String sql = "insert into entry_file(filename, entry_id) values (?,?)";
        
        for (int i = 0; i < listOfFiles.size(); i++) {
            String filename = listOfFiles.get(i).getName();
            try {
                pre = Connector.getCon().prepareStatement(sql);
                pre.setString(1, filename);
                pre.setInt(2, makeEntryid());
                pre.executeUpdate();
                pre.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("something went wrong with store entry file");
            }
        }
    }
    
    public static void deleteEntry(int id) {
        
        String sql = "DELETE FROM entry WHERE id = " + id;
        
        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.executeUpdate();
            pre.close();
        } catch (SQLException ex) {
            System.out.println("something went wrong with deleting the entry");
        }
        
    }
    
    public static void updateEntry(String description, String date, int id) {
        
        String sql = "UPDATE entry SET description = ?, dato = ? WHERE id = " + id;
        
        try {
            pre = Connector.getCon().prepareStatement(sql);
            pre.setString(1, description);
            pre.setString(2, date);
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
                myEntry = new Entry(LocalDate.parse(result.getString("dato")), result.getString("description"), result.getInt("id"));
                //UserManager.getCurrentUser().getResidentDiary().getMap().put(result.getInt("entryid"), myEntry);
                myList.add(myEntry);
                
            }
            return myList;
        } catch (SQLException e) {
        }
        return null;
        
    }
    
}
