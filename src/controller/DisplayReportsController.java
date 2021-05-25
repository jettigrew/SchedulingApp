package controller;

import DAO.AppointmentDAO;
import DAO.ContactDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import util.AlertGenerator;
import util.SceneSwitch;

import java.io.IOException;

public class DisplayReportsController {
    @FXML
    private TabPane reportsTabPane;

    @FXML
    private TextArea report1TextArea;

    @FXML
    private ComboBox<Integer> report2ContactCombo;

    @FXML
    private TableView<Appointment> report2Table;

    @FXML
    private TableColumn<?, ?> report2Appointment_IDCol;

    @FXML
    private TableColumn<?, ?> report2TitleCol;

    @FXML
    private TableColumn<?, ?> report2DescriptionCol;

    @FXML
    private TableColumn<?, ?> report2LocationCol;

    @FXML
    private TableColumn<?, ?> report2TypeCol;

    @FXML
    private TableColumn<?, ?> report2ContactCol;

    @FXML
    private TableColumn<?, ?> report2StartCol;

    @FXML
    private TableColumn<?, ?> report2EndCol;

    @FXML
    private TableColumn<?, ?> report2Customer_IDCol;

    @FXML
    private TextArea report3TextArea;

    @FXML
    private Tab reportTab1;

    @FXML
    private Tab reportTab2;

    @FXML
    private Tab reportTab3;

    @FXML
    void appointmentsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
    }

    @FXML
    void customersSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
    }

    @FXML
    void runReportHandler(ActionEvent event) {
        if (reportsTabPane.getSelectionModel().getSelectedItem() == reportTab1) {
            ObservableList<String> typeCountList = AppointmentDAO.retrieveTypeCountByMonth();
            report1TextArea.setText("");

            //lambda!
            typeCountList.forEach(string -> report1TextArea.setText(report1TextArea.getText() + string + "\n"));
        }
        if (reportsTabPane.getSelectionModel().getSelectedItem() == reportTab2) {
            if (!(report2ContactCombo.getValue() == null)) {
                report2Table.setItems(AppointmentDAO.retrieveAllAppointmentsByContact(report2ContactCombo.getValue()));
                report2Appointment_IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
                report2TitleCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
                report2DescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDescription"));
                report2LocationCol.setCellValueFactory(new PropertyValueFactory<>("appointmentLocation"));
                report2TypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
                report2ContactCol.setCellValueFactory(new PropertyValueFactory<>("associatedContactName"));
                report2StartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
                report2EndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
                report2Customer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            }
            else {
                AlertGenerator contactIDAlert = new AlertGenerator();
                contactIDAlert.createAlert("ERROR", "Contact ID required.");
            }
        }
        if (reportsTabPane.getSelectionModel().getSelectedItem() == reportTab3) {
            ObservableList<String> customerCountList = CustomerDAO.retrieveCustomerCountByCountry();
            report3TextArea.setText("");

            //lambda!
            customerCountList.forEach(string -> report3TextArea.setText(report3TextArea.getText() + string + "\n"));
        }
    }

    @FXML
    public void initialize() {
        report1TextArea.setDisable(true);
        report3TextArea.setDisable(true);

        ObservableList<Integer> contactIDList = FXCollections.observableArrayList();
        ContactDAO.retrieveAllContacts().forEach(contact -> contactIDList.add(contact.getContactID()));
        report2ContactCombo.setItems(contactIDList);
    }
}
