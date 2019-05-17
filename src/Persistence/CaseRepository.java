package Persistence;

import Domain.CaseModule.Case;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CaseRepository {

    private static Case selectedCase;

    public static Case getSelectedCase() {
        return selectedCase;
    }

    public static void setSelectedCase(Case selectedCase) {
        CaseRepository.selectedCase = selectedCase;
    }

    private static PreparedStatement pre = null;

    public static ArrayList<Case> getAllCasesByID(int socialWorkerID) {
        String SQL = "SELECT * FROM case_table WHERE socialworker_id = " + socialWorkerID;
        ArrayList<Case> caseArray = new ArrayList<>();

        try {
            pre = Connector.getCon().prepareStatement(SQL);
            ResultSet result = pre.executeQuery();

            while (result.next()) {
                caseArray.add(new Case(result.getString("title"), result.getString("description"), result.getString("type"), result.getDate("creation_date"), result.getBoolean("is_closed"), result.getInt("residentid"), result.getInt("id"), getFiles(result.getInt("id"))));

            }
            return caseArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String createCase(Case newCase) throws FileNotFoundException, SQLException {
        String sql = "insert into case_table(title,type,description,residentid,creation_date,is_closed,socialworker_id) values (?,?,?,?,?,?,?)";

        try {
            pre = Connector.getCon().prepareStatement(sql);

            pre.setString(1, newCase.getTitle());
            pre.setString(2, newCase.getCaseType());
            pre.setString(3, newCase.getDescription());
            pre.setInt(4, newCase.getResidentID());
            pre.setDate(5, newCase.getDate());
            pre.setBoolean(6, false);
            pre.setInt(7, UserManager.getCurrentUser().getID());
            pre.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pre.close();
        }

        return "Sagen er oprettet";

    }

    public static int getMaxCaseID() {
        String SQL = "SELECT * FROM  case_table WHERE id = (SELECT MAX(id) FROM case_table)";

        try {
            pre = Connector.getCon().prepareStatement(SQL);
            ResultSet result = pre.executeQuery();
            while (result.next()) {
                return result.getInt("id");
            }
            pre.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;

    }

    public static void closeCase(int caseID) {
        String SQL = "update case_table set is_closed = true where id =" + caseID;

        try {
            pre = Connector.getCon().prepareStatement(SQL);
            pre.executeUpdate();
            pre.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void updateCaseInDB(String description, int caseID) {
        String SQL = "update case_table set description =? where id =?";
        try {

            pre = Connector.getCon().prepareStatement(SQL);
            pre.setString(1, description);
            pre.setInt(2, caseID);
            pre.executeUpdate();

            pre.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void attachFilesToCase(String fileName, int caseID) throws SQLException, FileNotFoundException {
        String SQL = "INSERT INTO case_file (filename,case_id) VALUES (?,?) ";

        try {
            pre = pre = Connector.getCon().prepareStatement(SQL);
            pre.setString(1, fileName);
            pre.setInt(2, caseID);
            pre.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pre.close();
        }
    }

    public static ArrayList<String> getFiles(int caseID) throws SQLException {
        String SQL = "SELECT * FROM case_file where case_id=" + caseID;
        ArrayList<String> array = new ArrayList<>();

        try {
            pre = pre = Connector.getCon().prepareStatement(SQL);
            ResultSet result;
            result = pre.executeQuery();
            while (result.next()) {
                array.add(result.getString("filename"));
                return array;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CaseRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pre.close();
        }
        return null;

    }

    public static ArrayList<Case> getCaseByID(int caseID) throws SQLException {
        String SQL = "SELECT * FROM case_table where id=" + caseID;
        ArrayList<Case> array = new ArrayList<>();

        try {
            pre = Connector.getCon().prepareStatement(SQL);
            ResultSet result;
            result = pre.executeQuery();

            while (result.next()) {
                array.add(new Case(result.getString("title"), result.getString("description"), result.getString("type"), result.getDate("creation_date"), result.getBoolean("is_closed"), result.getInt("residentid"), result.getInt("id"), getFiles(result.getInt("id"))));

            }
            return array;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
