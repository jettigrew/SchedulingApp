package model;

import java.time.LocalDateTime;

public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String postalCode;
    private String phoneNumber;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String associatedCountryName;
    private String associatedDivisionName;

    public Customer() {}

    //TODO: deleted LocalDateTime createDate, LocalDateTime lastUpdate, String createdBy, String lastUpdatedBy check that this works
    public Customer(String customerName, String customerAddress, String postalCode, String phoneNumber,
                    String createdBy, String lastUpdatedBy, int divisionID, String associatedCountryName,
                    String associatedDivisionName) {
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.divisionID = divisionID;
        this.associatedCountryName = associatedCountryName;
        this.associatedDivisionName = associatedDivisionName;
    }

    public Customer(int customerID, String customerName, String customerAddress, String postalCode, String phoneNumber,
                    LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy,
                    int divisionID, String associatedCountryName, String associatedDivisionName) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getAssociatedCountryName() { return associatedCountryName; }

    public void setAssociatedCountryName(String associatedCountryName) { this.associatedCountryName = associatedCountryName; }

    public String getAssociatedDivisionName() { return associatedDivisionName; }

    public void setAssociatedDivisionName(String associatedDivisionName) { this.associatedDivisionName = associatedDivisionName; }
}
