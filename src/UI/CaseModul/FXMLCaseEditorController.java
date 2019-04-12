package UI.CaseModul;

import UI.CaseModul.FXMLCaseController;
import Domain.CaseModule.Case;
import Domain.User.Resident;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import static UI.Vault.stage;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FXMLCaseEditorController implements Initializable {

    ObservableList<String> typestatus = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> combo;
    @FXML
    private AnchorPane caseModulePane;

    @FXML
    private ImageView exitBtn;

    @FXML
    private ImageView minimizeBtn;
    @FXML
    private JFXTextField serviceField;
    @FXML
    private JFXTextField residentNameField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextArea descriptionArea;
    @FXML
    private ChoiceBox<String> test;
    @FXML
    private ImageView refreshBtn;
    @FXML
    private JFXButton createCase;

    @FXML
    void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    void minimizeAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        descriptionArea.setEditable(false);
        serviceField.setEditable(false);
        residentNameField.setEditable(false);
        titleField.setEditable(false);
        typestatus.add("Sundhed");
        typestatus.add("Mobilitet");
        typestatus.add("Kommunikation");
        test.setItems(typestatus);
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
            typeSetup(test.getSelectionModel().getSelectedItem());
        } catch (NullPointerException ex) {
            System.out.println("Ingen type valgt");
        }
    }

    @FXML
    private void createCase(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil oprette en ny sag?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {

            Case createdCase = new Case(descriptionArea.getText(), test.getSelectionModel().getSelectedItem(), new Resident(residentNameField.getText(), "sdsds", "sdsada", "12312312"));
            FXMLCaseController.getSocialWorker().getCases().put(createdCase.getCaseID(), createdCase);
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCase.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

}
