package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    // CREATE
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

    // READ
    public Customer GetCustomer(int customer_id) {

        Customer customer = null;
        Connection connection = connect();
        try {
            String getCustomerMySql = "SELECT customer_id, first_name, last_name,email, phone, balance, parent_id,password FROM customers WHERE customer_id = ?";
            PreparedStatement getCustomer = connection.prepareStatement(getCustomerMySql);
            getCustomer.setInt(1, customer_id);
            ResultSet getCustomerResult = getCustomer.executeQuery();
            if (getCustomerResult.next()) {
                int customerId = (customer_id);
                String fisrtName = getCustomerResult.getString(2);
                String lastName = getCustomerResult.getString(3);
                String email = getCustomerResult.getString(4);
                int phone = getCustomerResult.getInt(5);
                double balance = getCustomerResult.getDouble(6);
                int parentId = getCustomerResult.getInt(7);
                String password = getCustomerResult.getString(8);
                customer = new Customer(customerId, fisrtName, lastName, email, phone, balance, parentId, password);

            } else {
                System.err.println("No customer found do customer_id: " + customer_id);
            }
        } catch (SQLException e) {
            System.err.println("An error has occured: " + e.getMessage());
        }
        return customer;
    }

}
