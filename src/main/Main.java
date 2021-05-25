package main;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Appointment;
import util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;


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
