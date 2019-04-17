/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModul;

import Domain.DiaryModule.Entry;
import static UI.DiaryModul.Starter.stage;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
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

        //Entry entry = new Entry(dp_date.getValue(), textarea_des.getText());

        //Vault.resident.getResidentDiary().getList().put(FXMLDiaryController.selectedEntryForEdit.getId(), entry);
        
        if(textarea_des.getText().equals(FXMLDiaryController.selectedEntryForEdit.getEntryDescription())){
            
        }
        else if(!textarea_des.getText().equals(FXMLDiaryController.selectedEntryForEdit.getEntryDescription())){
          
            Vault.resident.getResidentDiary().getList().get(FXMLDiaryController.selectedEntryForEdit.getId()).editDescription(textarea_des.getText());
        }

        FXMLDiaryController.selectedEntryForEdit.editDateAndDescription(textarea_des.getText(), dp_date.getValue());

        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
