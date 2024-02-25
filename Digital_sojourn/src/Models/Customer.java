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

    public Customer(int customerID, String firstName, String lastName, String email, int phone, double balance,
            int parent_id, String password) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.parentId = parent_id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "\nCustomer Information\n" +
                "Customer_id: " + customerID +
                "Firt Name: " + firstName +
                "Last Name: " + lastName +
                "email: " + email +
                "Phone NUmber: " + phone +
                "Balance Account: " + balance +
                "Parten ID: " + parentId +
                "Password: " + password;

    }
}
