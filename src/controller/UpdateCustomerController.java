package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.SceneSwitch;

import java.io.IOException;

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
    void cancelUpdateCustomerInfoHandler(ActionEvent event) throws IOException {
        //TODO: Add condition with warning message
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
    }

    @FXML
    void submitUpdateCustomerInfoHandler(ActionEvent event) {

    }
}
