package UI;

import Domain.User.User;
import Persistence.UserManager;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLAdministrationController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    private AnchorPane vaultPane;

    @FXML
    private JFXListView<User> listview_users;
    private ObservableList<User> usersObs;
    private ArrayList<User> tempUsers;
    private User selectedUser = null;
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private AnchorPane administrationPane;
    @FXML
    private JFXButton backToMenuBtn;
    @FXML
    private JFXButton deleteUserBtn;
    @FXML
    private JFXTextField firstNameField;
    @FXML
    private JFXTextField lastNameField;
    @FXML
    private JFXButton createUserBtn;
    @FXML
    private JFXTextField userNameField;
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXRadioButton residentRadioBtn;
    @FXML
    private ToggleGroup userTypeGroup;
    @FXML
    private JFXRadioButton socialWorkerRadioBtn;
    @FXML
    private JFXRadioButton careWorkerRadioBtn;
    @FXML
    private JFXRadioButton adminRadioBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        tempUsers = UserManager.getAllUsers();
        usersObs = FXCollections.observableArrayList(tempUsers);
        listview_users.setItems(usersObs);

    }

    private void makeStageDragable() {
        administrationPane.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        administrationPane.setOnMouseDragged((event) -> {
            Vault.stage.setX(event.getScreenX() - xOffset);
            Vault.stage.setY(event.getScreenY() - yOffset);
            Vault.stage.setOpacity(0.8f);
        });
        administrationPane.setOnDragDone((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
        administrationPane.setOnMouseReleased((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
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

    @FXML
    private void backToMenuAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLVault.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    public void setSelectedUser() {
        selectedUser = listview_users.getSelectionModel().getSelectedItem();
        infoFillOut();

    }

    public void infoFillOut() {
        adminRadioBtn.setSelected(false);
        firstNameField.setText(selectedUser.getFirstName());
        lastNameField.setText(selectedUser.getLastName());
        userNameField.setText(selectedUser.getUsername());
        passwordField.setText("Ingen information");

        switch (selectedUser.getRoleID()) {
            case 1:
                careWorkerRadioBtn.setSelected(true);
                break;
            case 2:
                socialWorkerRadioBtn.setSelected(true);
                break;
            case 4:
                residentRadioBtn.setSelected(true);
                break;

            case 5:
                adminRadioBtn.setSelected(true);
                break;

            case 6:
                careWorkerRadioBtn.setSelected(true);
                adminRadioBtn.setSelected(true);
                break;

            case 7:
                socialWorkerRadioBtn.setSelected(true);
                adminRadioBtn.setSelected(true);
                break;

        }

    }

    @FXML
    private void deleteUserAction(ActionEvent event) {
        UserManager.deleteUser(selectedUser.getID());
    }

    @FXML
    private void createUserAction(ActionEvent event) {

    }

}
