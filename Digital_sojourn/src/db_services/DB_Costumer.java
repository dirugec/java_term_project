package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.spi.DirStateFactory.Result;

import Models.Customer;

public class DB_Costumer {

    String url = "jdbc:mysql://localhost:3306/RESORT_DB";
    String user = "resort";
    String password = "resort1234";

    private Connection connect() {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
            e.printStackTrace();
        }
        return connection;
    }

    // CREATE Customer
    public int AddCustomer(String first_name, String last_name, String email, int phone, double balance,
            int parent_id,
            String password) {
        int customerId = -1;

        Connection connection = connect();

        try {
            connection.setAutoCommit(false);
            // Add Customer
            String addCustomerMySql = "insert into customers(first_name, last_name,email, phone, balance, parent_id,password) values(?,?,?,?,?,?,?)";
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
        Connection connection = connect();
        try {
            String getCustomerMySql = "SELECT customer_id, first_name, last_name,email, phone, balance, parent_id,password FROM customers WHERE customer_id = ?";
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
                customer = new Customer(customerId, fisrtName, lastName, email, phone, balance, parentId, password);

                connection.close();

            } else {
                System.err.println("No customer found for customer_id: " + customer_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading customer has occured: " + e.getMessage());
        }
        return customer;
    }

    // Read All Customers
    public ArrayList<Customer> GetAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        Connection connection = connect();
        try {
            String gellAllCustomersMysql = "SELECT customer_id, first_name, last_name,email, phone, balance, parent_id,password FROM customers";
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

                customers.add(new Customer(customerId, fisrtName, lastName, email, phone, balance, parentId, password));

            }
        } catch (SQLException e) {
            System.err.println("An error reading customer list has occured: " + e.getMessage());
        }

        return customers;
    }

    // Updtae Customer Info

    // TODO: IT IS NOT WORKING
    // public boolean updateCustomerInfo(int customer_id, int phone, String
    // first_name, String last_name, String email) {
    // boolean success = false;
    // Connection connection = connect();

    // try {
    // String updateCustomerMysql = "UPDATE customers SET first_name = ? WHERE
    // customer_id = ?";
    // PreparedStatement updateCustomer =
    // connection.prepareStatement(updateCustomerMysql);
    // updateCustomer.setString(1, first_name);
    // // updateCustomer.setString(2, last_name);
    // // updateCustomer.setString(3, email);
    // // updateCustomer.setInt(4, phone);
    // updateCustomer.setInt(2, customer_id);
    // success = true;

    // connection.close();
    // } catch (SQLException e) {
    // System.err.println("An error updating customer has occured: " +
    // e.getMessage());

    // }
    // return success;
    // }
}
