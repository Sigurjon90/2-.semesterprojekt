package UI.DiaryModule;

import static UI.Vault.stage;
import UI.Vault;
import Domain.DiaryModule.Entry;
import Persistence.DiaryRepository;
import Persistence.UserManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDiaryController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    public static Entry selectedEntryForEdit;
    public ObservableList<Entry> obsEntryList;
    public ObservableList<Entry> tempList;
    private ArrayList<Entry> tempEntries;

    @FXML
    private JFXListView<Entry> list_entrys;
    @FXML
    private JFXTextField textfield_search;
    @FXML
    private DatePicker dp_search;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            updateList();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        makeStageDragable();
        checkPermissions();
    }

    private void setList(ObservableList<Entry> list) {
        this.obsEntryList = list;
    }

    private void checkPermissions() {
        if (!UserManager.getCurrentUser().checkForPermission("edit_entry")) {
            btn_edit.setDisable(true);
        }

        if (!UserManager.getCurrentUser().checkForPermission("delete_entry")) {
            btn_delete.setDisable(true);
        }
        if (!UserManager.getCurrentUser().checkForPermission("create_entry")) {
            btn_newEntry.setDisable(true);
        }
    }

    public ObservableList<Entry> getList() {
        return obsEntryList;
    }

    private void updateList() throws SQLException {
        tempEntries = DiaryRepository.getEntrys(UserManager.getCurrentResident().getID());
        obsEntryList = FXCollections.observableArrayList(tempEntries);
        list_entrys.setItems(obsEntryList.sorted());
    }

    @FXML
    private void searchEntryButtonHandler(ActionEvent event) throws IOException {
        tempList = FXCollections.observableArrayList();
        if (dp_search.getValue() == null) {
            list_entrys.setItems(obsEntryList);
        } else if (dp_search.getValue() != null) {
            for (int i = 0; i < obsEntryList.size(); i++) {
                if (obsEntryList.get(i).getDate().equals(dp_search.getValue())) {
                    tempList.add(obsEntryList.get(i));
                    list_entrys.setItems(tempList.sorted());
                }
            }
        }
    }

    @FXML
    private void showEntry(MouseEvent event) {
        selectedEntryForEdit = list_entrys.getSelectionModel().getSelectedItem();
        if (!obsEntryList.isEmpty()) {
            try {
                textarea_entry.setText(list_entrys.getSelectionModel().getSelectedItem().getEntryDescription());
                if (list_entrys.getSelectionModel().getSelectedItem().fileNames() != null) {
                    lb_file.setText(list_entrys.getSelectionModel().getSelectedItem().fileNames());
                }
                
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

    @FXML
    private void deleteEntry(ActionEvent event) throws SQLException {
        if (selectedEntryForEdit != null) {
            DiaryRepository.deleteEntry(list_entrys.getSelectionModel().getSelectedItem().getid());
            updateList();
            textarea_entry.clear();
            lb_file.setText("");
        }

    }

    @FXML
    private void displayEntryCreator(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryCreator.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
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
}
