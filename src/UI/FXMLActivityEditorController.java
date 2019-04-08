package UI;

import Domain.User.User;
import static UI.Vault.stage;
import static UI.Vault.testCalendar;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class FXMLActivityEditorController implements Initializable {

    ObservableList<String> typestatus = FXCollections.observableArrayList("Medicin", "udendørsaktivitet", "indendørsaktivitet");
    ObservableList<String> typeComboBoxList = FXCollections.observableArrayList();

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField StartTextField;
    @FXML
    private TextField endTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private RadioButton sharedYes;
    @FXML
    private ToggleGroup fælles;
    @FXML
    private RadioButton sharedNo;
    @FXML
    private RadioButton entryYes;
    @FXML
    private ToggleGroup indlæg;
    @FXML
    private RadioButton entryNo;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private Button saveActivityBtn;
    @FXML
    private Button cancelActivity;

    @FXML
    public void saveActivity(ActionEvent event) throws IOException {
        testCalendar.createActivity(titleTextField.getText(), Vault.currentLoggedOn, placeTextField.getText(), StartTextField.getText(), endTextField.getText(), descriptionTextField.getText(),
            typeComboBox.getValue(), sharedYes.isSelected());
        Parent root = FXMLLoader.load(getClass().getResource("FXMLCalender.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.setItems(typestatus);
    }

}
