package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DisplayCustomersController {
    @FXML
    private TableView<?> customersTable;

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
    void addCustomerHandler(ActionEvent event) {

    }

    @FXML
    void appointmentsSwitchHandler(ActionEvent event) {

    }

    @FXML
    void deleteCustomerHandler(ActionEvent event) {

    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) {

    }

    @FXML
    void updateCustomerHandler(ActionEvent event) {

    }
}
