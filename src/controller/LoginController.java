package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.User;
import util.AlertGenerator;
import util.CurrentUser;
import util.SceneSwitch;
import util.TimeConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button loginLabel;

    @FXML
    private Label regionLabel;

    ResourceBundle resourceBundle = ResourceBundle.getBundle("util.Translation", Locale.FRENCH);

    @FXML
    void loginHandler(ActionEvent event) throws IOException, ParseException {
        String logFile = "login_activity.txt", logFileString;
        FileWriter fileWriter = new FileWriter(logFile, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        if (!(UserDAO.loginUser(usernameText.getText(), passwordText.getText()) == null)) {
            User newUser = UserDAO.loginUser(usernameText.getText(), passwordText.getText());
            assert newUser != null;
            new CurrentUser(newUser.getUserID(), newUser.getUserName());

            logFileString = "SUCCESSFUL login attempt by " + newUser.getUserName() + " " +
                    TimeConverter.localToDatabase(LocalDateTime.now()) + " UTC";
            printWriter.println(logFileString);

            SceneSwitch switcher = new SceneSwitch();
            switcher.switchScenes(event, "/view/DisplayAppointments.fxml");

            TimeConverter.checkIfAppointmentSoon(newUser.getUserID());
        }
        else {
            if (Locale.getDefault().getLanguage().equals("fr")){
                AlertGenerator loginFailed = new AlertGenerator();
                loginFailed.createAlert("ERROR", resourceBundle.getString("LoginError"));
            }
            else {
                AlertGenerator loginFailed = new AlertGenerator();
                loginFailed.createAlert("ERROR", "Username or password is incorrect.");
            }

            logFileString = "UNSUCCESSFUL login attempt by " + usernameText.getText() + " " +
                    TimeConverter.localToDatabase(LocalDateTime.now()) + " UTC";
            printWriter.println(logFileString);
        }
        printWriter.close();
    }

    @FXML
    void initialize() {
        regionLabel.setText(String.valueOf(ZoneId.systemDefault()));

        if (Locale.getDefault().getLanguage().equals("fr")) {
            titleLabel.setText(resourceBundle.getString("AppointmentScheduler"));
            usernameLabel.setText(resourceBundle.getString("Username"));
            passwordLabel.setText(resourceBundle.getString("Password"));
            loginLabel.setText(resourceBundle.getString("Login"));
        }
    }
}
