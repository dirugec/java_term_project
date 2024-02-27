package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Models.Customer;

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
        boolean succes = false;
        Connection connection = connect();

        try {
            connection.setAutoCommit(true);
            String loadFounsMysql = "UPDATE customers SET balance = ? WHERE customer_id = ?";
            PreparedStatement loadFounds = connection.prepareStatement(loadFounsMysql);
            loadFounds.setDouble(1, amount);
            loadFounds.setInt(2, customer_id);
            succes = true;
            System.out.println(loadFounds);
            loadFounds.executeUpdate();
            // connection.commit();
            connection.close();
        } catch (SQLException e) {
            System.err.println("An error updating customer has occured:" + e.getMessage());
        }
        return succes;

    }

}
