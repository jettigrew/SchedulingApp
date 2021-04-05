package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    void addAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void customersSwitchHandler(ActionEvent event) {

    }

    @FXML
    void deleteAppointmentHandler(ActionEvent event) {

    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) {

    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) {

    }
}
