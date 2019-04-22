/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModule;

import static UI.Vault.stage;
import Domain.DiaryModule.Entry;
import UI.Vault;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    @FXML
    private Label lb_error;

    private File file = null;
    private FileChooser chooser = new FileChooser();
    private List<File> fileList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fileList = new ArrayList<>();
        chooser.setInitialDirectory(new File("."));
    }

    @FXML
    void saveFile(ActionEvent event) {
        chooser.setTitle("Vedhæft fil");
        file = chooser.showOpenDialog(new Stage());
        fileList.add(file);
    }

    @FXML
    void saveNewEntryHandler(ActionEvent event) throws IOException {
        //if(dp_date.getValue()==null){
        //    lb_error.setText("Indsæt venligst en dato!");
        //}
        //else{
        
        Entry entry = null;
        if (fileList == null) {
            entry = new Entry(dp_date.getValue(), textarea_des.getText());
        } else {

            entry = new Entry(dp_date.getValue(), textarea_des.getText(), fileList);
        }

        Vault.resident.getResidentDiary().getList().put(entry.getId(), entry);
        System.out.println(Vault.resident.getResidentDiary().getList().get(entry.getId()).getEntryDescription());
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDiary.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    //}
    }

    @FXML
    void showDiaryDisplay(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModule/FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
