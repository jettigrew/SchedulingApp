package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import util.CurrentUser;
import util.SceneSwitch;

import java.io.IOException;

public class DisplayAppointmentsController {
    @FXML
    private TableView<?> weekAppointmentsTable;

    @FXML
    private TableColumn<?, ?> weekAppointmentsAppointment_IDCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsTitleCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsDescriptionCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsLocationCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsContactCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsTypeCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsStartCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsEndCol;

    @FXML
    private TableColumn<?, ?> weekAppointmentsCustomer_IDCol;

    @FXML
    private TableView<?> monthAppointmentsTable;

    @FXML
    private TableColumn<?, ?> monthAppointmentsAppointment_IDCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsTitleCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsDescriptionCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsLocationCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsContactCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsTypeCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsStartCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsEndCol;

    @FXML
    private TableColumn<?, ?> monthAppointmentsCustomer_IDCol;

    @FXML
    private TableView<?> allAppointmentsTable;

    @FXML
    private TableColumn<?, ?> allAppointmentsAppointment_IDCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsTitleCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsDescriptionCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsLocationCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsContactCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsTypeCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsStartCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsEndCol;

    @FXML
    private TableColumn<?, ?> allAppointmentsCustomer_IDCol;

    @FXML
    void addAppointmentHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/AddAppointment.fxml");
    }

    @FXML
    void customersSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
    }

    @FXML
    void deleteAppointmentHandler(ActionEvent event) {
        //TODO: Add selection condition
        //TODO: Code delete functionality
    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayReports.fxml");
    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) throws IOException {
        //TODO: Add selection condition
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/UpdateAppointment.fxml");
    }
}
