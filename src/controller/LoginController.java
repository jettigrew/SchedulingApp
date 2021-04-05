package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.SceneSwitch;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Label regionLabel;

    @FXML
    void loginHandler(ActionEvent event) throws IOException {
        //TODO: ADD conditional for user authentication
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
    }
}
