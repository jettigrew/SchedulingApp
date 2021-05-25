package model;

public class Contact {
    private int contactID;
    private String contactName;
    private String emailAddress;

    public Contact() {}

    public Contact(String contactName, String emailAddress) {
        this.contactName = contactName;
        this.emailAddress = emailAddress;
    }

    public Contact(int contactID, String contactName, String emailAddress) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.emailAddress = emailAddress;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString(){
        return contactName;
    }
}
