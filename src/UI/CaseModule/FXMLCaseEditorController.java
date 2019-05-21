package UI.CaseModule;

import Persistence.CaseRepository;
import Persistence.UserManager;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLCaseEditorController implements Initializable {

    private String attachedFile = null;
    private FileChooser chooser = new FileChooser();
    private ArrayList<String> attachedFiles;
    private ObservableList<String> obsFileList = FXCollections.observableArrayList();
    
    @FXML
    private JFXListView<String> fileView;
    @FXML
    private AnchorPane caseModulePane;
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private JFXTextArea descriptionArea;
    @FXML
    private JFXTextField serviceField;
    @FXML
    private JFXTextField residentNameField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXButton attacheFileBtn;
    @FXML
    private Label numberOfFiles;
    @FXML
    private JFXButton Backbtn;
    @FXML
    private Label warningCreateLabel;
    @FXML
    private JFXButton closeCaseBtn;
    @FXML
    private JFXButton saveEditBtn;
    @FXML
    private JFXTextField closeReasonField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (CaseRepository.getSelectedCase().getAttachedFiles() != null) {
            Date date = new Date();
            obsFileList = FXCollections.observableArrayList(CaseRepository.getSelectedCase().getAttachedFiles());
            residentNameField.setEditable(false);
            serviceField.setEditable(false);
            titleField.setEditable(false);
            titleField.setText(CaseRepository.getSelectedCase().getTitle());
            serviceField.setText(CaseRepository.getSelectedCase().getCaseType());
            residentNameField.setText(UserManager.getUser(CaseRepository.getSelectedCase().getResidentID()).getFirstName());
            descriptionArea.setText(CaseRepository.getSelectedCase().getDescription() + "\nNy redigering:" + date.toGMTString() + "\n");
            fileView.setItems(obsFileList);

        } else {
            Date date = new Date();
            serviceField.setEditable(false);
            titleField.setEditable(false);
            titleField.setText(CaseRepository.getSelectedCase().getTitle());
            serviceField.setText(CaseRepository.getSelectedCase().getCaseType());
            residentNameField.setText(UserManager.getUser(CaseRepository.getSelectedCase().getResidentID()).getFirstName());
            descriptionArea.setText(CaseRepository.getSelectedCase().getDescription() + "\nNy redigering:" + date.toGMTString() + "\n");
            fileView.setItems(obsFileList);
            attachedFiles = new ArrayList<>();

            obsFileList = FXCollections.observableArrayList();
            fileView.setItems(obsFileList);
            chooser.setInitialDirectory(new File("."));
            attachedFiles = new ArrayList<>();
        }

    }

    @FXML
    private void exitAction(MouseEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) caseModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void attachFileAction(ActionEvent event) {
        chooser.setTitle("Vedhæft Fil");
        attachedFile = chooser.showOpenDialog(new Stage()).getName();
        attachedFiles.add(attachedFile);
        obsFileList.add(attachedFile);
    }

    @FXML
    private void backToCaseModule(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    private void closeCaseAction(ActionEvent event) throws IOException {
        if (!closeReasonField.getText().isEmpty() && UserManager.getCurrentUser().checkForPermission("close_case")) {

            CaseRepository.closeCase(CaseRepository.getSelectedCase().getCaseID());
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

        } else {
            closeReasonField.setText("Angiv grund");
        }
    }

    @FXML
    private void saveCaseEditAction(ActionEvent event) throws IOException, FileNotFoundException, SQLException {
        if (UserManager.getCurrentUser().checkForPermission("edit_case")) {
            CaseRepository.attachFilesToCase(attachedFile, CaseRepository.getSelectedCase().getCaseID());
            CaseRepository.updateCaseInDB(descriptionArea.getText(), CaseRepository.getSelectedCase().getCaseID());
            CaseRepository.getSelectedCase().setDescription(descriptionArea.getText());
            CaseRepository.getSelectedCase().setAttachedFiles(attachedFiles);
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

}
