package UI.DiaryModule;

import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FXMLEntryEditorController implements Initializable {

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

    private File file = null;
    private FileChooser chooser = new FileChooser();
    private List<File> fileList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dp_date.setValue(FXMLDiaryController.selectedEntryForEdit.getDate());

        textarea_des.setText(FXMLDiaryController.selectedEntryForEdit.getEntryDescription());

        chooser.setInitialDirectory(new File("."));
        fileList = new ArrayList<>();
        
        fileList.addAll(FXMLDiaryController.selectedEntryForEdit.getFiles());

    }

    @FXML
    void saveEntryHandler(ActionEvent event) throws IOException {
        
        
        FXMLDiaryController.selectedEntryForEdit.editEntry(textarea_des.getText(), dp_date.getValue(), fileList);

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void saveFile(ActionEvent event) {
        chooser.setTitle("Vedhæft fil");
        file = chooser.showOpenDialog(new Stage());
        fileList.add(file);
    }

    @FXML
    void showDiaryDisplay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
