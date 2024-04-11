package Models;

/**
 * This class is used to create a Merchant object with the following attributes:
 * merchantId, name
 * 
 */
public class Merchant {
    private int merchantId;
    private String name;

    public Merchant(int merchantId, String name) {
        this.merchantId = merchantId;
        this.name = name;
    }

    public int getMerchantID() {
        return merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nMerchant Information\n" +
                "Merchant_id: " + merchantId +
                " Name: " + name;
    }
}
