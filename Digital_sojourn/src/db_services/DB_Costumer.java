package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Customer;

public class DB_Costumer {

    Connection connection = DB_Service.connect();

    // CREATE Customer
    public int AddCustomer(String first_name, String last_name, String email, int phone, double balance,
            int parent_id,
            String password, int active, int userType) {
        int customerId = -1;

        try {
            connection.setAutoCommit(false);
            // Add Customer
            String addCustomerMySql = "INSERT INTO customers(first_name, last_name,email, phone, balance, parent_id, password, active, user_type) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement addCustomer = connection.prepareStatement(addCustomerMySql,
                    Statement.RETURN_GENERATED_KEYS); // preparableStatement make shure avoid the script injection
            // Statement.RETURN_GENERATED_KEY retunr the key generated
            addCustomer.setString(1, first_name);
            addCustomer.setString(2, last_name);
            addCustomer.setString(3, email);
            addCustomer.setInt(4, phone);
            addCustomer.setDouble(5, balance);
            addCustomer.setInt(6, parent_id);
            addCustomer.setString(7, password);
            addCustomer.setInt(8, active);
            addCustomer.setInt(9, userType);
            addCustomer.executeUpdate();

            // getting the customer id
            ResultSet addCustomerResult = addCustomer.getGeneratedKeys();
            if (addCustomerResult.next()) {
                customerId = addCustomerResult.getInt(1);

            }

            if (customerId > 1) {
                connection.commit();
            } else {
                connection.rollback();
            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error has occured: " + e.getMessage());
        }
        return customerId;

    }

    // READ Customer Info
    public Customer GetCustomer(int customer_id) {

        Customer customer = null;

        try {
            String getCustomerMySql = "SELECT customer_id, first_name, last_name,email, phone, balance, parent_id,password, active, user_type FROM customers WHERE customer_id = ?";
            PreparedStatement getCustomer = connection.prepareStatement(getCustomerMySql);
            getCustomer.setInt(1, customer_id);
            ResultSet getCustomerResult = getCustomer.executeQuery();
            if (getCustomerResult.next()) {
                int customerId = customer_id;
                String fisrtName = getCustomerResult.getString(2);
                String lastName = getCustomerResult.getString(3);
                String email = getCustomerResult.getString(4);
                int phone = getCustomerResult.getInt(5);
                double balance = getCustomerResult.getDouble(6);
                int parentId = getCustomerResult.getInt(7);
                String password = getCustomerResult.getString(8);
                int active = getCustomerResult.getInt(9);
                int userType = getCustomerResult.getInt(10);

                customer = new Customer(customerId, fisrtName, lastName, email, phone,
                        balance, parentId, password, active, userType);

                connection.close();

            } else {
                System.err.println("No customer found for customer_id: " + customer_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading customer has occured: " +
                    e.getMessage());
        }
        return customer;
    }

    // Read All Customers
    public ArrayList<Customer> GetAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        try {
            String gellAllCustomersMysql = "SELECT customer_id, first_name,last_name,email, phone, balance, parent_id,password, active, user_type FROM customers";
            PreparedStatement getAllCustomers = connection.prepareStatement(gellAllCustomersMysql);
            ResultSet getAllCustomerResult = getAllCustomers.executeQuery();
            while (getAllCustomerResult.next()) {
                int customerId = getAllCustomerResult.getInt("customer_id");
                String fisrtName = getAllCustomerResult.getString("first_name");
                String lastName = getAllCustomerResult.getString("last_name");
                String email = getAllCustomerResult.getString("email");
                int phone = getAllCustomerResult.getInt("phone");
                double balance = getAllCustomerResult.getDouble("balance");
                int parentId = getAllCustomerResult.getInt("parent_id");
                String password = getAllCustomerResult.getString("password");
                int active = getAllCustomerResult.getInt("active");
                int user_type = getAllCustomerResult.getInt("user_tpe");

                customers.add(new Customer(customerId, fisrtName, lastName, email, phone,
                        balance, parentId, password, active, user_type));

            }
        } catch (SQLException e) {
            System.err.println("An error reading customer list has occured: " +
                    e.getMessage());
        }

        return customers;
    }

    // Updtae Customer Info

    public boolean updateCustomerInfo(int customer_id, int phone, String first_name, String last_name, String email) {
        boolean success = false;

        try {
            String updateCustomerMysql = "UPDATE customers SET first_name = ?, last_name= ? , email = ?, phone = ? WHERE customer_id = ?";
            PreparedStatement updateCustomer = connection.prepareStatement(updateCustomerMysql);
            updateCustomer.setString(1, first_name);
            updateCustomer.setString(2, last_name);
            updateCustomer.setString(3, email);
            updateCustomer.setInt(4, phone);
            updateCustomer.setInt(5, customer_id);
            updateCustomer.executeUpdate();
            success = true;

            connection.close();
        } catch (SQLException e) {

            System.err.println("An error updating customer has occured: " +
                    e.getMessage());

        }
        return success;
    }

    // // Update Customer Status Active/Desactive

    public boolean UpdateActiveStatus(int customer_id, int active) {

        boolean success = false;

        try {
            String updateCustomerStatusMysql = "UPDATE customers SET active = ? WHERE customer_id = ?";
            PreparedStatement updateCustomerStatus = connection.prepareStatement(updateCustomerStatusMysql);
            updateCustomerStatus.setInt(1, active);
            updateCustomerStatus.setInt(2, customer_id);
            updateCustomerStatus.executeUpdate();
            success = true;

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error updating customer status has occured: " +
                    e.getMessage());
        }

        return success;
    }

    public double GetCustomerBalance(int customer_id) {

        double balance = -1;

        try {
            String getCustomerBalanceMysql = "SELECT balance FROM customers WHERE customer_id = ?";
            PreparedStatement getCustomerBalance = connection.prepareStatement(getCustomerBalanceMysql);
            getCustomerBalance.setInt(1, customer_id);
            ResultSet getCustomerBalanceResult = getCustomerBalance.executeQuery();
            if (getCustomerBalanceResult.next()) {
                balance = getCustomerBalanceResult.getDouble("balance");

            } else {
                System.err.println("No balance found for customer_id: " + customer_id);
            }

        } catch (SQLException e) {
            System.err.println("An error Getting the customer balance has occured: " +
                    e.getMessage());
        }
        return balance;
    }

    // // Update Customer Password
    public boolean UpdatePassword(int customer_id, String password) {

        boolean success = false;

        try {
            String updatePasswordMysql = "UPDATE customers SET password = ? WHERE customer_id = ?";
            PreparedStatement updatePassword = connection.prepareStatement(updatePasswordMysql);
            updatePassword.setString(1, password);
            updatePassword.setInt(2, customer_id);
            updatePassword.executeUpdate();
            success = true;

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error updating customer password has occured: " +
                    e.getMessage());
        }

        return success;
    }

}
