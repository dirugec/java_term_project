package Models;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private int phone;
    private double balance;
    private int parentId;
    private String password;
    private int active;

    // Constructor
    public Customer(int customerID, String firstName, String lastName, String email, int phone, double balance,
            int parent_id, String password, int active) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.parentId = parent_id;
        this.password = password;
        this.active = active;

    }

    // Getters and Setters
    public int getCustomerID() {
        return customerID;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
        return "\nCustomer Information\n" +
                "Customer_id: " + customerID +
                " First Name: " + firstName +
                " Last Name: " + lastName +
                " email: " + email +
                " Phone Number: " + phone +
                " Balance Account: " + balance +
                " Parent ID: " + parentId +
                " Password: " + password +
                " Active: " + active;

    }
}
