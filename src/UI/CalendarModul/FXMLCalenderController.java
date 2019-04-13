package UI.CalendarModul;

import Domain.CalendarModule.Activity;
import Domain.User.CareWorker;
import Domain.User.User;
import UI.Vault;
import static UI.Vault.stage;
import static UI.Vault.testCalendar;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLCalenderController implements Initializable {

    private ObservableList<Activity> obList;
    private ListProperty<Activity> listProperty = new SimpleListProperty<>();

    @FXML
    private AnchorPane parent;
    @FXML
    private Accordion accordionPane;
    @FXML
    private Button planBtn;
    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private Label titleLabel;
    @FXML
    private Label startLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private ComboBox<?> sharedComboBox;
    @FXML
    private Label endLabel;
    @FXML
    private Label userLabel;
    @FXML
    private ListView<Activity> mondayList, tuesdayList, wednesdayList, thursdayList, fridayList, saturdayList, sundayList;
    @FXML
    private JFXDatePicker endTextField;
    @FXML
    private JFXDatePicker StartTextField;

    @FXML
    public void planAction(ActionEvent event) throws IOException {
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
            testCalendar.getCalender().remove(Vault.currentActivity.getId());
        }
        Vault.currentActivity = null;
        deleteBtn.setDisable(true);
        obList = FXCollections.observableArrayList(new ArrayList<>());
        mondayList.itemsProperty().bind(listProperty);
        listProperty.set(obList);
        mondayList.refresh();

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
        switch (day) {
            case "Monday":
                mondayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 1) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;
            case "Tuesday":
                tuesdayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 2) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;

            case "Wednesday":
                wednesdayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 3) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;

            case "Thursday":
                thursdayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 4) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;

            case "Friday":
                fridayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 5) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;

            case "Saturday":
                saturdayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 6) {
                        obList.add(testCalendar.getActivity(i));
                    }
                }
                break;

            case "Sunday":
                sundayList.itemsProperty().bind(listProperty);
                listProperty.set(obList);
                for (int i = 1; i <= testCalendar.getCalender().size(); i++) {
                    if (testCalendar.getActivity(i).getDay() == 7) {
                        obList.add(testCalendar.getActivity(i));
                    }
                    break;
                }
        }
    }

    @FXML
    public void openActivity(MouseEvent event) throws IOException {
        ListView myList;
        Activity myActivity;

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                myList = (ListView) event.getSource();
                myActivity = (Activity) myList.getSelectionModel().getSelectedItem();
                Parent currentParent;
                Vault.currentActivity = myActivity;
                try {
                    currentParent = FXMLLoader.load(getClass().getResource("FXMLActivityEditor.fxml"));
                    Scene scene = new Scene(currentParent);
                    stage.setScene(scene);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLCalenderController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (event.getClickCount() == 1) {
                myList = (ListView) event.getSource();
                myActivity = (Activity) myList.getSelectionModel().getSelectedItem();
                Vault.currentActivity = myActivity;
                if (myList.getSelectionModel().getSelectedItem() != null) {
                    deleteBtn.setDisable(false);
                }

            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

        //User is the person logged into the system
//        if (Vault.currentLoggedOn instanceof CareWorker) {
        planBtn.setDisable(false);
        deleteBtn.setDisable(true);
    }
}
