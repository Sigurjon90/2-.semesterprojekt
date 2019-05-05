package UI.CaseModule;

import Persistence.UserManager;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLCaseEditorController implements Initializable {

    private File attachedFile = null;
    private FileChooser chooser = new FileChooser();
    private List<File> attachedFiles;
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
    private JFXListView<String> fileView;
    private ObservableList<String> obsFileList;
    @FXML
    private JFXButton closeCaseBtn;
    @FXML
    private JFXButton saveEditBtn;
    @FXML
    private JFXTextField closeReasonField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        obsFileList = FXCollections.observableArrayList();
        fileView.setItems(obsFileList);
        attachedFiles = new ArrayList<>();
        attachedFiles = Vault.currentCase.getAttachedFiles();
        chooser.setInitialDirectory(new File("."));
        residentNameField.setEditable(false);
        serviceField.setEditable(false);
        titleField.setEditable(false);
        titleField.setText(Vault.currentCase.getTitle());
        serviceField.setText(Vault.currentCase.getCaseType());
        residentNameField.setText(Vault.currentCase.getResident().getFirstName());
        descriptionArea.setText(Vault.currentCase.getDescription() + "\nNy redigering: \n");

        for (File f : Vault.currentCase.getAttachedFiles()) {
            obsFileList.add(f.getName());
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
        attachedFile = chooser.showOpenDialog(new Stage());
        attachedFiles.add(attachedFile);
        obsFileList.add(attachedFile.getName());

    }

    @FXML
    private void backToCaseModule(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void closeCaseAction(ActionEvent event) {
        if (!closeReasonField.getText().isEmpty() && UserManager.getCurrentUser().checkForPermission(15)) {
            Vault.currentCase.closeCase(true, closeReasonField.getText());
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            closeReasonField.setText("Angiv grund");
        }
    }

    @FXML
    private void saveCaseEditAction(ActionEvent event) {
        Vault.currentCase.setDescription(descriptionArea.getText());
        Vault.currentCase.setAttachedFiles(attachedFiles);
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

}
