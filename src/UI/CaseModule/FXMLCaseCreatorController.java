package UI.CaseModule;

import Domain.CaseModule.Case;
import Domain.User.User;
import Persistence.CaseRepository;
import Persistence.UserManager;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLCaseCreatorController implements Initializable {

    private ObservableList<String> typestatus = FXCollections.observableArrayList();
    private String attachedFile = null;
    private FileChooser chooser = new FileChooser();
    private ArrayList<String> attachedFiles;

    @FXML
    private AnchorPane caseModulePane;
    @FXML
    private JFXTextField residentFirstNameField;
    @FXML
    private JFXTextField residentLastNameField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextArea descriptionArea;
    @FXML
    private Label warningCreateLabel;
    @FXML
    private JFXListView<String> fileView;
    private ObservableList<String> obsFileList;
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private JFXButton createCase;
    @FXML
    private JFXButton attacheFileBtn;
    @FXML
    private JFXButton Backbtn;
    @FXML
    private JFXTextField residentUsernameField;
    @FXML
    private JFXComboBox<String> serviceComboBox;
    private ObservableList<String> serviceObs;
    private ArrayList<String> tempService;
    private ObservableList<User> residentObs;
    private ArrayList<User> tempResidents;
    @FXML
    private JFXRadioButton ExistingUserBtn;
    @FXML
    private JFXRadioButton NewUserBtn;
    @FXML
    private JFXComboBox<User> residentComboBox;
    @FXML
    private Label FirstnameLb;
    @FXML
    private Label LastnameLb;
    @FXML
    private Label UsernameLb;

    @FXML
    void exitAction(MouseEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    @FXML
    void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) caseModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chooser.setInitialDirectory(new File("."));
        attachedFiles = new ArrayList<>();
        descriptionArea.setEditable(false);

        residentFirstNameField.setEditable(false);
        residentLastNameField.setEditable(false);
        titleField.setEditable(false);

        obsFileList = FXCollections.observableArrayList();
        fileView.setItems(obsFileList);

        tempService = fillWithDummyValues();
        serviceObs = FXCollections.observableArrayList(tempService);
        serviceComboBox.setItems(serviceObs);

        descriptionArea.setEditable(true);
        residentFirstNameField.setEditable(true);
        residentLastNameField.setEditable(true);
        titleField.setEditable(true);

        tempResidents = UserManager.getAllUsersWithRoleID(4);
        residentObs = FXCollections.observableArrayList(tempResidents);
        residentComboBox.setItems(residentObs);
    }

    private ArrayList<String> fillWithDummyValues() {
        ArrayList<String> ary = new ArrayList<>();
        ary.add("Sundhed");
        ary.add("Mobilitet");
        ary.add("Kommunikation");
        ary.add("Socialt Problem");
        ary.add("Egenomsorg");

        return ary;

    }

    @FXML
    private void createCase(ActionEvent event) throws IOException, SQLException {

        if (ExistingUserBtn.isSelected()) {
            if (!titleField.getText().isEmpty() && !descriptionArea.getText().isEmpty() && residentComboBox.getSelectionModel().getSelectedItem() != null) {
                Case createdCase = null;
                java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Bekræftigelse");
                alert.setHeaderText(null);
                alert.setContentText("Er du sikker på du vil oprette en ny sag?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {

                    createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceComboBox.getSelectionModel().getSelectedItem(), sqlDate, false, residentComboBox.getSelectionModel().getSelectedItem().getID(), null);
                    CaseRepository.createCase(createdCase);
                    if (!obsFileList.isEmpty()) {
                        CaseRepository.attachFilesToCase(attachedFile, CaseRepository.getMaxCaseID());
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                }

            } else {
                warningCreateLabel.setOpacity(1);
            }

        } else if (NewUserBtn.isSelected()) {
            if (!titleField.getText().isEmpty() && !residentFirstNameField.getText().isEmpty() && !residentLastNameField.getText().isEmpty() && !descriptionArea.getText().isEmpty() && !residentUsernameField.getText().isEmpty()) {

                Case createdCase = null;
                java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Bekræftigelse");
                alert.setHeaderText(null);
                alert.setContentText("Er du sikker på du vil oprette en ny sag?");
                Optional<ButtonType> action = alert.showAndWait();

                if (action.get() == ButtonType.OK) {

                    User tempUser = new User(residentFirstNameField.getText(), residentLastNameField.getText(), residentUsernameField.getText());
                    UserManager.createUserInUsers(tempUser);
                    UserManager.createUserInResidents(UserManager.getCurrentUser().getID(), 95, UserManager.getUserIDByUsername(tempUser.getUsername()));
                    createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceComboBox.getSelectionModel().getSelectedItem(), sqlDate, false, UserManager.getUserIDByUsername(tempUser.getUsername()), null);
                    CaseRepository.createCase(createdCase);
                    if (!obsFileList.isEmpty()) {
                        CaseRepository.attachFilesToCase(attachedFile, CaseRepository.getMaxCaseID());
                    }
                    Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                }
            } else {
                warningCreateLabel.setOpacity(1);
            }
        }
    }

    @FXML
    private void backToCaseModule(ActionEvent event
    ) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void attachFileAction(ActionEvent event) {
        chooser.setTitle("Vedhæft Fil");
        attachedFile = chooser.showOpenDialog(new Stage()).getName();
        attachedFiles.add(attachedFile);
        obsFileList.add(attachedFile);
    }

    @FXML
    private void residentRadioHandler(ActionEvent event) {

        if (ExistingUserBtn.isSelected()) {
            residentComboBox.setVisible(true);
            residentFirstNameField.setVisible(false);
            residentLastNameField.setVisible(false);
            residentUsernameField.setVisible(false);
            FirstnameLb.setVisible(false);
            LastnameLb.setVisible(false);
            UsernameLb.setVisible(false);
        } else if (NewUserBtn.isSelected()) {
            residentComboBox.setVisible(false);
            residentFirstNameField.setVisible(true);
            residentLastNameField.setVisible(true);
            residentUsernameField.setVisible(true);
            FirstnameLb.setVisible(true);
            LastnameLb.setVisible(true);
            UsernameLb.setVisible(true);
        }

    }

}
