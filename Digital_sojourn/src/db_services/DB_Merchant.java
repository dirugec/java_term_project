package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Merchant;

public class DB_Merchant {

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

    public Merchant GetMerchant(int merchant_id) {

        Merchant merchant = null;
        Connection connection = connect();

        try {
            String getMerchantMysql = "SELECT merchant_id, name FROM merchant WHERE merchant_id = ?";
            PreparedStatement getMerchant = connection.prepareStatement(getMerchantMysql);
            getMerchant.setInt(1, merchant_id);
            ResultSet getMerchantResult = getMerchant.executeQuery();
            if (getMerchantResult.next()) {
                int merchantId = merchant_id;
                String name = getMerchantResult.getString(2);

                merchant = new Merchant(merchantId, name);
            }

        } catch (SQLException e) {
            System.err.println("No Merchant found merchant_id: " + merchant_id);
        }
        return merchant;
    }

    public ArrayList<Merchant> GetAllMerchants() {

        ArrayList<Merchant> merchants = new ArrayList<>();

        Connection connection = connect();

        try {
            String getAllMerchantsMySql = "SELECT merchant_id, name FROM merchant";
            PreparedStatement getAllMerchants = connection.prepareStatement(getAllMerchantsMySql);
            ResultSet getAllMerchantsResult = getAllMerchants.executeQuery();

            while (getAllMerchantsResult.next()) {
                int merchantId = getAllMerchantsResult.getInt("merchant_id");
                String name = getAllMerchantsResult.getString("name");

                merchants.add(new Merchant(merchantId, name));
            }
        } catch (SQLException e) {
            System.err.println("An error reading merchants list has occured: " + e.getMessage());
        }
        return merchants;
    }

}
