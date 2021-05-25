package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import util.AlertGenerator;
import util.SceneSwitch;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class AddCustomerController {
    @FXML
    private ComboBox<Country> addCountryCombo;

    @FXML
    private TextField addCustomer_IDText;

    @FXML
    private TextField addNameText;

    @FXML
    private TextField addAddressText;

    @FXML
    private TextField addPostalCodeText;

    @FXML
    private ComboBox<FirstLevelDivision> addRegionCombo;

    @FXML
    private TextField addPhoneNumberText;

    @FXML
    void cancelAddCustomerInfoHandler(ActionEvent event) throws IOException {
        AlertGenerator cancelAddAlert = new AlertGenerator();
        if (cancelAddAlert.getConfirmationAlert("Are you sure you want to cancel? Changes will not be saved.")) {
            SceneSwitch switcher = new SceneSwitch();
            switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
        }
    }

    @FXML
    void submitAddCustomerInfoHandler(ActionEvent event) throws IOException {
        Customer customer = new Customer();

        if (addNameText.getText().trim().isEmpty() || addNameText.getText().trim().equals("Customer name")) {
            AlertGenerator nameAlert = new AlertGenerator();
            nameAlert.createAlert("ERROR", "Customer must have name.");
            return;
        }
        else {
            customer.setCustomerName(addNameText.getText().trim());
        }

        if (addAddressText.getText().trim().isEmpty() || addAddressText.getText().trim().equals("Street address + City")) {
            AlertGenerator addressAlert = new AlertGenerator();
            addressAlert.createAlert("ERROR", "Customer must have address.");
            return;
        }
        else {
            customer.setCustomerAddress(addAddressText.getText().trim());
        }

        if (addPostalCodeText.getText().trim().isEmpty() || addPostalCodeText.getText().trim().equals("Postal code")) {
            AlertGenerator postalAlert = new AlertGenerator();
            postalAlert.createAlert("ERROR", "Customer must have postal code.");
            return;
        }
        else {
            customer.setPostalCode(addPostalCodeText.getText().trim());
        }

        if (addCountryCombo.getValue() == null) {
            AlertGenerator countryAlert = new AlertGenerator();
            countryAlert.createAlert("ERROR", "Customer must have country.");
            return;
        }

        if (addRegionCombo.getValue()== null) {
            AlertGenerator regionAlert = new AlertGenerator();
            regionAlert.createAlert("ERROR", "Customer must have region.");
            return;
        }
        else {
            FirstLevelDivision division = addRegionCombo.getValue();
            customer.setDivisionID(division.getDivisionID());
        }

        if (addPhoneNumberText.getText().trim().isEmpty() || addPhoneNumberText.getText().trim().equals("Phone number")) {
            AlertGenerator phoneAlert = new AlertGenerator();
            phoneAlert.createAlert("ERROR", "Customer must have phone number.");
            return;
        }
        else {
            customer.setPhoneNumber(addPhoneNumberText.getText().trim());
        }

        AlertGenerator newCustomerAlert = new AlertGenerator();
        if (newCustomerAlert.getConfirmationAlert("Save new customer and return to Customers screen?")) {
            if (CustomerDAO.createCustomer(customer)) {
                SceneSwitch switcher = new SceneSwitch();
                switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
            }
            else {
                AlertGenerator additionFailedAlert = new AlertGenerator();
                additionFailedAlert.createAlert("ERROR", "Customer was not added.");
            }
        }
    }

    @FXML
    public void initialize() {
        addCustomer_IDText.setDisable(true);
        addCustomer_IDText.setText("Auto Gen - Disabled");
        addNameText.setText("Customer name");
        addAddressText.setText("Street address + City");
        addPostalCodeText.setText("Postal code");
        addCountryCombo.setItems(CountryDAO.retrieveAllCountries());
        addPhoneNumberText.setText("Phone number");

        //Anonymous class would be unnecessary and more unwieldy here, lambda simplifies this EventHandler code
        EventHandler<ActionEvent> selectCountry = actionEvent -> {
            int selectedCountryID = addCountryCombo.getValue().getCountryID();
            addRegionCombo.setItems(FirstLevelDivisionDAO.retrieveFirstLevelDivisionsByCountryID(selectedCountryID));
        };

        addCountryCombo.setOnAction(selectCountry);
    }
}
