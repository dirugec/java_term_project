package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Admin_User;
import Models.Merchant_User;

public class DB_Merchant_Users {

    static Connection connection = DB_Service.connect();

    // // Get Admin User Password by Admin ID
    public String getMerchantUserPassword(int merchant_user_id) {
        String pwrd = "";

        try {
            String getMerchantUserPasswordMySql = "SELECT password FROM customers WHERE customer_id = ?";
            PreparedStatement getMerchantUserPassword = connection.prepareStatement(getMerchantUserPasswordMySql);
            getMerchantUserPassword.setInt(1, merchant_user_id);
            ResultSet getMerchantUserPasswordResult = getMerchantUserPassword.executeQuery();

            if (getMerchantUserPasswordResult.next()) {
                pwrd = getMerchantUserPasswordResult.getString("password");
            } else {
                System.err.println("No found merchant_user_id: " + merchant_user_id);
            }
        } catch (SQLException e) {
            System.err.println("An error Getting the Merchant User password has occured: " +
                    e.getMessage());
        }
        return pwrd;
    }

    // Get Merchant User Object
    public static Merchant_User getMerchantUser(int merchant_user_id) {
        Merchant_User merchantUser = null;

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

                connection.close();

            } else {
                System.err.println("No Merchant User found for merchant_user_id: " + merchant_user_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading Merchant user has occured: " +
                    e.getMessage());
        }
        return merchantUser;
    }

}
