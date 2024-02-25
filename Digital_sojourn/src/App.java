import Models.Customer;
import db_services.DB_Costumer;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DB_Costumer database = new DB_Costumer();

        // // TEST CREATE CUSTOMER
        // System.out.println(database
        // .AddCustomer("Arileza",
        // "Kupatri",
        // "arileza@gamil.com",
        // 123123337,
        // 0.00,
        // 2,
        // "arilezaTheWorst")); // printing the customer object

        // // TEST READ CUSTOMER BY ID
        // Customer prueba = database.GetCustomer(8);
        // System.out.println(prueba.toString());
        // System.out.println(prueba.getFirstName());

        // Update Customer basic info

    }
}
