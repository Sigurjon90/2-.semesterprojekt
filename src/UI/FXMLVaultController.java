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
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;

public class FXMLVaultController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label lb_residents;

    @FXML
    private Button btn_calendar;

    @FXML
    private Button btn_logout;

    @FXML
    private JFXListView<User> listview_residents;

    @FXML
    private AnchorPane vaultPane;

    @FXML
    private Label errorLabel;

    private ObservableList<User> residentsObs;

    private ArrayList<User> tempResidents;

    private ListProperty<Entry> listProperty = new SimpleListProperty<>();
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private Button btn_diary;
    @FXML
    private Button btn_case;
    @FXML
    private Button btn_admin;
    @FXML
    private Line lastLine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();

        if (UserManager.getCurrentUser().checkForPermission("view_residents")) {
            listview_residents.setVisible(true);
            tempResidents = UserManager.getResidents(UserManager.getCurrentUser().getID());
            residentsObs = FXCollections.observableArrayList(tempResidents);
            listview_residents.setItems(residentsObs);

        } else if (UserManager.getCurrentUser().checkForPermission("delete_user") || UserManager.getCurrentUser().checkForPermission("create_user")) {
            btn_diary.setDisable(true);
            btn_calendar.setDisable(true);
            btn_case.setDisable(true);
            btn_admin.setVisible(true);
            btn_admin.setDisable(false);
            lastLine.setVisible(true);

        } else {
            listview_residents.setVisible(false);
            lb_residents.setVisible(false);
        }

        checkPermissions();

    }

    void checkPermissions() {
        if (!UserManager.getCurrentUser().checkForPermission("view_diary")) {
            btn_diary.setDisable(true);
        }
        if (!UserManager.getCurrentUser().checkForPermission("view_calendar")) {
            btn_calendar.setDisable(true);
        }
        if (!UserManager.getCurrentUser().checkForPermission("view_case")) {
            btn_case.setDisable(true);
        }
    }

    @FXML
    void setCurrentResident() {
        User tempUser = listview_residents.getSelectionModel().getSelectedItem();
        UserManager.setCurrentResident(tempUser);
        System.out.println(UserManager.getCurrentResident());
    }

    @FXML
    void logoutHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void diaryHandler(ActionEvent event) throws IOException {
        if (UserManager.getCurrentResident() != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            errorLabel.setText("Du har ikke valgt en beboer");
            errorLabel.setOpacity(1);
        }
    }

    @FXML
    void calendarHandler(ActionEvent event) throws IOException {
        if (UserManager.getCurrentResident() != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/CalendarModule/FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            errorLabel.setText("Du har ikke valgt en beboer");
            errorLabel.setOpacity(1);
        }
    }

    @FXML
    void caseHandler(ActionEvent event) throws IOException {
        if (UserManager.getCurrentResident() != null) {
            Parent root = FXMLLoader.load(getClass().getResource("/UI/CaseModule/FXMLCase.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else {
            errorLabel.setText("Du har ikke valgt en beboer");
            errorLabel.setOpacity(1);
        }
    }

    @FXML
    private void exitAction(MouseEvent event
    ) {
        System.exit(1);
    }

    @FXML
    private void minimizeAction(MouseEvent event
    ) {
        Stage stage = (Stage) vaultPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void administrationAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAdministration.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    private void makeStageDragable() {
        vaultPane.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        vaultPane.setOnMouseDragged((event) -> {
            Vault.stage.setX(event.getScreenX() - xOffset);
            Vault.stage.setY(event.getScreenY() - yOffset);
            Vault.stage.setOpacity(0.8f);
        });
        vaultPane.setOnDragDone((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
        vaultPane.setOnMouseReleased((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
    }
}
