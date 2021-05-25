package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import util.AlertGenerator;
import util.SceneSwitch;
import util.TimeConverter;
import util.TimeFormatter;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.stream.IntStream;

public class UpdateAppointmentController {
    @FXML
    private TextField updateAppointment_IDText;

    @FXML
    private TextField updateTitleText;

    @FXML
    private TextField updateDescriptionText;

    @FXML
    private TextField updateLocationText;

    @FXML
    private TextField updateTypeText;

    @FXML
    private ComboBox<Contact> updateContactCombo;

    @FXML
    private ComboBox<Integer> updateCustomer_IDCombo;

    @FXML
    private ComboBox<Integer> updateUser_IDCombo;

    @FXML
    private DatePicker updateMeetingDatePicker;

    @FXML
    private DatePicker updateEndDatePicker;

    @FXML
    private ComboBox<String> updateStartHourCombo;

    @FXML
    private ComboBox<String> updateStartMinuteCombo;

    @FXML
    private ComboBox<String> updateStartAMPMCombo;

    @FXML
    private ComboBox<String> updateEndHourCombo;

    @FXML
    private ComboBox<String> updateEndMinuteCombo;

    @FXML
    private ComboBox<String> updateEndAMPMCombo;

    @FXML
    void cancelUpdateAppointmentInfoHandler(ActionEvent event) throws IOException {
        AlertGenerator cancelUpdateAlert = new AlertGenerator();
        if (cancelUpdateAlert.getConfirmationAlert("Are you sure you want to cancel? Changes will not be saved.")) {
            SceneSwitch switcher = new SceneSwitch();
            switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
        }
    }

    @FXML
    void submitUpdateAppointmentInfoHandler(ActionEvent event) throws IOException {
        Appointment appointment = new Appointment();

        if (updateTitleText.getText().trim().isEmpty()) {
            AlertGenerator titleAlert = new AlertGenerator();
            titleAlert.createAlert("ERROR", "Appointment must have title.");
            return;
        }
        else {
            appointment.setAppointmentTitle(updateTitleText.getText().trim());
        }

        if (updateDescriptionText.getText().trim().isEmpty()) {
            AlertGenerator descriptionAlert = new AlertGenerator();
            descriptionAlert.createAlert("ERROR", "Appointment must have description.");
            return;
        }
        else {
            appointment.setAppointmentDescription(updateDescriptionText.getText().trim());
        }

        if (updateLocationText.getText().trim().isEmpty()) {
            AlertGenerator locationAlert = new AlertGenerator();
            locationAlert.createAlert("ERROR", "Appointment must have location.");
            return;
        }
        else {
            appointment.setAppointmentLocation(updateLocationText.getText().trim());
        }

        if (updateTypeText.getText().trim().isEmpty()) {
            AlertGenerator typeAlert = new AlertGenerator();
            typeAlert.createAlert("ERROR", "Appointment must have type.");
            return;
        }
        else {
            appointment.setAppointmentType(updateTypeText.getText().trim());
        }

        if (updateContactCombo.getValue() == null) {
            AlertGenerator contactAlert = new AlertGenerator();
            contactAlert.createAlert("ERROR", "Appointment must have associated contact.");
            return;
        }
        else {
            Contact contact = updateContactCombo.getValue();
            appointment.setContactID(contact.getContactID());
        }

        if (updateCustomer_IDCombo.getValue() == null) {
            AlertGenerator customerIDAlert = new AlertGenerator();
            customerIDAlert.createAlert("ERROR", "Appointment must have associated Customer_ID.");
            return;
        }
        else {
            appointment.setCustomerID(updateCustomer_IDCombo.getValue());
        }

        if (updateUser_IDCombo.getValue() == null) {
            AlertGenerator userIDAlert = new AlertGenerator();
            userIDAlert.createAlert("ERROR", "Appointment must have associated User_ID.");
        }
        else {
            appointment.setUserID(updateUser_IDCombo.getValue());
        }

        if (updateMeetingDatePicker.getValue() == null || updateMeetingDatePicker.getValue() == null || updateStartHourCombo.getValue() == null ||
                updateStartMinuteCombo.getValue() == null || updateStartAMPMCombo.getValue() == null || updateEndHourCombo.getValue() == null ||
                updateEndMinuteCombo.getValue() == null || updateEndAMPMCombo.getValue() == null) {
            AlertGenerator dateTimeAlert = new AlertGenerator();
            dateTimeAlert.createAlert("ERROR", "Appointment must have start date/time and end date/time.");
        }
        else {
            //TODO: add function that returns 24-hour hours as ints, then use this for time checks
            LocalDate endDate;
            LocalDate endValue = updateEndDatePicker.getValue();

            LocalDate startDate = updateMeetingDatePicker.getValue();
            int startHour = TimeFormatter.getCorrectHour(updateStartHourCombo.getValue(), updateStartAMPMCombo.getValue());
            int startMin = Integer.parseInt(updateStartMinuteCombo.getValue());
            LocalDateTime startDateTime = startDate.atTime(startHour, startMin);

            if ((updateStartAMPMCombo.getValue().equals("PM") && updateEndAMPMCombo.getValue().equals("AM"))) {
                endDate = updateMeetingDatePicker.getValue().plusDays(1);
            }
            else {
                endDate = updateMeetingDatePicker.getValue();
            }
            int endHour = TimeFormatter.getCorrectHour(updateEndHourCombo.getValue(), updateEndAMPMCombo.getValue());
            int endMin = Integer.parseInt(updateEndMinuteCombo.getValue());
            LocalDateTime endDateTime = endDate.atTime(endHour, endMin);

            //TODO: Make sure this actually works at all?? Both the time conversion and the overlap check
            if ((startDateTime.compareTo((LocalDateTime.now())) < 1 || endDateTime.compareTo(startDateTime) < 1)) {
                AlertGenerator dateTimeAlert2 = new AlertGenerator();
                dateTimeAlert2.createAlert("ERROR", "Appointment must start in future and end after start.");
                return;
            } else if (!TimeConverter.isWithinBusinessHours(startDateTime, endDateTime)) {
                AlertGenerator dateTimeAlert3 = new AlertGenerator();
                dateTimeAlert3.createAlert("ERROR", "Appointment must occur on a weekday between 8 AM and 10 PM Eastern Time.");
                return;
            } else if (AppointmentDAO.isOverlapping(updateCustomer_IDCombo.getValue(), Integer.parseInt(updateAppointment_IDText.getText()),
                    TimeConverter.localToDatabase(startDateTime), TimeConverter.localToDatabase(endDateTime))) {
                AlertGenerator dateTimeAlert4 = new AlertGenerator();
                dateTimeAlert4.createAlert("ERROR", "Appointment must not overlap with customer's other appointments.");
                return;
            } else {
                appointment.setAppointmentStart(TimeConverter.localToDatabase(startDateTime));
                appointment.setAppointmentEnd(TimeConverter.localToDatabase(endDateTime));
            }

            AlertGenerator updateAppointmentAlert = new AlertGenerator();
            if (updateAppointmentAlert.getConfirmationAlert("Update appointment and return to Appointments screen?")) {
                if (AppointmentDAO.updateAppointment(Integer.parseInt(updateAppointment_IDText.getText()), appointment.getAppointmentTitle(),
                        appointment.getAppointmentDescription(), appointment.getAppointmentLocation(), appointment.getAppointmentType(),
                        appointment.getAppointmentStart(), appointment.getAppointmentEnd(), appointment.getCustomerID(),
                        appointment.getUserID(), appointment.getContactID())) {
                    SceneSwitch switcher = new SceneSwitch();
                    switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
                }
                else {
                    AlertGenerator additionFailedAlert = new AlertGenerator();
                    additionFailedAlert.createAlert("ERROR", "Appointment was not updated.");
                    return;
                }
            }
        }
    }

    public void sendAppointment(Appointment appointment) throws ParseException {
        updateAppointment_IDText.setText(String.valueOf(appointment.getAppointmentID()));
        updateTitleText.setText(appointment.getAppointmentTitle());
        updateDescriptionText.setText(appointment.getAppointmentDescription());
        updateLocationText.setText(appointment.getAppointmentLocation());
        updateTypeText.setText(appointment.getAppointmentType());
        updateContactCombo.setValue(ContactDAO.retrieveContact(appointment.getContactID()));
        updateMeetingDatePicker.setValue(LocalDate.parse(appointment.getAppointmentStart().substring(0, 10)));
        updateEndDatePicker.setValue(LocalDate.parse(appointment.getAppointmentEnd().substring(0, 10)));
        updateStartHourCombo.setValue(appointment.getAppointmentStart().substring(11, 13));
        updateStartMinuteCombo.setValue(appointment.getAppointmentStart().substring(14, 16));
        updateEndHourCombo.setValue(appointment.getAppointmentEnd().substring(11, 13));
        updateEndMinuteCombo.setValue(appointment.getAppointmentEnd().substring(14, 16));
        updateStartAMPMCombo.setValue(appointment.getAppointmentStart().substring(16));
        updateEndAMPMCombo.setValue(appointment.getAppointmentEnd().substring(16));
        updateCustomer_IDCombo.setValue(appointment.getCustomerID());
        updateUser_IDCombo.setValue(appointment.getUserID());
    }

    @FXML
    public void initialize() {
        updateAppointment_IDText.setDisable(true);
        updateMeetingDatePicker.getEditor().setDisable(true);
        updateEndDatePicker.getEditor().setDisable(true);
        updateContactCombo.setItems(ContactDAO.retrieveAllContacts());

        //Lambda
        ObservableList<String> hourList = FXCollections.observableArrayList();
        IntStream.range(1, 13).forEach(num -> hourList.add(String.format("%02d", num)));
        updateStartHourCombo.setItems(hourList);
        updateEndHourCombo.setItems(hourList);

        ObservableList<String> minuteList = FXCollections.observableArrayList();
        IntStream.range(0, 60).forEach(num -> minuteList.add(String.format("%02d", num)));
        updateStartMinuteCombo.setItems(minuteList);
        updateEndMinuteCombo.setItems(minuteList);

        ObservableList<String> timeOfDay = FXCollections.observableArrayList();
        timeOfDay.add("AM");
        timeOfDay.add("PM");
        updateStartAMPMCombo.setItems(timeOfDay);
        updateEndAMPMCombo.setItems(timeOfDay);

        //Another lambda, condenses for loop
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
        CustomerDAO.retrieveAllCustomers().forEach(customer -> customerIDList.add(customer.getCustomerID()));
        updateCustomer_IDCombo.setItems(customerIDList);

        //Same lambda as above
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
        UserDAO.retrieveAllUsers().forEach(user -> userIDList.add(user.getUserID()));
        updateUser_IDCombo.setItems(userIDList);
    }
}
