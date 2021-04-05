package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.DatabaseConnection;

public class Main extends Application {

    public static void main(String[] args) {
        DatabaseConnection.connect();
        launch(args);
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
