package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Appointment;
import model.Contact;
import model.User;
import util.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

public class DisplayAppointmentsController {
    @FXML
    private TableView<Appointment> weekAppointmentsTable;

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
    private TableView<Appointment> monthAppointmentsTable;

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
    private TableView<Appointment> allAppointmentsTable;

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
    void deleteAppointmentHandler(ActionEvent event) throws IOException {
        Appointment selectedAppointment;

        if (!allAppointmentsTable.getSelectionModel().isEmpty()) {
            selectedAppointment = allAppointmentsTable.getSelectionModel().getSelectedItem();
        }
        else if (!monthAppointmentsTable.getSelectionModel().isEmpty()) {
            selectedAppointment = monthAppointmentsTable.getSelectionModel().getSelectedItem();
        }
        else if (!weekAppointmentsTable.getSelectionModel().isEmpty()) {
            selectedAppointment = weekAppointmentsTable.getSelectionModel().getSelectedItem();
        }
        else {
            AlertGenerator deletionFailedAlert = new AlertGenerator();
            deletionFailedAlert.createAlert("ERROR", "You must select an appointment to delete.");
            return;
        }

        AlertGenerator deletionConfirmationAlert = new AlertGenerator();
        if (deletionConfirmationAlert.getConfirmationAlert("Are you sure you want to delete appointment?")) {
            if (AppointmentDAO.deleteAppointment(selectedAppointment.getAppointmentID())) {
                SceneSwitch switcher = new SceneSwitch();
                switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
                String deletionString = "Appointment " + selectedAppointment.getAppointmentID() + " " +
                        selectedAppointment.getAppointmentTitle() + " of type " + selectedAppointment.getAppointmentType() +
                        " successfully deleted.";
                AlertGenerator appointmentDeletionAlert = new AlertGenerator();
                appointmentDeletionAlert.createAlert("INFORMATION", deletionString);
            } else {
                AlertGenerator deletionFailedAlert = new AlertGenerator();
                deletionFailedAlert.createAlert("INFORMATION", "Appointment was not deleted.");
            }
        }
    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayReports.fxml");
    }

    @FXML
    void updateAppointmentHandler(ActionEvent event) throws IOException, ParseException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
        loader.load();

        if (!allAppointmentsTable.getSelectionModel().isEmpty()) {
            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.sendAppointment(allAppointmentsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if (!monthAppointmentsTable.getSelectionModel().isEmpty()) {
            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.sendAppointment(monthAppointmentsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else if (!weekAppointmentsTable.getSelectionModel().isEmpty()) {
            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.sendAppointment(weekAppointmentsTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            AlertGenerator updateFailedAlert = new AlertGenerator();
            updateFailedAlert.createAlert("ERROR", "You must select an appointment to update.");
        }
    }

    @FXML
    public void initialize() throws ParseException {
        weekAppointmentsTable.setItems(AppointmentDAO.retrieveAllAppointments("weekly"));
        weekAppointmentsAppointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        weekAppointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        weekAppointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        weekAppointmentsLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        weekAppointmentsContactCol.setCellValueFactory(new PropertyValueFactory<>("associatedContactName"));
        weekAppointmentsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        weekAppointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        weekAppointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        weekAppointmentsCustomer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        monthAppointmentsTable.setItems(AppointmentDAO.retrieveAllAppointments("monthly"));
        monthAppointmentsAppointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        monthAppointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        monthAppointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        monthAppointmentsLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        monthAppointmentsContactCol.setCellValueFactory(new PropertyValueFactory<>("associatedContactName"));
        monthAppointmentsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        monthAppointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        monthAppointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        monthAppointmentsCustomer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        allAppointmentsTable.setItems(AppointmentDAO.retrieveAllAppointments("all"));
        allAppointmentsAppointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        allAppointmentsTitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
        allAppointmentsDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
        allAppointmentsLocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
        allAppointmentsContactCol.setCellValueFactory(new PropertyValueFactory<>("associatedContactName"));
        allAppointmentsTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        allAppointmentsStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        allAppointmentsEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        allAppointmentsCustomer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));


    }
}
