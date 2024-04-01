package Models;

public class Transaction {

    private int transID;
    private int customerID;
    private String customerName;
    private String dateTrans;
    private double amount;
    private int merchantID;
    private String merchantName;

    public Transaction(int transID, int customerID, String customerName, String dateTrans, double amount,
            int merchantID, String merchantName) {
        this.transID = transID;
        this.customerID = customerID;
        this.dateTrans = dateTrans;
        this.amount = amount;
        this.merchantID = merchantID;
        this.customerName = customerName;
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

    public String getCustomerName() {
        return customerName;
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
                " Customer Name: " + customerName +
                " Date Trans: " + dateTrans +
                " Amount: " + amount +
                " Merchant ID: " + merchantID +
                " Merchant Name: " + merchantName;

    }
}