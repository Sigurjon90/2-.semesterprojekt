package UI.CalendarModule;

import Domain.User.User;
import UI.Vault;
import static UI.Vault.stage;
import static UI.Vault.testCalendar;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLActivityEditorController implements Initializable {

    ObservableList<String> typestatus = FXCollections.observableArrayList("Medicin", "Udendørsaktivitet", "Indendørsaktivitet");
    ObservableList<String> typeComboBoxList = FXCollections.observableArrayList();
    private boolean newActivity;

    
    @FXML
    private AnchorPane calendarModulePane;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker startTextField;
    @FXML
    private DatePicker endTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private RadioButton sharedYes;
    @FXML
    private RadioButton entryYes;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ImageView pictoView;
    @FXML
    private Button saveActivityBtn;
    @FXML
    private Button updateActivityBtn;
    @FXML
    private Button cancelActivity;
    @FXML
    private JFXTimePicker endTimeField;
    @FXML
    private JFXTimePicker startTimeField;

    @FXML
    public void comboAction(ActionEvent event) {
        String imageToGet = typeComboBox.getValue();
        System.out.println(imageToGet);
        pictoView.setImage(new Image("/UI/CalendarModule/" + imageToGet + ".png"));
    }

    @FXML
    public void saveActivity(ActionEvent event) throws IOException {

        LocalDateTime startDate, endDate;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil gemme?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            startDate = startTextField.getValue().atTime(startTimeField.getValue());
            endDate = startTextField.getValue().atTime(endTimeField.getValue());

            testCalendar.createActivity(titleTextField.getText(), Vault.currentLoggedOn, placeTextField.getText(), startDate, endDate, descriptionTextField.getText(), typeComboBox.getValue(), sharedYes.isSelected(), entryYes.isSelected());
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    public void updateActivity(ActionEvent event) throws IOException {

        LocalDateTime startDate, endDate;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil gemme dine ændringer?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            startDate = startTextField.getValue().atTime(startTimeField.getValue());
            endDate = startTextField.getValue().atTime(endTimeField.getValue());

            Vault.currentActivity.updateActivity(titleTextField.getText(), Vault.currentLoggedOn, placeTextField.getText(), startDate, endDate, descriptionTextField.getText(), typeComboBox.getValue(), sharedYes.isSelected(), entryYes.isSelected());
            testCalendar.getCalender().replace(Vault.currentActivity.getId(), Vault.currentActivity);
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        Vault.currentActivity = null;
    }

    @FXML
    public void annullerActivity(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil annullere?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.setItems(typestatus);
        startTimeField.set24HourView(true);
        endTimeField.set24HourView(true);
        if (Vault.currentActivity != null && !Vault.newAction) {
            //newActivity = false;
            saveActivityBtn.setDisable(true);
            updateActivityBtn.setDisable(false);
            titleTextField.setText(Vault.currentActivity.getTitle());
            startTextField.setValue(Vault.currentActivity.getStartDate());
            endTextField.setValue(Vault.currentActivity.getEndDate());
            startTimeField.setValue(Vault.currentActivity.getStartTime());
            endTimeField.setValue(Vault.currentActivity.getEndTime());
            placeTextField.setText(Vault.currentActivity.getPlace());
            sharedYes.setSelected(Vault.currentActivity.getShared());
            entryYes.setSelected(Vault.currentActivity.getEntry());
            descriptionTextField.setText(Vault.currentActivity.getDescription());

        } else {
            saveActivityBtn.setDisable(false);
            updateActivityBtn.setDisable(true);
        }

    }
    
    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) calendarModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

}
