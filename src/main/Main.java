package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DatabaseConnection;
import util.TimeConverter;

import java.sql.Date;
import java.text.DateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Main extends Application {

    public static void main(String[] args) {
        DatabaseConnection.connect();
        launch(args);

        System.out.println(TimeConverter.databaseToLocal("2021-05-05 22:38:35"));

        DatabaseConnection.disconnect();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Appointments");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
