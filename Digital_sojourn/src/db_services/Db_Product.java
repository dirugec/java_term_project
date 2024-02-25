package db_services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Product;

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

    public Product GetProduct(int product_id) {
        Product product = null;
        Connection connection = connect();

        try {
            String getProductMySql = "SELECT product_id, name, price, merchant_id FROM products WHERE product_id = ?";
            PreparedStatement getProduct = connection.prepareStatement(getProductMySql);
            getProduct.setInt(1, product_id);
            ResultSet getProductResult = getProduct.executeQuery();
            if (getProductResult.next()) {
                int productID = product_id;
                String name = getProductResult.getString(2);
                double price = getProductResult.getDouble(3);
                int merchantID = getProductResult.getInt(4);

                product = new Product(productID, name, price, merchantID);
            }
        } catch (SQLException e) {
            System.err.println("No Product found product_id: " + product_id);
        }
        return product;
    }

    // TODO: CREATE PRODUCT BY MERCHANT
    public ArrayList<Product> GetProductsByMercant(int merchant_id) {
        ArrayList<Product> productsByMerchant = new ArrayList<>();

        Connection connection = connect();

        try {
            String getProductsByMerchantMySql = "SELECT product_id, name, price, merchant_id FROM products WHERE merchant_id = ?";
            PreparedStatement getProductByMerchant = connection.prepareStatement(getProductsByMerchantMySql);
            getProductByMerchant.setInt(1, merchant_id);
            ResultSet getProductByMerchantResult = getProductByMerchant.executeQuery();
            while (getProductByMerchantResult.next()) {
                int productID = getProductByMerchantResult.getInt("product_id");
                String name = getProductByMerchantResult.getString("name");
                double price = getProductByMerchantResult.getDouble("price");
                int merchantID = getProductByMerchantResult.getInt("merchant_id");

                productsByMerchant.add(new Product(productID, name, price, merchantID));
            }
        } catch (SQLException e) {
            System.err.println("No Products found for Merchant id: " + merchant_id);
        }
        return productsByMerchant;
    }
}
