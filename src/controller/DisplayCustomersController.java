package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import util.SceneSwitch;

import java.io.IOException;

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
    void deleteCustomerHandler(ActionEvent event) {
        //TODO: Add selection condition
        //TODO: Code delete functionality
    }

    @FXML
    void reportsSwitchHandler(ActionEvent event) throws IOException {
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayReports.fxml");
    }

    @FXML
    void updateCustomerHandler(ActionEvent event) throws IOException {
        //TODO: Add selection condition
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/UpdateCustomer.fxml");
    }
}
