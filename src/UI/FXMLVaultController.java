/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import static UI.Vault.stage;
import com.jfoenix.controls.JFXListView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author jens
 */
public class FXMLVaultController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private Label lb_residents;

    @FXML
    private Button btn_calendar;

    @FXML
    private JFXListView<?> listview_residents;

    @FXML
    private Button btn_diary;

    @FXML
    private Button btn_case;

    @FXML
    void diaryHandler(ActionEvent event) throws IOException {
Parent root = FXMLLoader.load(getClass().getResource("/UI/DiaryModul/FXMLDiary.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void calendarHandler(ActionEvent event) throws IOException {
Parent root = FXMLLoader.load(getClass().getResource("/UI/CalendarModul/FXMLCalender.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void caseHandler(ActionEvent event) throws IOException {
Parent root = FXMLLoader.load(getClass().getResource("/UI/CaseModul/FXMLCase.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
