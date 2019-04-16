package UI.CaseModul;

import Domain.CaseModule.Case;
import Domain.User.Resident;
import Domain.User.SocialWorker;
import Domain.User.User;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLCaseController implements Initializable {

    private static SocialWorker currentLoggedOn;

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
        Resident r2 = new Resident("Jens", "Vitus", "jevit18", "456");
        Resident r3 = new Resident("Morten", "Breum", "monoe17", "789");
        currentLoggedOn.createCase("Alkohol problem", "Alkoholproblemer kan have forskellige sværhedsgrader.\n"
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
                + "Hvor meget og hvor lang tid man skal drikke, før man bliver afhængig varierer fra person til person. Arvelighed spiller ind på, hvor hurtigt man udvikler afhængighed og måske også på, hvor svært afhængig, man bliver. ", r3);
        currentLoggedOn.createCase("Stoffer", "problem2", r1);
        currentLoggedOn.createCase("Sex", "problem3?", r2);

        obsCaseList = FXCollections.observableArrayList();
        caseList.setItems(obsCaseList);
        
        editCaseBtn.setDisable(true);

        update();

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
//        FXMLLoader createScene = new FXMLLoader(getClass().getResource("FXMLCaseEditor.fxml"));
//        Parent createRoot = (Parent) createScene.load();
//        Stage createStage = new Stage();
//        createStage.setScene(new Scene(createRoot));
//        createStage.initStyle(StageStyle.UNDECORATED);
//        createStage.show();
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) caseModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void createCaseAction(ActionEvent event) throws IOException {
        FXMLLoader createScene = new FXMLLoader(getClass().getResource("FXMLCaseEditor.fxml"));
        Parent createRoot = (Parent) createScene.load();
        Stage createStage = new Stage();
        createStage.setScene(new Scene(createRoot));
        createStage.initStyle(StageStyle.UNDECORATED);
        createStage.show();
    }

    @FXML
    private void searchCaseAction(ActionEvent event) {
        if (!searchIDField.getText().isEmpty() && searchIDField.getText().matches("\\d+")) {
            Integer idSearch = Integer.parseInt(searchIDField.getText());
            obsCaseList.clear();
            if (obsCaseList.contains(currentLoggedOn.getCases().get(idSearch))) {
                obsCaseList.add(currentLoggedOn.getCases().get(idSearch));
                warningLabel.setOpacity(0);
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
        casePreviewField.setText(caseList.getSelectionModel().getSelectedItem().showInformation() + "\n");

    }

    public static SocialWorker getSocialWorker() {
        return currentLoggedOn;
    }
}
