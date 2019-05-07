/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Domain.DiaryModule.Entry;
import Domain.User.CareWorker;
import Domain.User.Resident;
import Domain.User.User;
import Persistence.UserManager;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author jens
 */
public class FXMLVaultController implements Initializable {

    @FXML
    private Label lb_residents;

    @FXML
    private Button btn_calendar;

    @FXML
    private JFXListView<User> listview_residents;

    @FXML
    private Button btn_diary;

    @FXML
    private Button btn_case;

    @FXML
    private AnchorPane vaultPane;

    private ObservableList<User> residentsObs;

    private ArrayList<User> tempResidents;

    private ListProperty<Entry> listProperty = new SimpleListProperty<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (UserManager.getCurrentUser().checkForPermission(16)) {
            listview_residents.setVisible(true);
            tempResidents = UserManager.getResidents(UserManager.getCurrentUser().getRoleID());
            residentsObs = FXCollections.observableArrayList(tempResidents);
            listview_residents.setItems(residentsObs);
        } else {
            listview_residents.setVisible(false);
        }
    }

    @FXML
    void setCurrentResident() {
        User tempUser = listview_residents.getSelectionModel().getSelectedItem();
        UserManager.setCurrentResident(tempUser);
        System.out.println(UserManager.getCurrentResident());
    }
    
    
    @FXML
    void diaryHandler(ActionEvent event) throws IOException {

        if (UserManager.getCurrentUser().checkForPermission(1)) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            System.out.println("You don't have permission for this action");
        }

    }

    @FXML
    void calendarHandler(ActionEvent event) throws IOException {
        if (UserManager.getCurrentUser().checkForPermission(2)) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/CalendarModule/FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            System.out.println("You don't have permission for this action");
        }

    }

    @FXML
    void caseHandler(ActionEvent event) throws IOException {
        if (UserManager.getCurrentUser().checkForPermission(3)) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/CaseModule/FXMLCase.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            System.out.println("You don't have permission for this action");
        }
    }

    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) vaultPane.getScene().getWindow();
        stage.setIconified(true);
    }
}
