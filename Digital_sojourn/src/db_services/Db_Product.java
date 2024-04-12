package db_services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Models.Product;

public class DB_Product {
    // Connect to the database
    static Connection connection = DB_Service.connect();

    /**
     * This method is used to get the product name of the product
     * 
     * @param product_id the id of the product
     * @return String name of the product
     * @throws SQLException if an error occurs while getting the product name from
     *                      the
     *                      database
     */
    public static Product getProduct(int product_id) {
        Product product = null;

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

    /**
     * This method is used to get all the products from the database
     * 
     * @param merchant_id the id of the merchant
     * @return ArrayList of Product objects
     * @throws SQLException if an error occurs while getting the products from the
     */
    public static ArrayList<Product> getProductsByMercant(int merchant_id) {
        ArrayList<Product> productsByMerchant = new ArrayList<>();

        try {
            System.out.println("Product 0");
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
        } catch (SQLException e) { // TODO: Was not called when there is no return query
            System.err.println("No Products found for Merchant id: " + merchant_id);
        }
        return productsByMerchant;
    }
}
