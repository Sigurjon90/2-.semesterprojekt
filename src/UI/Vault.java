package UI;

import Domain.CalendarModule.Calendar;
import Domain.User.CareWorker;
import Domain.User.Resident;
import Domain.User.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Vault extends Application {

    public static Stage stage;
    public static User currentLoggedOn = new CareWorker("care", "carer");
    public static Calendar testCalendar = new Calendar();
    public static Resident resident = new Resident();

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
