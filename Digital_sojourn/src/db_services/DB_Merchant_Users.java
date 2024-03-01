package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Merchant_Users {

    Connection connection = DB_Service.connect();

    // // Get Admin User Password by Admin ID
    public String GetMerchantUserPassword(int merchant_user_id) {
        String pwrd = "";
        
        try {
            System.out.println("Step 2a");
            String getMerchantUserPasswordMySql = "SELECT password FROM customers WHERE customer_id = ?";
            System.out.println("Step 2b");
            PreparedStatement getMerchantUserPassword = connection.prepareStatement(getMerchantUserPasswordMySql);
            System.out.println("Step 2c");
            getMerchantUserPassword.setInt(1, merchant_user_id);
            System.out.println("Step 2d");
            ResultSet getMerchantUserPasswordResult = getMerchantUserPassword.executeQuery();
            System.out.println("Step 2e");
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
}
