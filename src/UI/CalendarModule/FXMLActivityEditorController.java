package UI.CalendarModule;

import Domain.CalendarModule.Calendar;
import Domain.DiaryModule.Entry;
import Persistence.DiaryRepository;
import Persistence.UserManager;
import UI.Vault;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLActivityEditorController implements Initializable {

    private ObservableList<String> typestatus = FXCollections.observableArrayList("Medicin", "Udendørsaktivitet", "Indendørsaktivitet");
    private ObservableList<String> typeComboBoxList = FXCollections.observableArrayList();
    private boolean newActivity;
    private double xOffset = 0;
    private double yOffset = 0;

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
    private TextArea descriptionTextField;
    @FXML
    private RadioButton sharedYes;
    @FXML
    private RadioButton entryYes;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ImageView pictoView;
    @FXML
    private JFXButton saveActivityBtn;
    @FXML
    private JFXButton updateActivityBtn;
    @FXML
    private JFXButton cancelActivity;
    @FXML
    private JFXTimePicker endTimeField;
    @FXML
    private JFXTimePicker startTimeField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.setItems(typestatus);
        startTimeField.set24HourView(true);
        endTimeField.set24HourView(true);
        if (Vault.currentActivity != null && !Vault.newAction) {
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
            typeComboBox.setValue(Vault.currentActivity.getType());

        } else {
            saveActivityBtn.setDisable(false);
            updateActivityBtn.setDisable(true);
        }

        makeStageDragable();
        errorLabel.setOpacity(0);
    }

    @FXML
    public void comboAction(ActionEvent event) {
        String imageToGet = typeComboBox.getValue();
        pictoView.setImage(new Image("/icons/" + imageToGet + ".png"));
    }

    @FXML
    public void saveActivity(ActionEvent event) throws IOException, SQLException {
        if (!titleTextField.getText().isEmpty() && startTextField.getValue() != null && endTextField.getValue() != null && !placeTextField.getText().isEmpty() && !descriptionTextField.getText().isEmpty() && !typeComboBox.getValue().isEmpty() && startTimeField.getValue() != null && endTimeField.getValue() != null) {
            LocalDateTime startDate, endDate;

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bekræftigelse");
            alert.setHeaderText(null);
            alert.setContentText("Er du sikker på du vil gemme?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {
                startDate = startTextField.getValue().atTime(startTimeField.getValue());
                endDate = startTextField.getValue().atTime(endTimeField.getValue());

                Calendar.getCurrentCalendar().createActivity(titleTextField.getText(),
                        UserManager.getCurrentUser().getFullName(), placeTextField.getText(), startDate, endDate,
                        descriptionTextField.getText(), typeComboBox.getValue(), sharedYes.isSelected(), entryYes.isSelected());

                if (entryYes.isSelected()) {
                    String shared;
                    if (sharedYes.isSelected()) {
                        shared = "Er en fællesaktivitet";
                    } else {
                        shared = "Er ikke en fællesaktivitet";
                    }
                    String entryString = "Titel: " + titleTextField.getText() + "\n\n" + "Startdato: " + startDate + "\n\n" + "Slutdato: " + endDate + "\n\n" + shared + "\n\n"
                            + "Type: " + typeComboBox.getValue() + "\n\n" + "Sted: " + placeTextField.getText() + "\n\n\n" + descriptionTextField.getText();
                    DiaryRepository.storeEntry(new Entry(startDate.toLocalDate(), entryString));
                }

                Parent root = FXMLLoader.load(getClass().getResource("FXMLCalendar.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
            }
        } else {
            errorLabel.setText("Du har ikke udfyldt alle felter");
            errorLabel.setOpacity(1);
        }
    }

    public void updateActivity(ActionEvent event) throws IOException, SQLException {

        LocalDateTime startDate, endDate;

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil gemme dine ændringer?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            startDate = startTextField.getValue().atTime(startTimeField.getValue());
            endDate = startTextField.getValue().atTime(endTimeField.getValue());

            Vault.currentActivity.updateActivity(titleTextField.getText(), UserManager.getCurrentUser().getFullName(), placeTextField.getText(), startDate, endDate, descriptionTextField.getText(), typeComboBox.getValue(), sharedYes.isSelected(), entryYes.isSelected());
            Calendar.getCurrentCalendar().getCalendar().replace(Vault.currentActivity.getActivityID(), Vault.currentActivity);
            Parent root = FXMLLoader.load(getClass().getResource("FXMLCalendar.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }
        Vault.currentActivity = null;
    }

    @FXML
    public void cancelActivity(ActionEvent event) throws IOException {
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

    @FXML
    private void exitAction(MouseEvent event) {
        System.exit(1);
    }

    @FXML
    private void minimizeAction(MouseEvent event) {
        Stage stage = (Stage) calendarModulePane.getScene().getWindow();
        stage.setIconified(true);
    }

    private void makeStageDragable() {
        calendarModulePane.setOnMousePressed((event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        calendarModulePane.setOnMouseDragged((event) -> {
            Vault.stage.setX(event.getScreenX() - xOffset);
            Vault.stage.setY(event.getScreenY() - yOffset);
            Vault.stage.setOpacity(0.8f);
        });
        calendarModulePane.setOnDragDone((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
        calendarModulePane.setOnMouseReleased((event) -> {
            Vault.stage.setOpacity(1.0f);
        });
    }

}
