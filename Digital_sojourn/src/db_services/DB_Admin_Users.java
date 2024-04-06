package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Models.Admin_User;

public class DB_Admin_Users {

    static Connection connection = DB_Service.connect();

    // // Get Admin User Password by Admin ID
    public String getAdminPassword(int admin_id) {
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

    public static Admin_User getAdminUser(int admin_id) {

        Admin_User adminUser = null;

        try {
            String getAdminUserMySql = "SELECT admin_id, first_name, last_name,email, phone, role, password, active FROM admin_users WHERE admin_id = ?";
            PreparedStatement getAdminUser = connection.prepareStatement(getAdminUserMySql);
            getAdminUser.setInt(1, admin_id);
            ResultSet getAdminUserResult = getAdminUser.executeQuery();
            if (getAdminUserResult.next()) {
                int adminId = getAdminUserResult.getInt(1);
                String fisrtName = getAdminUserResult.getString(2);
                String lastName = getAdminUserResult.getString(3);
                String email = getAdminUserResult.getString(4);
                int phone = getAdminUserResult.getInt(5);
                String role = getAdminUserResult.getString(6);
                String password = getAdminUserResult.getString(7);
                int active = getAdminUserResult.getInt(8);

                adminUser = new Admin_User(adminId, fisrtName, lastName, email, phone, role, password, active);

                connection.close();

            } else {
                System.err.println("No Admin User found for admin_id: " + admin_id);
            }
        } catch (SQLException e) {
            System.err.println("An error reading admin user has occured: " +
                    e.getMessage());
        }
        return adminUser;
    }
}
