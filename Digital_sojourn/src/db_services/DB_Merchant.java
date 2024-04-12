package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Merchant;

public class DB_Merchant {

    // Connect to the database
    static Connection connection = DB_Service.connect();

    /**
     * This method is used to get the merchant name of the merchant
     * 
     * @param merchant_id the id of the merchant
     * @return String name of the merchant
     * @throws SQLException if an error occurs while getting the merchant name from
     *                      the
     *                      database
     */
    public static Merchant getMerchant(int merchant_id) {

        Merchant merchant = null; // Initialize the merchant object to null

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

    /**
     * This method is used to get all the merchants from the database
     * 
     * @return ArrayList of Merchant objects
     */
    public static ArrayList<Merchant> getAllMerchants() {

        ArrayList<Merchant> merchants = new ArrayList<>(); // Initialize the merchants list

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
