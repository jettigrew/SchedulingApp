package controller;

import DAO.CountryDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import util.AlertGenerator;
import util.SceneSwitch;

import java.io.IOException;

public class UpdateCustomerController {
    @FXML
    private ComboBox<Country> updateCountryCombo;

    @FXML
    private TextField updateCustomer_IDText;

    @FXML
    private TextField updateNameText;

    @FXML
    private TextField updateAddressText;

    @FXML
    private TextField updatePostalCodeText;

    @FXML
    private ComboBox<FirstLevelDivision> updateRegionCombo;

    @FXML
    private TextField updatePhoneNumberText;

    @FXML
    void cancelUpdateCustomerInfoHandler(ActionEvent event) throws IOException {
        AlertGenerator cancelUpdateAlert = new AlertGenerator();
        if (cancelUpdateAlert.getConfirmationAlert("Are you sure you want to cancel? Changes will not be saved.")) {
            SceneSwitch switcher = new SceneSwitch();
            switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
        }
    }

    @FXML
    void submitUpdateCustomerInfoHandler(ActionEvent event) throws IOException {
        Customer customer = new Customer();

        if (updateNameText.getText().trim().isEmpty()) {
            AlertGenerator nameAlert = new AlertGenerator();
            nameAlert.createAlert("ERROR", "Customer must have name.");
            return;
        }
        else {
            customer.setCustomerName(updateNameText.getText().trim());
        }

        if (updateAddressText.getText().trim().isEmpty()) {
            AlertGenerator addressAlert = new AlertGenerator();
            addressAlert.createAlert("ERROR", "Customer must have address.");
            return;
        }
        else {
            customer.setCustomerAddress(updateAddressText.getText().trim());
        }

        if (updatePostalCodeText.getText().trim().isEmpty()) {
            AlertGenerator postalAlert = new AlertGenerator();
            postalAlert.createAlert("ERROR", "Customer must have postal code.");
            return;
        }
        else {
            customer.setPostalCode(updatePostalCodeText.getText().trim());
        }

        if (updateCountryCombo.getValue() == null) {
            AlertGenerator countryAlert = new AlertGenerator();
            countryAlert.createAlert("ERROR", "Customer must have country.");
            return;
        }

        if (updateRegionCombo.getValue()== null) {
            AlertGenerator regionAlert = new AlertGenerator();
            regionAlert.createAlert("ERROR", "Customer must have region.");
            return;
        }
        else {
            FirstLevelDivision division = updateRegionCombo.getValue();
            customer.setDivisionID(division.getDivisionID());
        }

        if (updatePhoneNumberText.getText().trim().isEmpty()) {
            AlertGenerator phoneAlert = new AlertGenerator();
            phoneAlert.createAlert("ERROR", "Customer must have phone number.");
            return;
        }
        else {
            customer.setPhoneNumber(updatePhoneNumberText.getText().trim());
        }

        AlertGenerator updateCustomerAlert = new AlertGenerator();
        if (updateCustomerAlert.getConfirmationAlert("Update customer and return to Customers screen?")) {
            if (CustomerDAO.updateCustomer(Integer.parseInt(updateCustomer_IDText.getText()), customer.getCustomerName(),
                    customer.getCustomerAddress(), customer.getPostalCode(), customer.getPhoneNumber(), customer.getDivisionID())) {
                SceneSwitch switcher = new SceneSwitch();
                switcher.switchScenes(event, "/view/DisplayCustomers.fxml");
            }
            else {
                AlertGenerator updateFailedAlert = new AlertGenerator();
                updateFailedAlert.createAlert("ERROR", "Customer was not updated.");
            }
        }
    }

    public void sendCustomer(Customer customer) {
        updateCustomer_IDText.setText(String.valueOf(customer.getCustomerID()));
        updateNameText.setText(customer.getCustomerName());
        updateAddressText.setText(customer.getCustomerAddress());
        updatePostalCodeText.setText(customer.getPostalCode());
        updateCountryCombo.setValue(FirstLevelDivisionDAO.retrieveAssociatedCountry(customer.getDivisionID()));
        updateRegionCombo.setValue(FirstLevelDivisionDAO.retrieveFirstLevelDivision(customer.getDivisionID()));
        updatePhoneNumberText.setText(customer.getPhoneNumber());
    }

    @FXML
    public void initialize() {
        updateCustomer_IDText.setDisable(true);
        updateCountryCombo.setItems(CountryDAO.retrieveAllCountries());

        //Anonymous class would be unnecessary and more unwieldy here, lambda simplifies this EventHandler code
        EventHandler<ActionEvent> selectCountry = actionEvent -> {
            int selectedCountryID = updateCountryCombo.getValue().getCountryID();
            updateRegionCombo.setItems(FirstLevelDivisionDAO.retrieveFirstLevelDivisionsByCountryID(selectedCountryID));
        };

        updateCountryCombo.setOnAction(selectCountry);
    }
}
