/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DiaryModul;

import Domain.DiaryModule.Entry;
import Domain.User.Resident;
import static UI.DiaryModul.Starter.stage;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private JFXButton btn_back;
    @FXML
    private ImageView image_file;
    @FXML
    private Label lb_search;
    @FXML
    private JFXButton btn_newEntry;

    public ObservableList<Entry> list;
    private ListProperty<Entry> listProperty = new SimpleListProperty<>();

    Resident resident = new Resident();

    public void setList(ObservableList<Entry> list) {
        this.list = list;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        resident.getResidentDiary().getList().put(2, new Entry(new Date(), "hej med dig Mathias"));
        resident.getResidentDiary().getList().put(3, new Entry(new Date(), "hej  dig Mathias"));
        resident.getResidentDiary().getList().put(1, new Entry(new Date(), "hej med Mathias"));

        list = FXCollections.observableArrayList();
        list_entrys.itemsProperty().bind(listProperty);
        listProperty.set(list);
        
        updateList();
        

        //list.add(new Entry(new Date(), "hej med dig Mathias"));
        // list.add(new Entry(new Date(), "hej med dig Mathias"));
        //list.add(new Entry(new Date(), "hej med dig Mathias"));
    }

    public ObservableList<Entry> getList() {
        return list;
    }
    
    public void updateList(){
           list.clear();
        for (int i = 1; i <= resident.getResidentDiary().getList().size(); i++) {
            list.add(resident.getResidentDiary().getList().get(i));
        }
    }
    @FXML
    void showEntry(MouseEvent event) {
     
        String entryText = list_entrys.getSelectionModel().getSelectedItem().getEntryDescription();
        textarea_entry.setText(entryText);

    }

    @FXML
    void displayEditorWithedit(ActionEvent event) {

    }

    @FXML
    void deleteEntry(ActionEvent event) {

        resident.getResidentDiary().getList().remove(list_entrys.getSelectionModel().getSelectedIndex());

        
        updateList();

        //list_entrys.getItems().remove(list_entrys.getSelectionModel().getSelectedIndex());
    }

    @FXML
    void displayEditor(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryEditor.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

}
