package UI.DiaryModule;

import static UI.Vault.stage;
import Domain.DiaryModule.Entry;
import Persistence.DiaryRepository;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLEntryCreatorController implements Initializable {
    
    private double xOffset = 0;
    private double yOffset = 0;
    private File file = null;
    private FileChooser chooser = new FileChooser();
    private List<File> fileList;

    @FXML
    private AnchorPane DiaryPane;
    @FXML
    private TextArea textarea_des;
    @FXML
    private DatePicker dp_date;
    @FXML
    private Label lb_file;
    @FXML
    private JFXButton btn_file;
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_cancel;
    @FXML
    private Label lb_error;
    @FXML
    private Label errorLabel;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDragable();
        fileList = new ArrayList<>();
        chooser.setInitialDirectory(new File("."));
    }

    @FXML
    private void saveFile(ActionEvent event) {
        chooser.setTitle("Vedhæft fil");
        file = chooser.showOpenDialog(new Stage());
        fileList.add(file);
    }

    @FXML
    private void saveNewEntryHandler(ActionEvent event) throws IOException, SQLException {

        if (dp_date.getValue() != null && !textarea_des.getText().isEmpty()) {
            Entry entry = null;
            if (fileList == null) {
                entry = new Entry(dp_date.getValue(), textarea_des.getText());
            } else {
                entry = new Entry(dp_date.getValue(), textarea_des.getText(), fileList);
            }
            DiaryRepository.storeEntry(entry);
            DiaryRepository.storeEntryFile(entry);

            Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } else {
            errorLabel.setText("Du har ikke udfyldt alle felter");
            errorLabel.setOpacity(1);
        }
    }

    private void makeStageDragable() {
        DiaryPane.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        DiaryPane.setOnMouseDragged((event) -> {
            Vault.stage.setX(event.getScreenX() - xOffset);
            Vault.stage.setY(event.getScreenY() - yOffset);
            Vault.stage.setOpacity(0.8f);
        });
        DiaryPane.setOnDragDone((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
        DiaryPane.setOnMouseReleased((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
    }

    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) DiaryPane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void showDiaryDisplay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
