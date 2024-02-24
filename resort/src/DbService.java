import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {
    String url = "jdbc:mysql//localhost:3306/resort";
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

    // CREATE
    int AddCustomer(String first_name) {
        int customer_id = -1;
        Connection connection = connect();

        try {
            connection.setAutoCommit(false);
            // Add Customer
            String addCustomerMySql = "insert int customers(first_name) values(?)";
            PreparedStatement addCustomer = connection.prepareStatement(addCustomerMySql,
                    Statement.RETURN_GENERATED_KEYS); // preparableStatement make shure avoid the script injection
            // Statement.RETURN_GENERATED_KEY retunr the key generated
            addCustomer.setString(1, first_name);
            addCustomer.executeUpdate();
            // getting the customer id
            ResultSet addCustomerResults = addCustomer.getGeneratedKeys();
            if (addCustomerResults.next()) {
                customer_id = addCustomerResults.getInt(1);
            }

            if (customer_id > 1) {
                connection.commit();
            } else {
                connection.rollback();
            }

            connection.close();

        } catch (SQLException e) {
            System.err.println("An error has occured: " + e.getMessage());
        }
        return customer_id;

    }

}
