package UI.CaseModule;

import Domain.CaseModule.Case;
import Domain.User.Resident;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLCaseCreatorController implements Initializable {

    private ObservableList<String> typestatus = FXCollections.observableArrayList();
    private File attachedFile = null;
    private FileChooser chooser = new FileChooser();
    private List<File> attachedFiles;

    @FXML
    private AnchorPane caseModulePane;
    @FXML
    private JFXTextField serviceField;
    @FXML
    private JFXTextField residentNameField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextArea descriptionArea;
    @FXML
    private ChoiceBox<String> serviceBox;
    @FXML
    private Label warningCreateLabel;
    @FXML
    private Label numberOfFiles;
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
    private ImageView refreshBtn;
    @FXML
    private JFXButton Backbtn;

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
        serviceField.setEditable(false);
        residentNameField.setEditable(false);
        titleField.setEditable(false);
        descriptionArea.setText("Start med at vælge ydelse i menuen til højre"
                + " og tryk derefter på opdater knappen!");
        typestatus.add("Sundhed");
        typestatus.add("Mobilitet");
        typestatus.add("Kommunikation");
        serviceBox.setItems(typestatus);

        obsFileList = FXCollections.observableArrayList();
        fileView.setItems(obsFileList);

    }

    private void typeSetup(String type) {
        switch (type) {
            case "Sundhed":
                serviceField.setText("Sundheds ydelse");
                break;
            case "Mobilitet":
                serviceField.setText("Mobilitets ydelse");
                break;
            case "Kommunikation":
                serviceField.setText("Komunikations ydelse");
                break;
        }
        descriptionArea.setEditable(true);
        residentNameField.setEditable(true);
        titleField.setEditable(true);

    }

    @FXML
    private void refreshType(MouseEvent event) {
        try {
            typeSetup(serviceBox.getSelectionModel().getSelectedItem());
        } catch (NullPointerException ex) {
            System.out.println("Ingen type valgt");
        }
    }

    @FXML
    private void createCase(ActionEvent event) throws IOException {
        if (!titleField.getText().isEmpty() && !residentNameField.getText().isEmpty() && !descriptionArea.getText().isEmpty()) {
            Case createdCase = null;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræftigelse");
            alert.setHeaderText(null);
            alert.setContentText("Er du sikker på du vil oprette en ny sag?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {

                for (Map.Entry<Integer, Case> entry : FXMLCaseController.getSocialWorker().getCases().entrySet()) {
                    Resident caseResident = entry.getValue().getResident();
                    if (caseResident.getFirstName().equals(residentNameField.getText())) {
                        if (attachedFile == null) {
                            createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceBox.getSelectionModel().getSelectedItem(), caseResident);
                        } else {
                            createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceBox.getSelectionModel().getSelectedItem(), attachedFiles, caseResident);
                        }
                    }
                }

                if (createdCase == null) {
                    if (attachedFile == null) {
                        createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceBox.getSelectionModel().getSelectedItem(), new Resident(residentNameField.getText(), "Last name", "Username", "Password"));
                    } else {
                        createdCase = new Case(titleField.getText(), descriptionArea.getText(), serviceBox.getSelectionModel().getSelectedItem(), attachedFiles, new Resident(residentNameField.getText(), "Last name", "Username", "Password"));
                    }

                }

                FXMLCaseController.getSocialWorker().getCases().put(createdCase.getCaseID(), createdCase);
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } else {
            warningCreateLabel.setOpacity(1);

        }
    }

    @FXML
    private void backToCaseModule(ActionEvent event
    ) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void attachFileAction(ActionEvent event) {
        chooser.setTitle("Vedhæft Fil");
        attachedFile = chooser.showOpenDialog(new Stage());
        attachedFiles.add(attachedFile);
        obsFileList.add(attachedFile.getName());

    }

}
