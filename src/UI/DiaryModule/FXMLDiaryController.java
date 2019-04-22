
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModule;

import Domain.DiaryModule.Entry;
import Domain.User.Resident;
import static UI.Vault.stage;
import UI.Vault;
import Domain.DiaryModule.Entry;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jens
 */


public class FXMLDiaryController implements Initializable {

    @FXML
    private JFXListView<Entry> list_entrys;
    @FXML
    private JFXTextField textfiels_search;
    @FXML
    private Label lb_file;
    @FXML
    private JFXTextArea textarea_entry;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private JFXButton btn_delete;
    @FXML
    private AnchorPane DiaryPane;
    @FXML
    private JFXButton btn_back;
    @FXML
    private ImageView image_file;
    @FXML
    private Label lb_search;
    @FXML
    private JFXButton btn_newEntry;

    public static Entry selectedEntryForEdit;
    public ObservableList<Entry> list;
    private ListProperty<Entry> listProperty = new SimpleListProperty<>();

    Resident resident = new Resident();

    public void setList(ObservableList<Entry> list) {
        this.list = list;
    }

    public Resident getResident() {
        return resident;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Vault.resident.getResidentDiary().getList().put(2, new Entry(new Date(), "hej med dig Mathias"));
        //Vault.resident.getResidentDiary().getList().put(3, new Entry(new Date(), "hej  dig Mathias"));
        //Vault.resident.getResidentDiary().getList().put(1, new Entry(new Date(), "hej med Mathias"));
        list = FXCollections.observableArrayList();
        list_entrys.itemsProperty().bind(listProperty);
        listProperty.set(list);

        updateList();
    }

    public ObservableList<Entry> getList() {
        return list;
    }

    public void updateList() {
        list.clear();

        for (int i = 1; i <= Vault.resident.getResidentDiary().getList().size(); i++) {
            if(Vault.resident.getResidentDiary().getList().get(i).isVisible())
            list.add(Vault.resident.getResidentDiary().getList().get(i));
        }
    }

    @FXML
    void showEntry(MouseEvent event) {
        selectedEntryForEdit = list_entrys.getSelectionModel().getSelectedItem();
        if (!list.isEmpty()) {
            try {
                if (list_entrys.getSelectionModel().getSelectedItem().fileNames() != null) {
                    textarea_entry.setText(list_entrys.getSelectionModel().getSelectedItem().getEntryDescription());
                    lb_file.setText(list_entrys.getSelectionModel().getSelectedItem().fileNames());
                }
                else {
                    textarea_entry.setText(list_entrys.getSelectionModel().getSelectedItem().getEntryDescription());
                }

                //lb_file.setText(filenames);
            } catch (NullPointerException ex) {
            }

        }
    }

    @FXML
    void displayEntryEditor(ActionEvent event) throws IOException {
        if (selectedEntryForEdit != null) {
        selectedEntryForEdit = list_entrys.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryEditor.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        }
    }

    public void updateMap() {
        Vault.resident.getResidentDiary().getList().clear();
        System.out.println(Vault.resident.getResidentDiary().getList());
        for (int i = 0; i < list.size(); i++) {
            Vault.resident.getResidentDiary().getList().put(list.get(i).getId(), list.get(i));
        }

    }

    @FXML
    void deleteEntry(ActionEvent event) {
        if (selectedEntryForEdit != null) {
        
        Vault.resident.getResidentDiary().getList().get(list_entrys.getSelectionModel().getSelectedItem().getId()).setVisible(false);
        updateList();
        }
    }

    @FXML
    void displayEntryCreator(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryCreator.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
    
    @FXML
    void backToMenu (ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/FXMLVault.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
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


}