package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddAppointmentController {
    @FXML
    private ComboBox<?> addContactCombo;

    @FXML
    private TextField addAppointment_IDText;

    @FXML
    private TextField addTitleText;

    @FXML
    private TextField addDescriptionText;

    @FXML
    private TextField addLocationText;

    @FXML
    private DatePicker addStartDatePicker;

    @FXML
    private DatePicker addEndDatePicker;

    @FXML
    private ComboBox<?> addCustomer_IDCombo;

    @FXML
    private ComboBox<?> addUser_IDCombo;

    @FXML
    private ComboBox<?> addTypeCombo;

    @FXML
    private ComboBox<?> addStartHourCombo;

    @FXML
    private ComboBox<?> addStartMinuteCombo;

    @FXML
    private ComboBox<?> addStartAMPMCombo;

    @FXML
    private ComboBox<?> addEndHourCombo;

    @FXML
    private ComboBox<?> addEndMinuteCombo;

    @FXML
    private ComboBox<?> addEndAMPMCombo;

    @FXML
    void cancelAddAppointmentInfoHandler(ActionEvent event) {

    }

    @FXML
    void submitAddAppointmentInfoHandler(ActionEvent event) {

    }
}
