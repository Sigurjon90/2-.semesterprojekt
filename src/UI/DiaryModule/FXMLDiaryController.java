package UI.DiaryModule;

import Domain.User.Resident;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLDiaryController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

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

    public static Entry selectedEntryForEdit;
    public ObservableList<Entry> list;
    public ObservableList<Entry> tempList;
    private ListProperty<Entry> listProperty = new SimpleListProperty<>();

    Resident resident = new Resident();

    public void setList(ObservableList<Entry> list) {
        this.list = list;
    }

    public Resident getResident() {
        return resident;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Vault.resident.getResidentDiary().getMap().put(2, new Entry(new Date(), "hej med dig Mathias"));
        //Vault.resident.getResidentDiary().getMap().put(3, new Entry(new Date(), "hej  dig Mathias"));
        //Vault.resident.getResidentDiary().getMap().put(1, new Entry(new Date(), "hej med Mathias"));
        list = FXCollections.observableArrayList();
        tempList = FXCollections.observableArrayList();
        list_entrys.itemsProperty().bind(listProperty);
        listProperty.set(list);
        DiaryRepository.getEntrys(UserManager.getCurrentResident().getID());
        updateList();

        makeStageDragable();

    }

    public ObservableList<Entry> getList() {
        return list;
    }

    public void updateList() {
        list.clear();
       
        for (int i = 0; i <= UserManager.getCurrentResident().getResidentDiary().getMap().size(); i++) {
            System.out.println("te");
            if (UserManager.getCurrentResident().getResidentDiary().getMap().get(i).isVisible()) {
                list.add(UserManager.getCurrentResident().getResidentDiary().getMap().get(i));
                System.out.println("testing");
            }
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
                } else {
                    textarea_entry.setText(list_entrys.getSelectionModel().getSelectedItem().getEntryDescription());
                }

                //lb_file.setText(filenames);
            } catch (NullPointerException ex) {
            }

        }
    }

    @FXML
    void displayEntryEditor(ActionEvent event) throws IOException {
        if (UserManager.getCurrentUser().checkForPermission(4)) {
            if (selectedEntryForEdit != null) {
                selectedEntryForEdit = list_entrys.getSelectionModel().getSelectedItem();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryEditor.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
        }
    }

    public void updateMap() {
        UserManager.getCurrentResident().getResidentDiary().getMap().clear();
        System.out.println(UserManager.getCurrentResident().getResidentDiary().getMap());
        for (int i = 0; i < list.size(); i++) {
            UserManager.getCurrentResident().getResidentDiary().getMap().put(list.get(i).getEntryID(), list.get(i));
        }
    }

    @FXML
    void deleteEntry(ActionEvent event) {
        if (selectedEntryForEdit != null) {
            if (UserManager.getCurrentUser().checkForPermission(12)) {

                UserManager.getCurrentResident().getResidentDiary().getMap().get(list_entrys.getSelectionModel().getSelectedItem().getEntryID()).setVisible(false);
                updateList();

                textarea_entry.clear();
                lb_file.setText("");
            }
        }
    }

    @FXML
    void displayEntryCreator(ActionEvent event) throws IOException {
        if (UserManager.getCurrentUser().checkForPermission(8)) {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLEntryCreator.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
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

    @FXML
    void searchEntryButtonHandler(ActionEvent event) throws IOException {
        if (dp_search.getValue() == null) {
            listProperty.set(list);
            tempList.clear();
        } else if (dp_search.getValue() != null) {
            tempList.clear();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getDate().equals(dp_search.getValue())) {
                    tempList.add(list.get(i));
                    listProperty.set(tempList);
                }
            }

        }
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
