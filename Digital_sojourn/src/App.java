import Models.Customer;
import db_services.DB_Costumer;
import db_services.DB_Merchant;
import db_services.DB_Product;
import db_services.DB_Transactions;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DB_Costumer databaseCustomer = new DB_Costumer();

        // // TEST CREATE CUSTOMER
        // System.out.println(databaseCustomer.AddCustomer("John",
        // "Doe",
        // "john-doe@gamil.com",
        // 123123337,
        // 0.00,
        // 2,
        // "WhoAmI",
        // 1,
        // 1)); //

        // TEST READ CUSTOMER BY ID
        // Customer prueba = databaseCustomer.GetCustomer(9);
        // System.out.println(prueba.toString());
        // System.out.println(prueba.getFirstName());

        // Update Customer basic info
        // databaseCustomer.updateCustomerInfo(8, 12345000, "Angel", "Martinez",
        // "wiky@gmail.com");

        // // Test get all customers
        // System.out.println(databaseCustomer.GetAllCustomers());

        // Test get customer balance
        // System.out.println(databaseCustomer.GetCustomerBalance(1));

        // // Update Balance
        // System.out.println(databaseCustomer.UpdateBalance(9, 2000));

        // // Get family members
        // System.out.println(databaseCustomer.GetFamilyMembers(9));

        DB_Merchant databaseMerchant = new DB_Merchant();
        // // Test get Merchant
        // System.out.println(databaseMerchant.GetMerchant(1));

        // // Test get all Merchants
        // System.out.println(databaseMerchant.GetAllMerchants());

        // DB_Product databaseProduct = new DB_Product();

        // // Test get Product
        // System.out.println(databaseProduct.GetProduct(1));

        // // Test product list for merchant
        // System.out.println(databaseProduct.GetProductsByMercant(3));

        // // Test Transactions
        DB_Transactions dataTranstacion = new DB_Transactions();

        // // Insert Transaction
        // System.out.println(dataTranstacion.InsertTransaction(9, "20240227", 2, 300,
        // -1));

        // // get transactions by customer
        // System.out.println(dataTranstacion.GetTransByCustomer(9, "20240222",
        // "20240228"));

        // // Get transactions by merchant
        // System.out.println(dataTranstacion.GetTransByMerchant(1, "20240222",
        // "20240228"));

    }
}
