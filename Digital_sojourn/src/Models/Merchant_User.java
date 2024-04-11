package Models;

/**
 * This class is used to create a Merchant_User object with the following
 * attributes: merchantUserID, merchantID, firstName, lastName, email, phone,
 * role, password, active
 * 
 */
public class Merchant_User {

    private int merchantUserID;
    private int merchantID;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private String role;
    private String password;
    private int active;

    public Merchant_User(int merchantUserID, int merchantID, String firstName, String lastName, String email, int phone,
            String role, String password, int active) {
        this.merchantUserID = merchantUserID;
        this.merchantID = merchantID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.active = active;
    }

    public int getMerchantUserID() {
        return merchantUserID;
    }

    public void setMerchantUserID(int merchantUserID) {
        this.merchantUserID = merchantUserID;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Merchant User ID: " + merchantUserID +
                " Merchan ID: " + merchantID +
                " First Name: " + firstName +
                " Last Name: " + lastName +
                " email: " + email +
                " Phone Number: " + phone +
                " Role: " + role +
                " Password: " + password +
                " Active: " + active;

    }
}
