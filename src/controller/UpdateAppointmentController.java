package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    private ComboBox<?> updateContactCombo;

    @FXML
    private ComboBox<?> updateTypeCombo;

    @FXML
    private ComboBox<?> updateCustomer_IDCombo;

    @FXML
    private ComboBox<?> updateUser_IDCombo;

    @FXML
    private DatePicker updateStartDatePicker;

    @FXML
    private DatePicker updateEndDatePicker;

    @FXML
    private ComboBox<?> updateStartHourCombo;

    @FXML
    private ComboBox<?> updateStartMinuteCombo;

    @FXML
    private ComboBox<?> updateStartAMPMCombo;

    @FXML
    private ComboBox<?> updateEndHourCombo;

    @FXML
    private ComboBox<?> updateEndMinuteCombo;

    @FXML
    private ComboBox<?> updateEndAMPMCombo;

    @FXML
    void cancelUpdateAppointmentInfoHandler(ActionEvent event) {

    }

    @FXML
    void submitUpdateAppointmentInfoHandler(ActionEvent event) {

    }
}
