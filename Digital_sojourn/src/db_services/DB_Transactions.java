package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Models.Det_Transaction;
import Models.Transaction;

public class DB_Transactions {

    // Connect to the database
    static Connection connection = DB_Service.connect();

    /**
     * This method is used to insert a transaction into the database
     * 
     * @param customer_id the id of the customer making the transaction
     * @param dateTrans   the date of the transaction
     * @param amount      the amount of the transaction
     * @param merchant_id the id of the merchant the transaction is made to
     * @return int the id of the transaction
     * @throws SQLException if an error occurs while inserting the transaction into
     *                      the database
     */
    public static int insertTransaction(int customer_id, String dateTrans, double amount, int merchant_id) {

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

    /**
     * This method is used to insert a detail transaction into the database
     * 
     * @param trans_id   the id of the transaction the detail is associated with
     * @param product_id the id of the product in the detail transaction
     * @param price      the price of the product in the detail transaction
     * @param quantity   the quantity of the product in the detail transaction
     * @return int the id of the detail transaction
     * @throws SQLException if an error occurs while inserting the detail
     *                      transaction
     */
    public static int insertDetailTransaction(int trans_id, int product_id, double price, double quantity) {

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

    /**
     * This method is used to get all the transactions from the database
     * 
     * @param customer_id the id of the customer making the transaction
     * @param initialDate the initial date of the transactions
     * @param finalDate   the final date of the transactions
     * @return ArrayList of Transaction objects
     * @throws SQLException if an error occurs while getting the transactions from
     *                      the database
     */
    public ArrayList<Transaction> getTransByCustomer(int customer_id, String initialDate, String finalDate) {

        ArrayList<Transaction> transCustomerList = new ArrayList<>();

        try {
            String getTransbyCustomerMySql = " SELECT t.trans_id, t.customer_id,  c.first_name, c.last_name, t.date, t.amount,t.merchant_id, m.name\n"
                    + //
                    "FROM transactions t\n" + //
                    "INNER JOIN customers c\n" + //
                    "ON(t.customer_id=c.customer_id)\n" + //
                    "INNER JOIN merchant m\n" + //
                    "ON (t.merchant_id = m.merchant_id)\n" + //
                    "WHERE t.customer_id= ? \n" + //
                    "AND date BETWEEN ? AND ?; ";
            PreparedStatement getTransByCustomer = connection.prepareStatement(getTransbyCustomerMySql);
            getTransByCustomer.setInt(1, customer_id);
            getTransByCustomer.setString(2, initialDate);
            getTransByCustomer.setString(3, finalDate);
            ResultSet getTransByCustomerResult = getTransByCustomer.executeQuery();
            while (getTransByCustomerResult.next()) {

                int transID = getTransByCustomerResult.getInt("t.trans_id");
                int customerID = getTransByCustomerResult.getInt("customer_id");
                String customerFirstName = getTransByCustomerResult.getString("first_name");
                String customerLastName = getTransByCustomerResult.getString("last_name");
                String dateTrans = getTransByCustomerResult.getString("date");
                double amount = getTransByCustomerResult.getDouble("amount");
                int merchantID = getTransByCustomerResult.getInt("merchant_id");
                String merchantName = getTransByCustomerResult.getString("name");

                transCustomerList.add(new Transaction(transID, customerID, customerFirstName, customerLastName,
                        dateTrans, amount, merchantID,
                        merchantName));
            }

        } catch (SQLException e) {
            System.err.println("An error getting customer transaction list has occured: " +
                    e.getMessage());
        }
        return transCustomerList;
    }

    /**
     * This method is used to get all the transactions from the database made by a
     * customer
     * 
     * @param parentId the id of the parent customer
     * @return ArrayList of Transaction objects
     * @throws SQLException if an error occurs while getting the transactions from
     *                      the database
     */
    public ArrayList<Transaction> getFamilyTransList(int parentId) {
        ArrayList<Transaction> familyTransList = new ArrayList<Transaction>();

        try {
            String getFamilyTransListMysql = "SELECT t.trans_id, t.customer_id, c.first_name, c.last_name, t.date, t.amount, t.merchant_id, m.name "
                    +
                    "FROM transactions t INNER JOIN customers c ON (t.customer_id= c.customer_id) " +
                    "INNER JOIN merchant m ON (t.merchant_id = m.merchant_id) " +
                    "WHERE t.customer_id IN (SELECT customer_id FROM customers " +
                    "WHERE customer_id = ? or parent_id = ?) " +
                    "ORDER BY t.date;";
            PreparedStatement getFamilyTransList = connection.prepareStatement(getFamilyTransListMysql);
            getFamilyTransList.setInt(1, parentId);
            getFamilyTransList.setInt(2, parentId);
            ResultSet getFamilyTransListResult = getFamilyTransList.executeQuery();
            while (getFamilyTransListResult.next()) {
                int transID = getFamilyTransListResult.getInt("trans_id");
                int customerID = getFamilyTransListResult.getInt("customer_id");
                String customerFirstName = getFamilyTransListResult.getString("first_name");
                String customerLastName = getFamilyTransListResult.getString("last_name");
                String dateTrans = getFamilyTransListResult.getString("date");
                double amount = getFamilyTransListResult.getDouble("amount");
                int merchantID = getFamilyTransListResult.getInt("merchant_id");
                String merchantName = getFamilyTransListResult.getString("name");

                familyTransList.add(new Transaction(transID, customerID, customerFirstName, customerLastName, dateTrans,
                        amount, merchantID, merchantName));
            }
        } catch (Exception e) {

            System.err.println("An error getting family transaction list has occured: " +
                    e.getMessage());
        }
        return familyTransList;

    }

    /**
     * This method is used to get all the transactions from the database made to a
     * merchant
     * 
     * @param merchant_id the id of the merchant the transaction is made to
     * @param initialDate the initial date of the transactions
     * @param finalDate   the final date of the transactions
     * @return ArrayList of Transaction objects
     * @throws SQLException if an error occurs while getting the transactions from
     *                      the database
     */
    public static ArrayList<Transaction> getTransByMerchant(int merchant_id, String initialDate, String finalDate) {

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

    /**
     * This method is used to get the detail of a transaction from the database
     * 
     * @param transID the id of the transaction to get the detail of the transaction
     * @return ArrayList of Det_Transaction objects
     * @throws SQLException if an error occurs while getting the detail of the
     *                      transaction from the database
     */
    public ArrayList<Det_Transaction> getDetTransaction(int transID) {
        ArrayList<Det_Transaction> transDetailList = new ArrayList<>();

        try {
            String getTransDetailMysql = "SELECT d.det_trans_id, d.trans_id, d.product_id, p.name, d.price, d.quantity \n"
                    + //
                    "FROM `detail_trans` d \n" + //
                    "INNER JOIN products p\n" + //
                    "ON(d.product_id = p.product_id)\n" + //
                    "WHERE d.trans_id = ?;";
            PreparedStatement getTransDetail = connection.prepareStatement(getTransDetailMysql);
            getTransDetail.setInt(1, transID);
            ResultSet getTransDetailResult = getTransDetail.executeQuery();
            while (getTransDetailResult.next()) {
                int detailTransID = getTransDetailResult.getInt("det_trans_id");
                int trans_ID = getTransDetailResult.getInt("trans_id");
                int productID = getTransDetailResult.getInt("product_id");
                String productName = getTransDetailResult.getString("name");
                double price = getTransDetailResult.getDouble("price");
                double quantity = getTransDetailResult.getDouble("quantity");

                transDetailList
                        .add(new Det_Transaction(detailTransID, trans_ID, productID, productName, price, quantity));
            }

        } catch (SQLException e) {
            System.err.println("An error getting transaction detail has occured: " +
                    e.getMessage());

        }
        return transDetailList;

    }
}