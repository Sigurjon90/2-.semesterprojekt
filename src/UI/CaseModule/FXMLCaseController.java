package UI.CaseModule;

import Domain.CaseModule.Case;
import Domain.User.Resident;
import Domain.User.SocialWorker;
import Persistence.UserManager;
import UI.Vault;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
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
        currentLoggedOn = new SocialWorker("Daniel", "Szenczi", "Dan", "1234");
        Resident r1 = new Resident("Alex", "Tholle", "altho18", "123");
        currentLoggedOn.createCase("dsdsda", "Alkohol problem", "Alkoholproblemer kan have forskellige sværhedsgrader.\n"
                + "\n"
                + "Et stort alkoholforbrug (storforbrug) vil sige et forbrug over 14 genstande per uge for kvinder og mere end 21 genstande per uge for mænd.\n"
                + "\n"
                + "Et stort alkoholforbrug kan føre til mere end 200 legemlige og psykiske sygdomme og kaldes ofte et risikofyldt forbrug. \n"
                + "\n"
                + "Et skadeligt forbrug er et alkoholforbrug, der er så stort, at det har medført en alkoholskade. Når man er alkoholafhængig, har man ikke mere kontrol over sit forbrug af alkohol.\n"
                + "\n"
                + "Afhængighed indebærer blandt andet ubehagelige og i værste fald livstruende abstinenser, når man forsøger at holde op med at drikke. Afhængigheden påvirker forholdet til ens familie, venner og kolleger og fører til ensomhed.\n"
                + "\n"
                + "Alkoholafhængighed har mange sociale konsekvenser med risiko for at miste arbejde, blive skilt og føre til dårlig økonomi. \n"
                + "\n"
                + "Alle mennesker kan udvikle alkoholafhængighed, hvis alkoholforbruget er stort og varer i længere tid.\n"
                + "\n"
                + "Hvor meget og hvor lang tid man skal drikke, før man bliver afhængig varierer fra person til person. Arvelighed spiller ind på, hvor hurtigt man udvikler afhængighed og måske også på, hvor svært afhængig, man bliver. ", r1);

        obsCaseList = FXCollections.observableArrayList();
        caseList.setItems(obsCaseList);

        update();
        makeStageDragable();

    }
    
//     void checkPermissions() {
//        if (!UserManager.getCurrentUser().checkForPermission(1)) {
//            btn_diary.setDisable(true);
//        }
//        if (!UserManager.getCurrentUser().checkForPermission(2)) {
//            btn_calendar.setDisable(true);
//        }
//        if (!UserManager.getCurrentUser().checkForPermission(3)) {
//            btn_case.setDisable(true);
//        }
//    }

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

    private void update() {
        obsCaseList.clear();
        caseHandler(currentLoggedOn.getCases());
    }

    private void caseHandler(Map<Integer, Case> cases) {
        for (Map.Entry<Integer, Case> entry : cases.entrySet()) {
            obsCaseList.add(entry.getValue());
        }
    }

    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void editCaseAction(ActionEvent event) throws IOException {
        if (!caseList.getSelectionModel().isEmpty() && UserManager.getCurrentUser().checkForPermission(6)) {

            Vault.currentCase = caseList.getSelectionModel().getSelectedItem();
            if (!Vault.currentCase.isClosed()) {
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
            warningLabel.setText("ingeng sag er valgt");
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
            FXMLLoader createScene = new FXMLLoader(getClass().getResource("FXMLCaseCreator.fxml"));
            Parent createRoot = (Parent) createScene.load();
            Stage createStage = new Stage();
            createStage.setScene(new Scene(createRoot));
            createStage.initStyle(StageStyle.UNDECORATED);
            createStage.show();
        }
    }

    @FXML
    private void searchCaseAction(ActionEvent event) {
        if (!searchIDField.getText().isEmpty() && searchIDField.getText().matches("\\d+")) {
            Integer idSearch = Integer.parseInt(searchIDField.getText());
            if (obsCaseList.contains(currentLoggedOn.getCases().get(idSearch))) {
                obsCaseList.clear();
                obsCaseList.add(currentLoggedOn.getCases().get(idSearch));
                warningLabel.setOpacity(0);
            } else {
                obsCaseList.clear();
            }
        } else {
            warningLabel.setOpacity(1);
        }

    }

    @FXML
    private void resetListAction(ActionEvent event) {
        obsCaseList.clear();
        caseHandler(currentLoggedOn.getCases());
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
        } catch (NullPointerException ex) {

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

}
