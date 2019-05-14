package UI.CalendarModule;

import Domain.CalendarModule.Activity;
import Domain.CalendarModule.Calendar;
import static Domain.CalendarModule.Calendar.enableCurrentCalendar;
import Persistence.ActivityManager;
import Persistence.UserManager;
import UI.Vault;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLCalendarController implements Initializable {

    private ObservableList<Activity> obList;
    private ListProperty<Activity> listProperty = new SimpleListProperty<>();
    ObservableList<String> typestatus = FXCollections.observableArrayList("Medicin", "udendørsaktivitet", "indendørsaktivitet");
    private boolean newActivity;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private AnchorPane parent;
    @FXML
    private AnchorPane calendarModulePane;
    @FXML
    private Accordion accordionPane;
    @FXML
    private JFXButton planBtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private ListView<Activity> mondayList, tuesdayList, wednesdayList, thursdayList, fridayList, saturdayList, sundayList;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker startTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private RadioButton sharedYes;
    @FXML
    private RadioButton entryYes;
    @FXML
    private TextField typeComboBox;
    @FXML
    private JFXButton saveActivityBtn;
    @FXML
    private JFXButton updateActivityBtn;
    @FXML
    private JFXTimePicker endTimeField;
    @FXML
    private JFXTimePicker startTimeField;
    @FXML
    private JFXDatePicker endTextField;
    @FXML
    private JFXDatePicker StartTextField;
    @FXML
    private Label titleLabel;
    @FXML
    private Label slutLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label sharedLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label entryLabel;
    @FXML
    private Label placeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private ImageView pictoView;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Opretter kalender til valgte beboer.
        enableCurrentCalendar();
        //Opdaterer kalenderen med beboerens aktiviteter.
        if (UserManager.getCurrentUser().checkForPermission(16)) {
            ActivityManager.getActivities(UserManager.getCurrentResident().getID());
        } else {
            ActivityManager.getActivities(UserManager.getCurrentUser().getID());
        }
        hide();
        planBtn.setDisable(false);
        deleteBtn.setDisable(true);

        makeStageDragable();

        checkPermissions();

        errorLabel.setOpacity(0);
    }

    void checkPermissions() {
        if (!UserManager.getCurrentUser().checkForPermission(9)) {
            planBtn.setDisable(true);
        }

        if (!UserManager.getCurrentUser().checkForPermission(13)) {
            deleteBtn.setDisable(true);
        }
    }

    @FXML
    public void planAction(ActionEvent event) throws IOException {

        Vault.newAction = true;
        Parent currentParent = FXMLLoader.load(getClass().getResource("FXMLActivityEditor.fxml"));
        Scene scene = new Scene(currentParent);
        stage.setScene(scene);

    }

    @FXML
    public void deleteActivity(ActionEvent event) throws IOException {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Bekræftigelse");
        alert.setHeaderText(null);
        alert.setContentText("Er du sikker på du vil slette denne aftale?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Calendar.getCurrentCalendar().getCalendar().remove(Vault.currentActivity.getActivityID());
        }
        Vault.currentActivity = null;
        deleteBtn.setDisable(true);
        clearAllFields();
        hide();
        updateListView("Monday");
        updateListView("Tuesday");
        updateListView("Wednesday");
        updateListView("Thursday");
        updateListView("Friday");
        updateListView("Saturday");
        updateListView("Sunday");

    }

    public void clearAllFields() {
        titleTextField.setText("");
        startTextField.setValue(LocalDate.now());
        endTextField.setValue(LocalDate.now());
        startTimeField.setValue(LocalTime.now());
        endTimeField.setValue(LocalTime.now());
        placeTextField.setText("");
        sharedYes.setSelected(false);
        entryYes.setSelected(false);
        descriptionTextField.setText("");
    }

    @FXML
    public void clickDay(MouseEvent event) {
        if (event.getSource().toString().contains("Mandag")) {
            updateListView("Monday");
        }
        if (event.getSource().toString().contains("Tirsdag")) {
            updateListView("Tuesday");
        }
        if (event.getSource().toString().contains("Onsdag")) {
            updateListView("Wednesday");
        }
        if (event.getSource().toString().contains("Torsdag")) {
            updateListView("Thursday");
        }
        if (event.getSource().toString().contains("Fredag")) {
            updateListView("Friday");
        }
        if (event.getSource().toString().contains("Lørdag")) {
            updateListView("Saturday");
        }
        if (event.getSource().toString().contains("Søndag")) {
            updateListView("Sunday");
        }
    }

    public void updateListView(String day) {
        obList = FXCollections.observableArrayList(new ArrayList<>());
        Set<Integer> keyList = Calendar.getCurrentCalendar().getKeySet();

        switch (day) {
            case "Monday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 1) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                mondayList.setItems(obList);
                mondayList.refresh();
                break;
            case "Tuesday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 2) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                tuesdayList.setItems(obList);
                tuesdayList.refresh();
                break;

            case "Wednesday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 3) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                wednesdayList.setItems(obList);
                wednesdayList.refresh();
                break;

            case "Thursday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 4) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                thursdayList.setItems(obList);
                thursdayList.refresh();
                break;

            case "Friday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 5) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                fridayList.setItems(obList);
                fridayList.refresh();
                break;

            case "Saturday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 6) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                saturdayList.setItems(obList);
                saturdayList.refresh();
                break;

            case "Sunday":
                for (Integer i : keyList) {
                    if (Calendar.getCurrentCalendar().getActivity(i).getDay() == 7) {
                        obList.add(Calendar.getCurrentCalendar().getActivity(i));
                    }
                }
                sundayList.setItems(obList);
                sundayList.refresh();
                break;
        }
    }

    @FXML
    public void openActivity(MouseEvent event) throws IOException {
        ListView myList;
        Activity myActivity;

        Vault.newAction = false;
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                if (UserManager.getCurrentUser().checkForPermission(5)) {
                    myList = (ListView) event.getSource();
                    myActivity = (Activity) myList.getSelectionModel().getSelectedItem();
                    Parent currentParent;
                    Vault.currentActivity = myActivity;
                    try {
                        currentParent = FXMLLoader.load(getClass().getResource("FXMLActivityEditor.fxml"));
                        Scene scene = new Scene(currentParent);
                        stage.setScene(scene);

                    } catch (IOException ex) {
                        Logger.getLogger(FXMLCalendarController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    errorLabel.setOpacity(0);
                } else {
                    errorLabel.setText("Du har ikke adgang til at redigere i aktiviteter");
                    errorLabel.setOpacity(1);
                }
            }
            if (event.getClickCount() == 1) {
                myList = (ListView) event.getSource();
                myActivity = (Activity) myList.getSelectionModel().getSelectedItem();
                Vault.currentActivity = myActivity;
                if (myList.getSelectionModel().getSelectedItem() != null) {
                    show();
                    deleteBtn.setDisable(false);
                    typeComboBox.setText(Vault.currentActivity.getType());
                    newActivity = false;
                    titleTextField.setText(Vault.currentActivity.getTitle());
                    startTextField.setValue(Vault.currentActivity.getStartDate());
                    endTextField.setValue(Vault.currentActivity.getEndDate());
                    startTimeField.setValue(Vault.currentActivity.getStartTime());
                    endTimeField.setValue(Vault.currentActivity.getEndTime());
                    placeTextField.setText(Vault.currentActivity.getPlace());
                    sharedYes.setSelected(Vault.currentActivity.getShared());
                    entryYes.setSelected(Vault.currentActivity.getEntry());
                    descriptionTextField.setText(Vault.currentActivity.getDescription());
                    if (Vault.currentActivity.getType() != null) {
                        String imageToGet = Vault.currentActivity.getType();
                        System.out.println(imageToGet);
                        pictoView.setImage(new Image("/icons/" + imageToGet + ".png"));
                    } else {
                        pictoView.setImage(null);
                    }
                    startTextField.setEditable(false);
                    startTextField.setOnMouseClicked(e -> {
                        if (!startTextField.isEditable()) {
                            startTextField.hide();
                        }
                    });
                    endTextField.setEditable(false);
                    endTextField.setOnMouseClicked(e -> {
                        if (!endTextField.isEditable()) {
                            endTextField.hide();
                        }
                    });
                    startTimeField.setEditable(false);
                    startTimeField.setOnMouseClicked(e -> {
                        if (!startTimeField.isEditable()) {
                            startTimeField.hide();
                        }
                    });
                    endTimeField.setEditable(false);
                    endTimeField.setOnMouseClicked(e -> {
                        if (!endTimeField.isEditable()) {
                            endTimeField.hide();
                        }
                    });

                }

            }
        }

    }

    public void show() {
        titleTextField.setOpacity(1);
        startTextField.setOpacity(1);
        endTextField.setOpacity(1);
        startTimeField.setOpacity(1);
        endTimeField.setOpacity(1);
        placeTextField.setOpacity(1);
        sharedYes.setOpacity(1);
        entryYes.setOpacity(1);
        descriptionTextField.setOpacity(1);
        typeComboBox.setOpacity(1);
        titleLabel.setOpacity(1);
        slutLabel.setOpacity(1);
        startLabel.setOpacity(1);
        sharedLabel.setOpacity(1);
        typeLabel.setOpacity(1);
        entryLabel.setOpacity(1);
        placeLabel.setOpacity(1);
        descriptionLabel.setOpacity(1);
        pictoView.setOpacity(1);

    }

    public void hide() {
        titleTextField.setOpacity(0);
        startTextField.setOpacity(0);
        endTextField.setOpacity(0);
        startTimeField.setOpacity(0);
        endTimeField.setOpacity(0);
        placeTextField.setOpacity(0);
        sharedYes.setOpacity(0);
        entryYes.setOpacity(0);
        descriptionTextField.setOpacity(0);
        typeComboBox.setOpacity(0);
        titleLabel.setOpacity(0);
        slutLabel.setOpacity(0);
        startLabel.setOpacity(0);
        sharedLabel.setOpacity(0);
        typeLabel.setOpacity(0);
        entryLabel.setOpacity(0);
        placeLabel.setOpacity(0);
        descriptionLabel.setOpacity(0);
        pictoView.setOpacity(0);

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
