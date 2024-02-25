package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db_Product {
    String url = "jdbc:mysql://localhost:3306/RESORT_DB";
    String user = "resort";
    String password = "resort1234";

    private Connection connect() {

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
