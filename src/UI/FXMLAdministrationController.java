package UI;

import Domain.User.User;
import Persistence.UserManager;
import static UI.Vault.stage;
import static UI.Vault.testCalendar;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
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
    private ObservableList<User> careworkersObs;
    private ArrayList<User> tempCareworkers;
    private ObservableList<User> socialworkersObs;
    private ArrayList<User> tempSocialworkers;
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
    @FXML
    private Label error_Lb;
    @FXML
    private Label careWorker_Lb;
    @FXML
    private Label socialWorker_Lb;
    @FXML
    private JFXButton NewUserBtn;
    @FXML
    private JFXComboBox<User> comboBoxCareworker;
    @FXML
    private JFXComboBox<User> comboBoxSocialworker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        NewUserBtn.setVisible(true);
        createUserBtn.setVisible(false);

        makeStageDragable();
        tempUsers = UserManager.getAllUsers();
        usersObs = FXCollections.observableArrayList(tempUsers);
        listview_users.setItems(usersObs);

        //Laver en arrayliste med brugere af typen careworker og ligger den ind i listen under choiceboxen careworker_pricker
        tempCareworkers = UserManager.getAllUsersWithRoleID(1);
        careworkersObs = FXCollections.observableArrayList(tempCareworkers);
        comboBoxCareworker.setItems(careworkersObs);

        //Laver en arrayliste med brugere af typen socialworker og ligger den ind i listen under choiceboxen socialworker_pricker
        tempSocialworkers = UserManager.getAllUsersWithRoleID(2);
        socialworkersObs = FXCollections.observableArrayList(tempSocialworkers);
        comboBoxSocialworker.setItems(socialworkersObs);

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
        //if-statement til at kontrollere at det er et element der trykkes på, og ikke bare selve listviewet, hvilket ville resultere i en NullPointer.

        selectedUser = listview_users.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            comboBoxCareworker.setVisible(false);
            comboBoxSocialworker.setVisible(false);
            infoFillOut();
            NewUserBtn.setVisible(true);
            createUserBtn.setVisible(false);

            
            
            if(selectedUser.getRoleID()==4){
            comboBoxCareworker.setVisible(true);
            comboBoxSocialworker.setVisible(true);
            comboBoxCareworker.setPromptText(UserManager.getCareworkerFromResidents(selectedUser.getID()).toString());
            comboBoxSocialworker.setPromptText(UserManager.getSocialworkerFromResidents(selectedUser.getID()).toString());


            }
        }
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

        if (selectedUser != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræftigelse");
            alert.setHeaderText(null);
            alert.setContentText("Er du sikker på du vil slette denne bruger");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                UserManager.deleteUserFromResidents(selectedUser.getID());
                UserManager.deleteUserFromUsers(selectedUser.getID());
                clearFields();
                tempUsers = UserManager.getAllUsers();
                usersObs = FXCollections.observableArrayList(tempUsers);
                listview_users.setItems(usersObs);
            }
        } else {
            System.out.println("Please select a user");
        }

    }

    private int checkUserType() {
        if (careWorkerRadioBtn.isSelected()) {
            return 1;
        } else if (socialWorkerRadioBtn.isSelected()) {
            return 2;
        } else if (residentRadioBtn.isSelected()) {
            return 4;
        } else if (adminRadioBtn.isSelected()) {
            return 5;
        } else if (careWorkerRadioBtn.isSelected() && adminRadioBtn.isSelected()) {
            return 6;
        } else if (socialWorkerRadioBtn.isSelected() && adminRadioBtn.isSelected()) {
            return 7;
        }
        return 0;
    }

    @FXML
    private void createUserAction(ActionEvent event) throws SQLException {

        if (isAllFieldsFilledOut()) {

            int roleid = checkUserType();

            User user = new User(firstNameField.getText(), lastNameField.getText(), userNameField.getText(), passwordField.getText(), roleid);
            //Opret 
            UserManager.createUserInUsers(user);

            //Hvis der er tale om en beboer, skal den også oprettes i resident-table i databasen.
            if (roleid == 4) {
                //Hent beboerens id fra users-table i databasen og opret en resident i resident-table ud fra id'et og et id på henholdsvis socialWorker og careWorker.
                int userID = UserManager.getUserIDByUsername(user.getUsername());
                UserManager.createUserInResidents(comboBoxSocialworker.getSelectionModel().getSelectedItem().getID(), comboBoxCareworker.getSelectionModel().getSelectedItem().getID(), userID);
            }

            clearFields();

            tempUsers = UserManager.getAllUsers();
            usersObs = FXCollections.observableArrayList(tempUsers);
            listview_users.setItems(usersObs);

            comboBoxCareworker.setVisible(false);
            comboBoxSocialworker.setVisible(false);
            careWorker_Lb.setVisible(false);
            socialWorker_Lb.setVisible(false);

            NewUserBtn.setVisible(true);
            createUserBtn.setVisible(false);

        } else {
            error_Lb.setVisible(true);
        }
    }

    @FXML
    private void newUserButtonHandler(ActionEvent event) {
        listview_users.getSelectionModel().clearSelection();
        selectedUser = null;
        clearFields();
        NewUserBtn.setVisible(false);
        createUserBtn.setVisible(true);
        comboBoxCareworker.setVisible(false);
            comboBoxSocialworker.setVisible(false);
    }

    private void clearFields() {

        firstNameField.setText("");
        lastNameField.setText("");
        userNameField.setText("");
        passwordField.setText("");
        adminRadioBtn.setSelected(false);
        careWorkerRadioBtn.setSelected(false);
        socialWorkerRadioBtn.setSelected(false);
        residentRadioBtn.setSelected(false);
    }

    private boolean isAllFieldsFilledOut() {
        if (firstNameField.getText().isEmpty()
                || lastNameField.getText().isEmpty()
                || userNameField.getText().isEmpty()
                || passwordField.getText().isEmpty()
                || isRadiobuttonsFilledOut() == false) {
            System.out.println("returned false");
            return false;

        } else {
            System.out.println("returned true");
            return true;
        }

    }

    private boolean isRadiobuttonsFilledOut() {
        if (careWorkerRadioBtn.isSelected()) {
            return true;
        } else if (socialWorkerRadioBtn.isSelected()) {
            return true;
        } else if (residentRadioBtn.isSelected()) {
            return true;
        }

        return false;
    }

    @FXML
    private void residentRadioButtonHandler(ActionEvent event) {

        adminRadioBtn.setDisable(true);
        adminRadioBtn.setSelected(false);
        comboBoxCareworker.setVisible(true);
        comboBoxSocialworker.setVisible(true);
        careWorker_Lb.setVisible(true);
        socialWorker_Lb.setVisible(true);

    }

    @FXML
    private void careworkerRadioButtonHandler(ActionEvent event) {
        adminRadioBtn.setDisable(false);
        comboBoxCareworker.setVisible(false);
        comboBoxSocialworker.setVisible(false);
        careWorker_Lb.setVisible(false);
        socialWorker_Lb.setVisible(false);
    }

    @FXML
    private void socialworkerRadioButtonHandler(ActionEvent event) {
        adminRadioBtn.setDisable(false);
        comboBoxCareworker.setVisible(false);
        comboBoxSocialworker.setVisible(false);
        careWorker_Lb.setVisible(false);
        socialWorker_Lb.setVisible(false);

    }

}
