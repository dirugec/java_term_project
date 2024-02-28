package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB_Transactions {

    Connection connection = DB_Service.connect();

    public boolean InsertTransaction(int customer_id, String dateTrans, String type, double amount, int merchant_id) {
        boolean success = false;

        int transId = -1;

        try {
            connection.setAutoCommit(false);

            String insertTransMysql = "INSERT INTO transactions(customer_id, date, type, amount, merchant_id) VALUES(?,?,?,?,?)";
            PreparedStatement insertTrans = connection.prepareStatement(insertTransMysql,
                    Statement.RETURN_GENERATED_KEYS);
            insertTrans.setInt(1, customer_id);
            insertTrans.setString(2, dateTrans);
            insertTrans.setString(3, type);
            insertTrans.setDouble(4, amount);
            insertTrans.setInt(5, merchant_id);

            insertTrans.executeUpdate();

            ResultSet insertTransResult = insertTrans.getGeneratedKeys();
            if (insertTransResult.next()) {
                transId = insertTransResult.getInt(1);
            }

            if (transId > 1) {
                connection.commit();
                success = true;
            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error inserting transaction has occured : " + e.getMessage());
        }
        return success;
    }

    public boolean LoadFunds(int customer_id, double amount) {
        boolean success = false;

        double initialBalance = 0;
        double calculatedNewBalance = 0;

        DB_Costumer databaseCustomer = new DB_Costumer();
        initialBalance = databaseCustomer.GetCustomerBalance(customer_id);

        if (initialBalance >= 0) {
            try {

                calculatedNewBalance = initialBalance + amount;

                connection.setAutoCommit(true);
                String loadFundsMysql = "UPDATE customers SET balance = ? WHERE customer_id=?";
                PreparedStatement loadFunds = connection.prepareStatement(loadFundsMysql);
                loadFunds.setDouble(1, calculatedNewBalance);
                loadFunds.setInt(2, customer_id);
                // success = true;
                loadFunds.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                System.err.println("An error updating customer has occured:" +
                        e.getMessage());
            }

            // TODO: PLANNIG THE LOGIC STRUCTURE TO VALIDATE INSERT TRANSACTION AND UPDATE
            // BALANCE

            return success;
        }

        return success;

    }

    // public boolean purchase(int customer_id, int merchant_id, double amount) {

    // boolean success = false;

    // double initialBalance = 0;
    // double calculatedNewBalance = 0;

    // DB_Costumer databaseCustomer = new DB_Costumer();
    // initialBalance = databaseCustomer.GetCustomerBalance(customer_id);

    // if (initialBalance)

    // return success;

    // }
}