package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddCustomerController {
    @FXML
    private ComboBox<?> addCountryCombo;

    @FXML
    private TextField addCustomer_IDText;

    @FXML
    private TextField addNameText;

    @FXML
    private TextField addAddressText;

    @FXML
    private TextField addPostalCodeText;

    @FXML
    private ComboBox<?> addRegionCombo;

    @FXML
    private TextField addPhoneNumberText;

    @FXML
    void cancelAddCustomerInfoHandler(ActionEvent event) {

    }

    @FXML
    void submitAddCustomerInfoHandler(ActionEvent event) {

    }
}
