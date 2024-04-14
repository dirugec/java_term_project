package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Models.Merchant_User;

public class DB_Merchant_Users {
    // Connect to the database
    static Connection connection = DB_Service.connect();

    /**
     * This method is used to get the merchant password of the merchant user
     * 
     * @param merchant_user_id the id of the merchant user
     * @return String password of the merchant user
     * @throws SQLException if an error occurs while getting the merchant password
     *                      from the database
     */
    public String getMerchantUserPassword(int merchant_user_id) {
        String pwrd = "";

        try {
            String getMerchantUserPasswordMySql = "SELECT password FROM merchant_users WHERE merchant_user_id = ?";
            PreparedStatement getMerchantUserPassword = connection.prepareStatement(getMerchantUserPasswordMySql);
            getMerchantUserPassword.setInt(1, merchant_user_id);
            ResultSet getMerchantUserPasswordResult = getMerchantUserPassword.executeQuery();

            if (getMerchantUserPasswordResult.next()) {
                pwrd = getMerchantUserPasswordResult.getString("password");
            } else {
                System.err.println("No found Merchant User ID: " + merchant_user_id);
            }
        } catch (SQLException e) {
            System.err.println("An error Getting the Merchant User password has occured: " +
                    e.getMessage());
        }
        return pwrd;
    }

    /**
     * This method is used to get the merchant user from the database
     * 
     * @param merchant_user_id the id of the merchant user
     * @return Merchant_User object
     * @throws SQLException if an error occurs while getting the merchant user from
     *                      the database
     */
    public Merchant_User getMerchantUser(int merchant_user_id) {

        Merchant_User merchantUser = null; // Initialize the merchantUser object to null

        try {
            String getMerchantUserMySql = "SELECT merchant_user_id, merchant_id, first_name, last_name, email, phone, role, password, active FROM merchant_users WHERE merchant_user_id = ?";
            PreparedStatement getMerchantUser = connection.prepareStatement(getMerchantUserMySql);
            getMerchantUser.setInt(1, merchant_user_id);
            ResultSet getMerchantUserResult = getMerchantUser.executeQuery();
            if (getMerchantUserResult.next()) {
                int merchantUserID = getMerchantUserResult.getInt(1);
                int merchantID = getMerchantUserResult.getInt(2);
                String firstName = getMerchantUserResult.getString(3);
                String lastName = getMerchantUserResult.getString(4);
                String email = getMerchantUserResult.getString(5);
                int phone = getMerchantUserResult.getInt(6);
                String role = getMerchantUserResult.getString(7);
                String password = getMerchantUserResult.getString(8);
                int active = getMerchantUserResult.getInt(9);

                merchantUser = new Merchant_User(merchantUserID, merchantID, firstName, lastName, email, phone, role,
                        password, active);

                // connection.close();

            } else {
                System.err.println("No Merchant User found for merchant_user_id: " + merchant_user_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading Merchant user has occured: " +
                    e.getMessage());
        }
        return merchantUser;
    }

    /**
     * This method is used to update the password of a customer in the database.
     * 
     * @param merchant_user_id merchant user id to update password
     * @param password         new password to update
     * @return boolean This returns true if the password was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public static boolean updatePassword(int merchant_user_id, String password) {
        System.out.println("Merchant Users: Update Password");
        boolean blnSuccess = false;

        try {
            String updatePasswordMysql = "UPDATE merchant_users SET password = ? WHERE merchant_user_id = ?";
            PreparedStatement updatePassword = connection.prepareStatement(updatePasswordMysql);
            updatePassword.setString(1, password);
            updatePassword.setInt(2, merchant_user_id);
            updatePassword.executeUpdate();
            blnSuccess = true;

            // connection.close();
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return blnSuccess;
    }

    /**
     * This method is used to update customer information in the database.
     * 
     * @param merchant_user_id This is the Merchant User ID to be updated.
     * @param phone            This is the new phone number of the Merchant User.
     * @param first_name       This is the new first name of the Merchant User.
     * @param last_name        This is the new last name of the Merchant User.
     * @param email            This is the new email of the Merchant User.
     * @param role             This is the new role of the Merchant User.
     * @return boolean This returns true if the customer was successfully updated,
     *         and false otherwise.
     * @throws SQLException If an SQL error occurs, this exception is thrown.
     */
    public static boolean updateMerchantUserInfo(int merchant_user_id, String first_name, String last_name,
            String email, int phone, String role) {
        boolean success = false;

        try {
            String updateCustomerMysql = "UPDATE merchant_users SET first_name = ?, last_name= ? , email = ?, phone = ?, role = ? WHERE customer_id = ?;";
            PreparedStatement updateMerchantUser = connection.prepareStatement(updateCustomerMysql);
            updateMerchantUser.setString(1, first_name);
            updateMerchantUser.setString(2, last_name);
            updateMerchantUser.setString(3, email);
            updateMerchantUser.setInt(4, phone);
            updateMerchantUser.setString(5, role);
            updateMerchantUser.setInt(6, merchant_user_id);
            updateMerchantUser.executeUpdate();
            success = true;

        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
        return success;
    }
}
