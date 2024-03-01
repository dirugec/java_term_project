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
            String getAdminPasswordMySql = "SELECT password FROM admin_users WHERE admin_id = ?";
            PreparedStatement getAdminPassword = connection.prepareStatement(getAdminPasswordMySql);
            getAdminPassword.setInt(1, admin_id);
            ResultSet getAdminPasswordResult = getAdminPassword.executeQuery();

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
