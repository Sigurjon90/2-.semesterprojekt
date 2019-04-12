package UI;

import Domain.CalendarModule.Calendar;
import Domain.User.CareWorker;
import Domain.User.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Vault extends Application {

    public static Stage stage;
    public static User currentLoggedOn = new CareWorker("care", "carer");
    public static Calendar testCalendar = new Calendar();

    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        // Parent root = FXMLLoader.load(getClass().getResource("FXMLCalender.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/UI/CaseModul/FXMLCase.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
