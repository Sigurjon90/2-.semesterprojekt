package Persistence;

import Domain.CaseModule.Case;
import Domain.User.User;
import java.sql.Date;
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
                caseArray.add(new Case(result.getString("title"), result.getString("description"), result.getString("type"), result.getDate("creation_date"), result.getBoolean("is_closed"), result.getInt("residentid"), result.getInt("id")));

            }
            return caseArray;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String createCase(Case newCase) {
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
            pre.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return "Sagen er oprettet";

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

}