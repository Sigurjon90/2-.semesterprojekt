package UI.CaseModule;

import Domain.CaseModule.Case;
import Domain.User.Resident;
import Domain.User.SocialWorker;
import Domain.User.User;
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
import javafx.stage.StageStyle;

public class FXMLCaseController implements Initializable {

    private static SocialWorker currentLoggedOn;
    private double xOffset = 0;
    private double yOffset = 0;
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
    private ObservableList<Case> obsCaseList;
    @FXML
    private JFXButton searchCaseBtn;
    @FXML
    private JFXTextField searchIDField;
    @FXML
    private JFXButton resetBtn;
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
        if (!caseList.getSelectionModel().isEmpty() && UserManager.getCurrentUser().checkForPermission(6)) {

            CaseRepository.setSelectedCase(caseList.getSelectionModel().getSelectedItem());
            if (!CaseRepository.getSelectedCase().isClosed()) {
                
                FXMLLoader createScene = new FXMLLoader(getClass().getResource("FXMLCaseEditor.fxml"));
                Parent createRoot = (Parent) createScene.load();
                Stage createStage = new Stage();
                createStage.setScene(new Scene(createRoot));
                createStage.initStyle(StageStyle.UNDECORATED);
                createStage.show();
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
        if (UserManager.getCurrentUser().checkForPermission(10)) {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCaseCreator.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    @FXML
    private void searchCaseAction(ActionEvent event) {
        //     CaseRepository.closeCase(2);
//
//        if (!searchIDField.getText().isEmpty() && searchIDField.getText().matches("\\d+")) {
//            Integer idSearch = Integer.parseInt(searchIDField.getText());
//            if (obsCaseList.contains(currentLoggedOn.getCases().get(idSearch))) {
//                obsCaseList.clear();
//                obsCaseList.add(currentLoggedOn.getCases().get(idSearch));
//                warningLabel.setOpacity(0);
//            } else {
//                obsCaseList.clear();
//            }
//        } else {
//            warningLabel.setOpacity(1);
//        }

    }

    @FXML
    private void resetListAction(ActionEvent event) {
        obsCaseList.clear();
        //   caseHandler(currentLoggedOn.getCases());
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

    public static SocialWorker getSocialWorker() {
        return currentLoggedOn;
    }

    @FXML
    private void backToMenuAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/FXMLVault.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

//    

}
