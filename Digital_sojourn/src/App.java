import Models.Customer;
import db_services.DB_Costumer;
import db_services.DB_Merchant;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DB_Costumer databaseCustomer = new DB_Costumer();

        // // TEST CREATE CUSTOMER
        // System.out.println(databaseCustomer
        // .AddCustomer("Arileza",
        // "Kupatri",
        // "arileza@gamil.com",
        // 123123337,
        // 0.00,
        // 2,
        // "arilezaTheWorst")); // printing the customer object

        // // TEST READ CUSTOMER BY ID
        // Customer prueba = databaseCustomer.GetCustomer(8);
        // System.out.println(prueba.toString());
        // System.out.println(prueba.getFirstName());

        // //Update Customer basic info
        // databaseCustomer.updateCustomerInfo(8, 12345000, "Angel", "Martinez",
        // "wiky@gmail.com");

        // // Test get all customers
        // System.out.println(databaseCustomer.GetAllCustomers());

        DB_Merchant databaseMerchant = new DB_Merchant();
        // // Test get Merchant
        // System.out.println(databaseMerchant.GetMerchant(1));

        // // Test get all Merchants
        // System.out.println(databaseMerchant.GetAllMerchants());

    }
}
