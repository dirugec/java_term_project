package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB_Admin_Users {

    Connection connection = DB_Service.connect();

    // // Get Admin User Password by Admin ID
    public String GetAdminPassword(int admin_id) {
        String pwrd = "";

        try {
            System.out.println("Step 2a");
            String getAdminPasswordMySql = "SELECT password FROM admin_users WHERE admin_id = ?";
            System.out.println("Step 2b");
            PreparedStatement getAdminPassword = connection.prepareStatement(getAdminPasswordMySql);
            System.out.println("Step 2c");
            getAdminPassword.setInt(1, admin_id);
            System.out.println("Step 2d");
            ResultSet getAdminPasswordResult = getAdminPassword.executeQuery();
            System.out.println("Step 2e");

            if (getAdminPasswordResult.next()) {
                pwrd = getAdminPasswordResult.getString("password");
            } else {
                System.err.println("No found admin_id: " + admin_id);
            }
        } catch (SQLException e) {
            System.err.println("An error Getting the admin user password has occured: " +
                    e.getMessage());
        }
        return pwrd;
    }
}
