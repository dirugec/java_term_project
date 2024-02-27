package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB_Transactions {

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

    public boolean LoadFounds(int customer_id, double amount) {
        boolean success = false;
        Connection connection = connect();

        double initialBalance = 0;
        double calculatedNewBalance = 0;

        DB_Costumer databaseCustomer = new DB_Costumer();
        initialBalance = databaseCustomer.GetCustomerBalance(customer_id);

        if (initialBalance >= 0) {
            try {

                calculatedNewBalance = initialBalance + amount;

                connection.setAutoCommit(true);
                String loadFounsMysql = "UPDATE customers SET balance = ? WHERE customer_id =?";
                PreparedStatement loadFounds = connection.prepareStatement(loadFounsMysql);
                loadFounds.setDouble(1, calculatedNewBalance);
                loadFounds.setInt(2, customer_id);
                success = true;

                loadFounds.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                System.err.println("An error updating customer has occured:" + e.getMessage());
            }
            return success;
        }

        return success;

    }

    // public boolean purchase(int customer_id, int merchant_id) {

    // boolean success = false;

    // Connection connection = connect();

    // try {

    // // Validate Customer founds

    // } catch (SQLException e) {
    // System.err.println("An error with the purchase transaction has occurred : " +
    // e.getMessage());

    // }

    // return success;

    // }
}