package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.SceneSwitch;

import java.io.IOException;

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
    void cancelAddCustomerInfoHandler(ActionEvent event) throws IOException {
        //TODO: Add condition with warning message
        SceneSwitch switcher = new SceneSwitch();
        switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
    }

    @FXML
    void submitAddCustomerInfoHandler(ActionEvent event) {

    }
}
