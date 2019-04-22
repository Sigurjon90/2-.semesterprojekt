/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModule;

import Domain.DiaryModule.Entry;
import static UI.Vault.stage;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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

/**
 * FXML Controller class
 *
 * @author A
 */
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dp_date.setValue(FXMLDiaryController.selectedEntryForEdit.getDate());

        textarea_des.setText(FXMLDiaryController.selectedEntryForEdit.getEntryDescription());
    }

    @FXML
    void saveEntryHandler(ActionEvent event) throws IOException {


        Vault.resident.getResidentDiary().getList().get(FXMLDiaryController.selectedEntryForEdit.getId()).getFiles().add(file);
        if (textarea_des.getText().equals(FXMLDiaryController.selectedEntryForEdit.getEntryDescription())) {

        } else if (!textarea_des.getText().equals(FXMLDiaryController.selectedEntryForEdit.getEntryDescription())) {

            Vault.resident.getResidentDiary().getList().get(FXMLDiaryController.selectedEntryForEdit.getId()).editDescription(textarea_des.getText());
        }

        FXMLDiaryController.selectedEntryForEdit.editDateAndDescription(textarea_des.getText(), dp_date.getValue());

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void saveFile(ActionEvent event) {
        chooser.setTitle("Vedhæft fil");
        file = chooser.showOpenDialog(new Stage());

    }

    @FXML
    void showDiaryDisplay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
