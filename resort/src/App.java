public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        DbService database = new DbService();

        database.AddCustomer("pedro");

    }
}
