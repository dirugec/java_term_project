package db_services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DB_Service {

    static String url = "jdbc:mysql://localhost:3306/sujourn_db";
    static String user = "web";
    static String password = "web";

    public static Connection connect() {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
            e.printStackTrace();
        }
        return connection;

    }

}
