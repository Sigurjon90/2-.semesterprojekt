/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModul;

import static UI.Vault.stage;
import Domain.DiaryModule.Entry;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
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
 * @author jens
 */
public class FXMLEntryCreatorController implements Initializable {

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
        // TODO
    }

    @FXML
    void saveNewEntryHandler(ActionEvent event) throws IOException {

        Entry entry = new Entry(dp_date.getValue(), textarea_des.getText());
        Vault.resident.getResidentDiary().getList().put(entry.getId(), entry);
        System.out.println(Vault.resident.getResidentDiary().getList().get(entry.getId()).getEntryDescription());
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
