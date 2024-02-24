import db_services.DB_Costumer;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DB_Costumer database = new DB_Costumer();

        System.out.println(database.AddCustomer("Deneb")); // printing the customer id

    }
}
