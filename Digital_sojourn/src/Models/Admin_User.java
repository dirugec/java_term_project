package Models;

public class Admin_User {

    private int adminID;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private String role;
    private String password;
    private int active;

    public Admin_User(int adminID, String firstName, String lastName, String email, int phone, String role,
            String password, int active) {
        this.adminID = adminID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.password = password;
        this.active = active;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
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
        return "Admin_id: " + adminID +
                " First Name: " + firstName +
                " Last Name: " + lastName +
                " email: " + email +
                " Phone Number: " + phone +
                " Role: " + role +
                " Password: " + password +
                " Active: " + active;

    }
}
