package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Transaction;

public class DB_Transactions {

    static Connection connection = DB_Service.connect();

    // Insert transactions
    public static int InsertTransaction(int customer_id, String dateTrans, double amount, int merchant_id) {

        int transId = -1;

        try {
            connection.setAutoCommit(false);

            String insertTransMysql = "INSERT INTO transactions(customer_id, date, amount, merchant_id) VALUES(?,?,?,?)";
            PreparedStatement insertTrans = connection.prepareStatement(insertTransMysql,
                    Statement.RETURN_GENERATED_KEYS);
            insertTrans.setInt(1, customer_id);
            insertTrans.setString(2, dateTrans);
            insertTrans.setDouble(3, amount);
            insertTrans.setInt(4, merchant_id);

            insertTrans.executeUpdate();

            ResultSet insertTransResult = insertTrans.getGeneratedKeys();
            if (insertTransResult.next()) {
                transId = insertTransResult.getInt(1);
            }

            if (transId > 1) {
                connection.commit();

            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error inserting transaction has occured : " + e.getMessage());
        }
        return transId;
    }

    // Insert detail transactions
    public static int InsertDetailTransaction(int trans_id, int product_id, double price, double quantity) {

        int detailTransID = -1;

        try {
            String insertDetailTransMysql = "INSERT INTO detail_trans(trans_id, product_id, price, quantity) VALUES(?,?,?,?)";
            PreparedStatement insertDetailTrans = connection.prepareStatement(insertDetailTransMysql);
            insertDetailTrans.setInt(1, trans_id);
            insertDetailTrans.setInt(2, product_id);
            insertDetailTrans.setDouble(3, price);
            insertDetailTrans.setDouble(4, quantity);

            insertDetailTrans.executeQuery();

            ResultSet insertDetailTransResult = insertDetailTrans.getGeneratedKeys();
            if (insertDetailTransResult.next()) {
                detailTransID = insertDetailTransResult.getInt(1);
            }

            if (detailTransID > 1) {
                connection.commit();

            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error inserting detail transaction has occured : " + e.getMessage());
        }

        return detailTransID;

    }

    // Get Transactions by customer id
    public static ArrayList<Transaction> GetTransByCustomer(int customer_id, String initialDate, String finalDate) {

        ArrayList<Transaction> transCustomerList = new ArrayList<>();

        try {
            String getTransbyCustomerMySql = "SELECT * FROM transactions WHERE customer_id= ? AND date BETWEEN ? AND ?";
            PreparedStatement getTransByCustomer = connection.prepareStatement(getTransbyCustomerMySql);
            getTransByCustomer.setInt(1, customer_id);
            getTransByCustomer.setString(2, initialDate);
            getTransByCustomer.setString(3, finalDate);
            ResultSet getTransByCustomerResult = getTransByCustomer.executeQuery();
            while (getTransByCustomerResult.next()) {

                int transID = getTransByCustomerResult.getInt("trans_id");
                int customerID = getTransByCustomerResult.getInt("customer_id");
                String dateTrans = getTransByCustomerResult.getString("date");
                double amount = getTransByCustomerResult.getDouble("amount");
                int merchantID = getTransByCustomerResult.getInt("merchant_id");

                transCustomerList.add(new Transaction(transID, customerID, dateTrans, amount, merchantID));
            }

        } catch (SQLException e) {
            System.err.println("An error getting customer transaction list has occured: " +
                    e.getMessage());
        }
        return transCustomerList;
    }

    // //Get Transactions by Merchant
    public static ArrayList<Transaction> GetTransByMerchant(int merchant_id, String initialDate, String finalDate) {

        ArrayList<Transaction> transMerchantList = new ArrayList<>();

        try {
            String getTransbyMerchantMySql = "SELECT * FROM transactions WHERE merchant_id= ? AND date BETWEEN ? AND ?";
            PreparedStatement getTransByMerchant = connection.prepareStatement(getTransbyMerchantMySql);
            getTransByMerchant.setInt(1, merchant_id);
            getTransByMerchant.setString(2, initialDate);
            getTransByMerchant.setString(3, finalDate);
            ResultSet getTransByMerchantResult = getTransByMerchant.executeQuery();
            while (getTransByMerchantResult.next()) {

                int transID = getTransByMerchantResult.getInt("trans_id");
                int customerID = getTransByMerchantResult.getInt("customer_id");
                String dateTrans = getTransByMerchantResult.getString("date");
                double amount = getTransByMerchantResult.getDouble("amount");
                int merchantID = getTransByMerchantResult.getInt("merchant_id");

                transMerchantList.add(new Transaction(transID, customerID, dateTrans, amount, merchantID));
            }

        } catch (SQLException e) {
            System.err.println("An error getting merchant transaction list has occured: " +
                    e.getMessage());
        }
        return transMerchantList;
    }

}