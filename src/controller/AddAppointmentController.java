package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AddAppointmentController {
    @FXML
    public ComboBox<String> addStartMinCombo;

    @FXML
    public ComboBox<String> addEndMinCombo;

    @FXML
    private ComboBox<Contact> addContactCombo;

    @FXML
    private TextField addAppointment_IDText;

    @FXML
    private TextField addTitleText;

    @FXML
    private TextField addDescriptionText;

    @FXML
    private TextField addLocationText;

    @FXML
    private TextField addTypeText;

    @FXML
    private DatePicker addMeetingDatePicker;

    @FXML
    private DatePicker addEndDatePicker;

    @FXML
    private ComboBox<Integer> addCustomer_IDCombo;

    @FXML
    private ComboBox<Integer> addUser_IDCombo;

    @FXML
    private ComboBox<String> addStartHourCombo;

    @FXML
    private ComboBox<String> addStartAMPMCombo;

    @FXML
    private ComboBox<String> addEndHourCombo;

    @FXML
    private ComboBox<String> addEndAMPMCombo;

    @FXML
    void cancelAddAppointmentInfoHandler(ActionEvent event) throws IOException {
        AlertGenerator cancelAddAlert = new AlertGenerator();
        if (cancelAddAlert.getConfirmationAlert("Are you sure you want to cancel? Changes will not be saved.")) {
            SceneSwitch switcher = new SceneSwitch();
            switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
        }
    }

    @FXML
    void submitAddAppointmentInfoHandler(ActionEvent event) throws IOException {
        Appointment appointment = new Appointment();

        if (addTitleText.getText().trim().isEmpty() || addTitleText.getText().trim().equals("Title")) {
            AlertGenerator titleAlert = new AlertGenerator();
            titleAlert.createAlert("ERROR", "Appointment must have title.");
            return;
        }
        else {
            appointment.setAppointmentTitle(addTitleText.getText().trim());
        }

        if (addDescriptionText.getText().trim().isEmpty() || addDescriptionText.getText().trim().equals("Description")) {
            AlertGenerator descriptionAlert = new AlertGenerator();
            descriptionAlert.createAlert("ERROR", "Appointment must have description.");
            return;
        }
        else {
            appointment.setAppointmentDescription(addDescriptionText.getText().trim());
        }

        if (addLocationText.getText().trim().isEmpty() || addLocationText.getText().trim().equals("Location")) {
            AlertGenerator locationAlert = new AlertGenerator();
            locationAlert.createAlert("ERROR", "Appointment must have location.");
            return;
        }
        else {
            appointment.setAppointmentLocation(addLocationText.getText().trim());
        }

        if (addTypeText.getText().trim().isEmpty() || addTypeText.getText().trim().equals("Type")) {
            AlertGenerator typeAlert = new AlertGenerator();
            typeAlert.createAlert("ERROR", "Appointment must have type.");
            return;
        }
        else {
            appointment.setAppointmentType(addTypeText.getText().trim());
        }

        if (addContactCombo.getValue() == null) {
            AlertGenerator contactAlert = new AlertGenerator();
            contactAlert.createAlert("ERROR", "Appointment must have associated contact.");
            return;
        }
        else {
            Contact contact = addContactCombo.getValue();
            appointment.setContactID(contact.getContactID());
        }

        if (addCustomer_IDCombo.getValue() == null) {
            AlertGenerator customerIDAlert = new AlertGenerator();
            customerIDAlert.createAlert("ERROR", "Appointment must have associated Customer_ID.");
            return;
        }
        else {
            appointment.setCustomerID(addCustomer_IDCombo.getValue());
        }

        if (addUser_IDCombo.getValue() == null) {
            AlertGenerator userIDAlert = new AlertGenerator();
            userIDAlert.createAlert("ERROR", "Appointment must have associated User_ID.");
        }
        else {
            appointment.setUserID(addUser_IDCombo.getValue());
        }

        if (addMeetingDatePicker.getValue() == null || addMeetingDatePicker.getValue() == null || addStartHourCombo.getValue() == null ||
                addStartMinCombo.getValue() == null || addStartAMPMCombo.getValue() == null || addEndHourCombo.getValue() == null ||
                addEndMinCombo.getValue() == null || addEndAMPMCombo.getValue() == null) {
            AlertGenerator dateTimeAlert = new AlertGenerator();
            dateTimeAlert.createAlert("ERROR", "Appointment must have start date/time and end date/time.");
        }
        else {
            //TODO: add function that returns 24-hour hours as ints, then use this for time checks
            LocalDate endDate;
            LocalDate endValue = addEndDatePicker.getValue();

            LocalDate startDate = addMeetingDatePicker.getValue();
            int startHour = TimeFormatter.getCorrectHour(addStartHourCombo.getValue(), addStartAMPMCombo.getValue());
            int startMin = Integer.parseInt(addStartMinCombo.getValue());
            LocalDateTime startDateTime = startDate.atTime(startHour, startMin);

            if ((addStartAMPMCombo.getValue().equals("PM") && addEndAMPMCombo.getValue().equals("AM"))) {
                endDate = addMeetingDatePicker.getValue().plusDays(1);
            }
            else {
                endDate = addMeetingDatePicker.getValue();
            }
            int endHour = TimeFormatter.getCorrectHour(addEndHourCombo.getValue(), addEndAMPMCombo.getValue());
            int endMin = Integer.parseInt(addEndMinCombo.getValue());
            LocalDateTime endDateTime = endDate.atTime(endHour, endMin);

            //TODO: Make sure this actually works at all?? Both the time conversion and the overlap check
            if ((startDateTime.compareTo(LocalDateTime.now()) < 1 || endDateTime.compareTo(startDateTime) < 1)) {
                AlertGenerator dateTimeAlert2 = new AlertGenerator();
                dateTimeAlert2.createAlert("ERROR", "Appointment must start in future and end after start.");
                return;
            } else if (!TimeConverter.isWithinBusinessHours(startDateTime, endDateTime)) {
                AlertGenerator dateTimeAlert3 = new AlertGenerator();
                dateTimeAlert3.createAlert("ERROR", "Appointment must occur on a weekday between 8 AM and 10 PM Eastern Time.");
                return;
            } else if (AppointmentDAO.isOverlapping(addCustomer_IDCombo.getValue(), 0,
                    TimeConverter.localToDatabase(startDateTime), TimeConverter.localToDatabase(endDateTime))) {
                AlertGenerator dateTimeAlert4 = new AlertGenerator();
                dateTimeAlert4.createAlert("ERROR", "Appointment must not overlap with customer's other appointments.");
                return;
            } else {
                appointment.setAppointmentStart(TimeConverter.localToDatabase(startDateTime));
                appointment.setAppointmentEnd(TimeConverter.localToDatabase(endDateTime));
            }

            AlertGenerator newAppointmentAlert = new AlertGenerator();
            if (newAppointmentAlert.getConfirmationAlert("Save new appointment and return to Appointments screen?")) {
                if (AppointmentDAO.createAppointment(appointment)){
                    SceneSwitch switcher = new SceneSwitch();
                    switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
                }
                else {
                    AlertGenerator additionFailedAlert = new AlertGenerator();
                    additionFailedAlert.createAlert("ERROR", "Appointment was not added.");
                    return;
                }
            }
        }
    }

    @FXML
    public void initialize() {
        addAppointment_IDText.setDisable(true);
        addMeetingDatePicker.getEditor().setDisable(true);
        addEndDatePicker.getEditor().setDisable(true);
        addAppointment_IDText.setText("Auto Gen - Disabled");
        addTitleText.setText("Title");
        addDescriptionText.setText("Description");
        addLocationText.setText("Location");
        addTypeText.setText("Type");
        addContactCombo.setItems(ContactDAO.retrieveAllContacts());

        //Lambda
        ObservableList<String> hourList = FXCollections.observableArrayList();
        IntStream.range(1, 13).forEach(num -> hourList.add(String.format("%02d", num)));
        addStartHourCombo.setItems(hourList);
        addEndHourCombo.setItems(hourList);

        ObservableList<String> minuteList = FXCollections.observableArrayList();
        IntStream.range(0, 60).forEach(num -> minuteList.add(String.format("%02d", num)));
        addStartMinCombo.setItems(minuteList);
        addEndMinCombo.setItems(minuteList);

        ObservableList<String> timeOfDay = FXCollections.observableArrayList();
        timeOfDay.add("AM");
        timeOfDay.add("PM");
        addStartAMPMCombo.setItems(timeOfDay);
        addEndAMPMCombo.setItems(timeOfDay);

        //Another lambda, condenses for loop
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
        CustomerDAO.retrieveAllCustomers().forEach(customer -> customerIDList.add(customer.getCustomerID()));
        addCustomer_IDCombo.setItems(customerIDList);

        //Same lambda as above
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
        UserDAO.retrieveAllUsers().forEach(user -> userIDList.add(user.getUserID()));
        addUser_IDCombo.setItems(userIDList);

    }
}
