package UI.DiaryModule;

import static UI.Vault.stage;
import Domain.DiaryModule.Entry;
import Persistence.DiaryRepository;
import UI.Vault;
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
            entry = new Entry(dp_date.getValue(), textarea_des.getText());
        }

        DiaryRepository.storeEntry(entry);
        
        //Vault.resident.getResidentDiary().getMap().put(entry.getEntryID(), entry);
        //System.out.println("unikt id for entry = " + entry.getEntryID());
       
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
