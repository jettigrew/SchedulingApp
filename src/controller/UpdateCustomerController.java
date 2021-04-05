package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UpdateCustomerController {
    @FXML
    private ComboBox<?> updateCountryCombo;

    @FXML
    private TextField updateCustomer_IDText;

    @FXML
    private TextField updateNameText;

    @FXML
    private TextField updateAddressText;

    @FXML
    private TextField updatePostalCodeText;

    @FXML
    private ComboBox<?> updateRegionCombo;

    @FXML
    private TextField updatePhoneNumberText;

    @FXML
    void cancelUpdateCustomerInfoHandler(ActionEvent event) {

    }

    @FXML
    void submitUpdateCustomerInfoHandler(ActionEvent event) {

    }
}
