package db_services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DB_Service {

    // Database connection details
    static String url = "jdbc:mysql://localhost:3306/sojourn_db";
    static String user = "web";
    static String password = "web";

    /**
     * This method is used to connect to the database
     * 
     * @return Connection object
     * @throws SQLException if an error occurs while connecting to the database
     */
    public static Connection connect() {

        Connection connection; // Initialize the connection object
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
            e.printStackTrace();
        }
        return connection;

    }

}
