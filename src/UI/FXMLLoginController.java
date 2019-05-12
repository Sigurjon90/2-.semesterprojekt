/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Domain.User.CareWorker;
import Domain.User.Resident;
import Domain.User.SocialWorker;
import Domain.User.User;
import Persistence.UserManager;
import static UI.Vault.stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author jens
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Label lb_username;
    @FXML
    private JFXTextField text_username;
    @FXML
    private JFXPasswordField text_password;
    @FXML
    private Label lb_password;
    @FXML
    private JFXButton btn_login;
    @FXML
    private JFXButton btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loginHandler(ActionEvent event) throws IOException, SQLException {

        if (UserManager.login(text_username.getText(), text_password.getText())) {
            System.out.println(UserManager.getCurrentUser().toString());
            Parent root = FXMLLoader.load(getClass().getResource("FXMLVault.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }

    }

    @FXML
    void cancelHandler(ActionEvent event) {
        System.exit(1);
    }

}
