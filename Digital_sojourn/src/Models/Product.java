package Models;

/**
 * This class is used to create a Product object with the following attributes:
 * productID, name, price, merchantID
 * 
 */
public class Product {
    private int productID;
    private String name;
    private double price;
    private int merchantID;

    public Product(int productID, String name, double price, int merchantID) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.merchantID = merchantID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public String toString() {
        return "\nProduct Information\n" +
                "Product_id: " + productID +
                " Name: " + name +
                " price: " + price +
                " Merchant ID " + merchantID;
    }

}
