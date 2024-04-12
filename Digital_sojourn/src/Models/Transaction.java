package Models;

/**
 * This class is used to create a Transaction object with the following
 * attributes:
 * transID, customerID, customerFirstName, customerLastName, dateTrans, amount,
 * merchantID, merchantName
 */
public class Transaction {

    private int transID;
    private int customerID;
    private String customerFirstName;
    private String customerLastName;
    private String dateTrans;
    private double amount;
    private int merchantID;
    private String merchantName;

    public Transaction(int transID, int customerID, String customerFirstName, String customerLastName, String dateTrans,
            double amount,
            int merchantID, String merchantName) {
        this.transID = transID;
        this.customerID = customerID;
        this.dateTrans = dateTrans;
        this.amount = amount;
        this.merchantID = merchantID;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.merchantName = merchantName;
    }

    public Transaction(int transID, int customerID, String dateTrans, double amount, int merchantID) {
        this.transID = transID;
        this.customerID = customerID;
        this.dateTrans = dateTrans;
        this.amount = amount;
        this.merchantID = merchantID;
    }

    public int getTransID() {
        return transID;
    }

    public void setTransID(int transID) {
        this.transID = transID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getDateTrans() {
        return dateTrans;
    }

    public void setDateTrans(String dateTrans) {
        this.dateTrans = dateTrans;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(int merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Trans ID: " + transID +
                " Customer ID: " + customerID +
                " First Name: " + customerFirstName +
                " Last Name: " + customerLastName +
                " Date Trans: " + dateTrans +
                " Amount: " + amount +
                " Merchant ID: " + merchantID +
                " Merchant Name: " + merchantName;

    }
}