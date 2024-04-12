package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Customer;

public class DB_Costumer {
    // Connect to the database
    static Connection connection = DB_Service.connect();

    /**
     * This method is used to add a new customer to the database.
     *
     * @param customer This is the customer object to be added. It should contain
     *                 all the necessary information like name, address, etc.
     * @return boolean This returns true if the customer was successfully added, and
     *         false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public int addCustomer(Customer customer) {
        int customerId = -1;

        try {
            connection.setAutoCommit(false);
            // Add Customer
            String addCustomerMySql = "INSERT INTO customers(first_name, last_name,email, phone, balance, parent_id, password, user_type, active) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement addCustomer = connection.prepareStatement(addCustomerMySql,
                    Statement.RETURN_GENERATED_KEYS); // preparableStatement make shure avoid the script injection
            // Statement.RETURN_GENERATED_KEY retunr the key generated
            addCustomer.setString(1, customer.getFirstName());
            addCustomer.setString(2, customer.getLastName());
            addCustomer.setString(3, customer.getEmail());
            addCustomer.setInt(4, customer.getPhone());
            addCustomer.setDouble(5, customer.getBalance());
            addCustomer.setInt(6, customer.getParentId());
            addCustomer.setString(7, customer.getPassword());
            addCustomer.setInt(8, customer.getUserType());
            addCustomer.setInt(9, customer.getActive());

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

    /**
     * This method is used to check if a customer exists in the database.
     * 
     * @param customerID This is the customer ID to be checked.
     * @return boolean This returns true if the customer exists, and false
     *         otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     * 
     */
    public boolean checkIfCustomerExist(int customerID) {

        boolean customerExist = false;
        int count = 0;

        try {
            String checkCustomerExistMysql = "SELECT COUNT(*) FROM customers WHERE customer_id = ?;";
            PreparedStatement getCheckCustomerExist = connection.prepareStatement(checkCustomerExistMysql);
            getCheckCustomerExist.setInt(1, customerID);
            ResultSet getCheckCustomerExistResult = getCheckCustomerExist.executeQuery();
            if (getCheckCustomerExistResult.next()) {
                count = getCheckCustomerExistResult.getInt(1);
                if (count > 0) {
                    customerExist = true;
                }
            }

        } catch (Exception e) {
            System.err.println("An error checking if customer exist: " + e.getMessage());
        }
        return customerExist;
    }

    /**
     * This method is used to get a customer from the database.
     * 
     * @param customer_id This is the customer ID to be retrieved.
     * @return Customer This returns a customer object.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     * 
     */
    public Customer getCustomer(int customer_id) {
        Customer customer = null;
        try {
            String getCustomerMySql = "SELECT customer_id, first_name, last_name,email, phone, balance, parent_id,password, active, user_type FROM customers WHERE customer_id = ?";
            PreparedStatement getCustomer = connection.prepareStatement(getCustomerMySql);
            getCustomer.setInt(1, customer_id);
            ResultSet getCustomerResult = getCustomer.executeQuery();
            if (getCustomerResult.next()) {
                int customerId = getCustomerResult.getInt(1);
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

            } else {
                System.err.println("No customer found for customer_id: " + customer_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading customer has occured: " + e.getMessage());
        }
        return customer;
    }

    /**
     * This method is used to get all customers from the database.
     * 
     * @return ArrayList<Customer> This returns a list of all customers.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     * 
     */
    public static ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        try {
            String getAllCustomersMysql = "SELECT customer_id, first_name,last_name,email, phone, balance, parent_id,password, active, user_type FROM customers";
            PreparedStatement getAllCustomers = connection.prepareStatement(getAllCustomersMysql);
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
                int userType = getAllCustomerResult.getInt("user_type");

                customers.add(new Customer(customerId, fisrtName, lastName, email, phone,
                        balance, parentId, password, active, userType));

            }
        } catch (SQLException e) {
            System.err.println("An error reading customer list has occured: " +
                    e.getMessage());
        }

        return customers;
    }

    /**
     * This method is used to update customer information in the database.
     * 
     * @param customer_id This is the customer ID to be updated.
     * @param phone       This is the new phone number of the customer.
     * @param first_name  This is the new first name of the customer.
     * @param last_name   This is the new last name of the customer.
     * @param email       This is the new email of the customer.
     * @param active      This is the new active status of the customer.
     * @return boolean This returns true if the customer was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public boolean updateCustomerInfo(int customer_id, int phone, String first_name, String last_name,
            String email, int active) {
        boolean success = false;

        try {
            String updateCustomerMysql = "UPDATE customers SET first_name = ?, last_name= ? , email = ?, phone = ?, active = ? WHERE customer_id = ?;";
            PreparedStatement updateCustomer = connection.prepareStatement(updateCustomerMysql);
            updateCustomer.setString(1, first_name);
            updateCustomer.setString(2, last_name);
            updateCustomer.setString(3, email);
            updateCustomer.setInt(4, phone);
            updateCustomer.setInt(5, active);
            updateCustomer.setInt(6, customer_id);
            updateCustomer.executeUpdate();
            success = true;

        } catch (SQLException e) {

            System.err.println("An error updating customer has occured: " +
                    e.getMessage());

        }
        return success;
    }

    /**
     * This method is used to update the active status of a customer in the
     * database.
     * 
     * @param customer_id This is the customer ID to be updated.
     * @param active      This is the new active status of the customer.
     * @return boolean This returns true if the customer was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     * 
     */
    public static boolean updateActiveStatus(int customer_id, int active) {

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

    /**
     * This method is used to get the balance of a customer from the database.
     * 
     * @param customer_id This is the customer ID to be retrieved.
     * @return double This returns the balance of the customer.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public static double getCustomerBalance(int customer_id) {

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

    /**
     * This method is used to update the balance of a customer in the database.
     * 
     * @param customer_id customer id to update balance
     * @param newBalance  new balance to update
     * @return String success if the balance was successfully updated, and an error
     *         message otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public static boolean updateBalance(int customer_id, double newBalance) {
        boolean blnReturn = false;

        try {
            connection.setAutoCommit(true);
            String loadFundsMysql = "UPDATE customers SET balance = ? WHERE customer_id=?";
            PreparedStatement loadFunds = connection.prepareStatement(loadFundsMysql);
            loadFunds.setDouble(1, newBalance);
            loadFunds.setInt(2, customer_id);
            loadFunds.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return blnReturn;
    }

    /**
     * This method is used to get the customer password of the customer
     * 
     * @param customer_id the id of the customer
     * @return String the password of the customer
     * @throws SQLException if an error occurs while getting the customer password
     *                      from the database
     */
    public String getCustomerPassword(int customer_id) {
        String pwrd = "";

        try {
            String getCustomerPasswordMySql = "SELECT password FROM customers WHERE customer_id = ?";
            PreparedStatement getCustomerPassword = connection.prepareStatement(getCustomerPasswordMySql);
            getCustomerPassword.setInt(1, customer_id);
            ResultSet getCustomerPasswordResult = getCustomerPassword.executeQuery();

            if (getCustomerPasswordResult.next()) {
                pwrd = getCustomerPasswordResult.getString("password");
            } else {
                System.err.println("No found customer_id: " + customer_id);
            }
        } catch (SQLException e) {
            System.err.println("An error Getting the customer password has occured: " +
                    e.getMessage());
        }
        return pwrd;
    }

    /**
     * This method is used to update the password of a customer in the database.
     * 
     * @param customer_id customer id to update password
     * @param password    new password to update
     * @return boolean This returns true if the password was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public static boolean updatePassword(int customer_id, String password) {

        boolean success = false;

        try {
            String updatePasswordMysql = "UPDATE customers SET password = ? WHERE customer_id = ?";
            PreparedStatement updatePassword = connection.prepareStatement(updatePasswordMysql);
            updatePassword.setString(1, password);
            updatePassword.setInt(2, customer_id);
            updatePassword.executeUpdate();
            success = true;

            //connection.close();

        } catch (SQLException e) {
            System.err.println("An error updating customer password has occured: " +
                    e.getMessage());
        }

        return success;
    }

    /**
     * This method is used to get the family members of a customer from the
     * database.
     * 
     * @param customer_id This is the customer ID to be retrieved.
     * @return ArrayList<Customer> This returns a list of all family members.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public ArrayList<Customer> getFamilyMembers(int customer_id) {

        ArrayList<Customer> familyMembers = new ArrayList<>();

        try {
            String getFamilyMembersMysql = "SELECT customer_id, first_name,last_name,email, phone," +
                    "balance, parent_id,password,user_type, active  FROM customers WHERE parent_id = ?";
            PreparedStatement getFamilyMembers = connection.prepareStatement(getFamilyMembersMysql);
            getFamilyMembers.setInt(1, customer_id);
            ResultSet getFamilyMembersResult = getFamilyMembers.executeQuery();
            while (getFamilyMembersResult.next()) {
                int customerId = getFamilyMembersResult.getInt("customer_id");
                String fisrtName = getFamilyMembersResult.getString("first_name");
                String lastName = getFamilyMembersResult.getString("last_name");
                String email = getFamilyMembersResult.getString("email");
                int phone = getFamilyMembersResult.getInt("phone");
                double balance = getFamilyMembersResult.getDouble("balance");
                int parentId = getFamilyMembersResult.getInt("parent_id");
                String password = getFamilyMembersResult.getString("password");
                int userType = getFamilyMembersResult.getInt("user_type");
                int active = getFamilyMembersResult.getInt("active");

                familyMembers.add(new Customer(customerId, fisrtName, lastName, email, phone,
                        balance, parentId, password, userType, active));

            }
        } catch (SQLException e) {
            System.err.println("An error getting Family Members list has occured: " +
                    e.getMessage());
        }

        return familyMembers;

    }

    /**
     * This method is used to update the parent_id of a customer in the database.
     * 
     * @param customer_id customer id to update parent_id
     * @param parent_id   new parent_id to update
     * @return boolean This returns true if the parent_id was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public boolean updateParentId(int customer_id, int parent_id) {
        boolean success = false;

        try {

            if (parent_id == customer_id) {
                System.err.println("\nFamily member can't be his/her own parent\n");
                return false;
            }

            String updateParentIdMysql = "UPDATE customers SET parent_id = ? WHERE customer_id = ?";
            PreparedStatement updateParentId = connection.prepareStatement(updateParentIdMysql);
            updateParentId.setInt(1, parent_id);
            updateParentId.setInt(2, customer_id);
            updateParentId.executeUpdate();
            success = true;

        } catch (SQLException e) {
            System.err.println("An error updating parent_id has occured: " +
                    e.getMessage());
        }
        return success;
    }

}
