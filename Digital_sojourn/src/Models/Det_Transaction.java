package Models;

/**
 * This class is used to create a Det_Transaction object with the following
 * attributes
 * det_trans_id, transID, productID, product_name, product_price, quantity
 * 
 */
public class Det_Transaction {
    private int det_trans_id;
    private int transID;
    private int productID;
    private String product_name;
    private double product_price;
    private double quantity;

    public Det_Transaction(int transID, int productID, double product_price, double quantity) {
        this.transID = transID;
        this.productID = productID;
        this.product_price = product_price;
        this.quantity = quantity;
    }

    public Det_Transaction(int det_trans_id, int transID, int productID, String product_name, double product_price,
            double quantity) {
        this.det_trans_id = det_trans_id;
        this.transID = transID;
        this.productID = productID;
        this.product_name = product_name;
        this.product_price = product_price;
        this.quantity = quantity;
    }

    public int getDet_trans_id() {

        return det_trans_id;
    }

    public int getTransID() {
        return transID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProduct_name() {
        return product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Trans ID: " + transID +
                " Product ID: " + productID +
                " Product Name: " + product_name +
                " Pice: " + product_price +
                " Quantity: " + quantity;
    }

}
