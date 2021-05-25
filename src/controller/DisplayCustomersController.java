package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import util.AlertGenerator;
import util.SceneSwitch;

import java.io.IOException;

public class DisplayCustomersController {
    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<?, ?> customersCustomer_IDCol;

    @FXML
    private TableColumn<?, ?> customersNameCol;

    @FXML
    private TableColumn<?, ?> customersAddressCol;

    @FXML
    private TableColumn<?, ?> customersRegionCol;

    @FXML
    private TableColumn<?, ?> customersCountryCol;

    @FXML
    private TableColumn<?, ?> customersPostalCodeCol;

    @FXML
    private TableColumn<?, ?> customersPhoneNumberCol;

    @FXML
    void addCustomerHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/AddCustomer.fxml");
    }

    @FXML
    void appointmentsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayAppointments.fxml");
    }

    @FXML
    void deleteCustomerHandler(ActionEvent event) throws IOException {
        //TODO: Add selection condition
        //TODO: Code delete functionality
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            AlertGenerator deletionFailedAlert = new AlertGenerator();
            deletionFailedAlert.createAlert("ERROR", "You must select a customer to delete.");
            return;
        }

        AlertGenerator deletionConfirmationAlert = new AlertGenerator();
        String alertString = "Deleting a customer will cause all associated appointments to be deleted. " +
                "Are you sure you want to delete customer and associated appointments?";
        if (deletionConfirmationAlert.getConfirmationAlert(alertString)) {
            //First delete associated appointments
            //Lambda!
            ObservableList<Appointment> appointments = AppointmentDAO.retrieveAppointmentDeletionInfo(selectedCustomer.getCustomerID());
            appointments.forEach(appointment -> {
                AppointmentDAO.deleteAppointment(appointment.getAppointmentID());
                String deletionString = "Appointment " + appointment.getAppointmentID() + " " + appointment.getAppointmentTitle() +
                        " of type " + appointment.getAppointmentType() + " successfully deleted.";
                AlertGenerator appointmentDeletionAlert = new AlertGenerator();
                appointmentDeletionAlert.createAlert("INFORMATION", deletionString);
            });

            //Finally delete customer
            if (CustomerDAO.deleteCustomer(selectedCustomer.getCustomerID())) {
                SceneSwitch switcher = new SceneSwitch();
                switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
                String deletionString = "Customer " + selectedCustomer.getCustomerID() + " " + selectedCustomer.getCustomerName() +
                        " successfully deleted.";
                AlertGenerator customerDeletionAlert = new AlertGenerator();
                customerDeletionAlert.createAlert("INFORMATION", deletionString);
            }
            else {
                AlertGenerator deletionFailedAlert = new AlertGenerator();
                deletionFailedAlert.createAlert("ERROR", "Customer was not deleted.");
            }
        }
    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayReports.fxml");
    }

    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        if (!customersTable.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.sendCustomer(customersTable.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            AlertGenerator contactIDAlert = new AlertGenerator();
            contactIDAlert.createAlert("ERROR", "You must select a customer to update.");
        }
    }

    @FXML
    public void initialize() {
        customersTable.setItems(CustomerDAO.retrieveAllCustomers());
        customersCustomer_IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customersNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customersAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customersRegionCol.setCellValueFactory(new PropertyValueFactory<>("associatedDivisionName"));
        customersCountryCol.setCellValueFactory(new PropertyValueFactory<>("associatedCountryName"));
        customersPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customersPhoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }
}
