package UI.CaseModule;

import Domain.CaseModule.Case;

import Persistence.CaseRepository;
import Persistence.UserManager;
import UI.Vault;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLCaseController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    private ObservableList<Case> obsCaseList;
    private ArrayList<Case> tempCases;
    
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private AnchorPane caseModulePane;
    @FXML
    private JFXButton editCaseBtn;
    @FXML
    private JFXButton createCaseBtn;
    @FXML
    private JFXListView<Case> caseList;
    @FXML
    private JFXButton searchCaseBtn;
    @FXML
    private JFXTextField searchIDField;
    @FXML
    private ImageView resetBtn;
    @FXML
    private Label warningLabel;
    @FXML
    private JFXTextArea casePreviewField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempCases = CaseRepository.getAllCasesByID(UserManager.getCurrentUser().getID());
        obsCaseList = FXCollections.observableArrayList(tempCases);
        caseList.setItems(obsCaseList);
        makeStageDragable();
    }

    private void makeStageDragable() {
        caseModulePane.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        caseModulePane.setOnMouseDragged((event) -> {
            Vault.stage.setX(event.getScreenX() - xOffset);
            Vault.stage.setY(event.getScreenY() - yOffset);
            Vault.stage.setOpacity(0.8f);
        });
        caseModulePane.setOnDragDone((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
        caseModulePane.setOnMouseReleased((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void editCaseAction(ActionEvent event) throws IOException {
        if (!caseList.getSelectionModel().isEmpty() && UserManager.getCurrentUser().checkForPermission("edit_case")) {

            CaseRepository.setSelectedCase(caseList.getSelectionModel().getSelectedItem());
            if (!CaseRepository.getSelectedCase().isClosed()) {

                Parent root = FXMLLoader.load(getClass().getResource("FXMLCaseEditor.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                warningLabel.setOpacity(0);
            } else {
                warningLabel.setOpacity(1);
                warningLabel.setText("Sagen kan ikke redigeres");
            }
        } else {
            warningLabel.setOpacity(1);
            warningLabel.setText("Ingen sag er valgt");
        }
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) caseModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void createCaseAction(ActionEvent event) throws IOException {
        if (UserManager.getCurrentUser().checkForPermission("create_case")) {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCaseCreator.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    @FXML
    private void searchCaseAction(ActionEvent event) throws SQLException {
        if (!searchIDField.getText().isEmpty() && searchIDField.getText().matches("\\d+")) {
            if (CaseRepository.getCaseByID(Integer.parseInt(searchIDField.getText())) != null) {
                tempCases = CaseRepository.getCaseByID(Integer.parseInt(searchIDField.getText()));
                obsCaseList = FXCollections.observableArrayList(tempCases);
                caseList.setItems(obsCaseList);
                warningLabel.setOpacity(0);
            } else {
                obsCaseList.clear();
            }
        } else {
            warningLabel.setOpacity(1);
        }
    }

    @FXML
    private void resetListAction(MouseEvent event) throws SQLException {
        obsCaseList.clear();
        tempCases = CaseRepository.getAllCasesByID(UserManager.getCurrentUser().getID());
        obsCaseList = FXCollections.observableArrayList(tempCases);
        caseList.setItems(obsCaseList);

        if (!caseList.isExpanded()) {
            caseList.setExpanded(true);
            caseList.depthProperty().set(1);
        } else {
            caseList.setExpanded(false);
            caseList.depthProperty().set(0);
        }

    }

    @FXML
    private void showCaseInformation(MouseEvent event) {
        try {
            casePreviewField.setText(caseList.getSelectionModel().getSelectedItem().showInformation() + "\n");
            CaseRepository.setSelectedCase(caseList.getSelectionModel().getSelectedItem());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void backToMenuAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/FXMLVault.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
