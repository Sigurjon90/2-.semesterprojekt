package UI;

import Domain.User.CareWorker;
import Domain.User.User;
import static UI.Vault.stage;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class FXMLCalenderController implements Initializable {

    

    @FXML
    private AnchorPane parent;
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
    public void planAction(ActionEvent event) throws IOException {
        Parent currentActivity = FXMLLoader.load(getClass().getResource("FXMLActivityEditor.fxml"));
        Scene scene = new Scene(currentActivity);
        stage.setScene(scene);
    }

    @FXML
    public void deleteActivity(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //User is the person logged into the system
        if (Vault.currentLoggedOn instanceof CareWorker) {
            planBtn.setDisable(false);
            editBtn.setDisable(false);
            deleteBtn.setDisable(false);

        }
    }

}
