package UI;

import Domain.CalendarModule.Activity;
import Domain.CalendarModule.Calendar;
import Domain.User.Resident;
import Persistence.Connector;
import Persistence.DiaryRepository;
import Persistence.UserManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Vault extends Application {

    public static Stage stage;
//    public static User currentLoggedOn = new CareWorker("care", "carer");
    public static Case currentCase;
    public static Resident resident = new Resident();
    public static Activity currentActivity;
    public static boolean newAction;

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    public static void main(String[] args) {
        Connector connection = new Connector();
        connection.setupConnection();

        launch(args);
    }

}
