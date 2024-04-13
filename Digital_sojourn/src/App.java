
import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Console;

import Models.Admin_User;
import Models.Customer;
import Models.Det_Transaction;
import Models.Merchant;
import Models.Merchant_User;
import Models.Transaction;
import Models.Product;
import db_services.DB_Admin_Users;
import db_services.DB_Costumer;
import db_services.DB_Merchant_Users;
import db_services.DB_Merchant;
import db_services.DB_Product;
import db_services.DB_Transactions;

public class App {

    private static int gUserType; // 1 = Guest, 2 = Admin, 3 = Merchant
    private static int gUserID; // User ID for the current session

    private static Customer gCustomer; // Guest User Object for the current session
    private static Merchant gMerchant; // Merchant Object for the current session
    private static Merchant_User gMerchantUser; // Merchant User Object for the current session
    private static Admin_User gAdmin; // Admin User Object for the current session

    private static DB_Admin_Users dbAdminUser; // Admin User Database Object
    private static DB_Costumer dbCustomer; // Guest User Database Object
    private static DB_Product dbProduct; // Product Database Object
    //private static DB_Merchant_Users dbMerchantUser; // Merchant User Database Object
    private static DB_Transactions dbTransactions; // Transaction Database Object

    public static void main(String[] args) throws Exception {

        dbCustomer = new DB_Costumer();
        dbAdminUser = new DB_Admin_Users();
        //dbMerchantUser = new DB_Merchant_Users();
        dbTransactions = new DB_Transactions();

        // Display the login menu
        displayLoginMenu();

    }

    // ********** GENERAL METHODS **********
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Load the Guest User details based on the User ID
     */
    private static void loadGuestDetails() {
        System.out.println("Please enter Primary User ID: ");
        System.out.print("> ");

        gUserID = Integer.parseInt(System.console().readLine());
        gCustomer = dbCustomer.getCustomer(gUserID);
    }

    /**
     * 
     * @param title
     */
    private static void printHeaders(String title, int fullLength) {

        int dashRepeat = (fullLength - (title.length() + 2)) / 2;

        System.out.println("");
        System.out.println("-".repeat(fullLength));
        System.out.println("-".repeat(dashRepeat) + " " + title + " "
                + ((title.length() % 2 == 0) ? "" : "-") + "-".repeat(dashRepeat));
        System.out.println("-".repeat(fullLength));

    }

    /**
     * Display the header of the application
     */
    private static void displayHeader() {
        System.out.print(" ".repeat(40));
        System.out.println("____/--------------------\\___");
        System.out.print(" ".repeat(40));
        System.out.println("|                            |");
        System.out.print(" ".repeat(40));
        System.out.println("|       DIGITAL SOJOURN      |");
        System.out.print(" ".repeat(40));
        System.out.println("|                            |");
        System.out.print(" ".repeat(40));
        System.out.println("----\\--------------------/----");
    }

    /**
     * Display the main menu for the User
     */
    public static void displayLoginMenu() {
        boolean blnValidInput = false;
        boolean blnVerifiedPassword = false;
        do {
            try { // Determine type of User
                displayHeader();
                printHeaders("LOGIN", 50);
                System.out.println("[1] Guest");
                System.out.println("[2] Admin");
                System.out.println("[3] Merchant");
                System.out.println("[0] Exit");
                System.out.print("Please enter your choice: ");

                gUserType = Integer.parseInt(System.console().readLine());
                if (gUserType == 0) { // Exit App
                    blnValidInput = true;
                } else if ((gUserType > 0) && (gUserType < 4)) {
                    // blnValidInput = true;
                    blnVerifiedPassword = verifyPassword();
                    if (blnVerifiedPassword) {
                        switch (gUserType) {
                            case 1: // Guest
                                displayMainMenuGuest();
                                break;
                            case 2: // Admin
                                displayMainMenuAdminUser();
                                break;
                            case 3: // Merchant
                                displayMainMenuMerchantUser();
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    System.out.println("Invalid input. Select from choices.\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValidInput);
    }

    /**
     * Verify the password of the user
     * 
     * @return boolean - True if the password is verified
     * @throws Exception - Invalid input for UserID (Numbers only)
     */
    public static boolean verifyPassword() {
        // Retrieve login details
        boolean blnValidInput = false;
        boolean blnVerifiedPassword = false;
        do {
            try {
                System.out.println("------------------------------");
                System.out.print("Please enter ID Number: ");
                gUserID = Integer.parseInt(System.console().readLine());
                System.out.print("Please enter Password (Enter X to go back): ");
                String strPassword = System.console().readLine();
                String dbPassword;

                // Allows the user to go back to the main menu
                if (strPassword.toUpperCase().equals("X")) {
                    gUserType = 0;
                }

                // Verify login credentials
                switch (gUserType) {
                    case 0:
                        blnValidInput = true;
                        break;
                    case 1: // Login Customer
                        // Call the GetPassword script
                        dbPassword = dbCustomer.getCustomerPassword(gUserID);
                        if (dbPassword.equals(strPassword)) {
                            blnValidInput = true;
                            blnVerifiedPassword = true;
                            gCustomer = dbCustomer.getCustomer(gUserID);
                            System.out.println("Valid Password");
                        } else { // Invalid Password
                            System.out.println("Invalid Password");
                        }
                        break;
                    case 2: // Login Admin
                        // Call the GetPassword script
                        dbPassword = dbAdminUser.getAdminPassword(gUserID);
                        if (strPassword.equals(dbPassword)) {
                            blnValidInput = true;
                            blnVerifiedPassword = true;
                            gAdmin = dbAdminUser.getAdminUser(gUserID);
                        } else { // Invalid Password
                            System.out.println("Invalid Password");
                        }
                        break;
                    case 3: // Login Merchant
                        // Call the GetPassword script
                        dbPassword = DB_Merchant_Users.getMerchantUserPassword(gUserID);
                        if (strPassword.equals(dbPassword)) {
                            blnValidInput = true;
                            blnVerifiedPassword = true;
                            // Instansiate the global user: Merchant User
                            gMerchantUser = DB_Merchant_Users.getMerchantUser(gUserID);
                        } else { // Invalid Password
                            System.out.println("Invalid Password");
                        }
                        break;
                    default:
                        System.out.println("Switch: Default");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only for UserID.");
            }
        } while (!blnValidInput);

        return blnVerifiedPassword;
    }

    /**
     * Display the main menu for the User based on the User Type (Guest, Admin,
     * Merchant) and the User ID
     */
    public static void displayMainMenuGuest() {
        boolean blnValid = false;
        int iChoice = -1;

        displayHeader();
        do {
            try {
                printHeaders("MAIN MENU", 40);
                System.out.println("[1] View Balance");
                System.out.println("[2] Load Funds");
                System.out.println("[3] View Transactions");
                System.out.println("[4] Family Members");
                System.out.println("[5] Settings");
                System.out.println("[6] Back");
                System.out.println("[0] Exit");
                System.out.print("> ");
                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        viewBalance();
                        break;
                    case 2:
                        displayLoadFunds();
                        break;
                    case 3:
                        displayViewTransactions();
                        break;
                    case 4:
                        FamilyMembersManage();
                        break;
                    case 5:
                        displaySettings();
                        break;
                    case 6:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    /**
     * Display the main menu for the Admin User based on the User ID
     */
    public static void displayMainMenuAdminUser() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                printHeaders("ADMIN MAIN MENU", 40);
                System.out.println("[1] Manage Guest Users");
                System.out.println("[2] Manage Merchant Users");
                System.out.println("[3] Back");
                System.out.println("[0] Exit");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        displayViewPrimaryGuestDetails();
                        break;
                    case 2:

                        break;
                    case 3:
                        blnValid = true;
                        break;
                    // case 4:
                    // break;
                    // case 5:
                    // blnValid = true;
                    // break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    // ********** GUEST USER INTERFACE **********

    /**
     * Display the Guest user balance based on the User ID
     */
    private static void viewBalance() {
        printHeaders("VIEW BALANCE", 40);

        System.out.printf("First Name: %s\n", gCustomer.getFirstName());
        System.out.printf("Last Name: %s\n", gCustomer.getLastName());
        System.out.printf("Current Balance: $%,.2f\n\n", gCustomer.getBalance());
    }

    /**
     * Procedure to load funds to the Guest User
     */
    private static void displayLoadFunds() {
        boolean blnValid = false;

        do {
            try {

                printHeaders("LOAD FUNDS", 40);

                System.out.print("Please enter amount to load: $");
                double iAmount = Double.parseDouble(System.console().readLine());

                // Call Script: Load Funds
                double newBalance = gCustomer.getBalance() + iAmount;
                if (dbCustomer.updateBalance(gUserID, newBalance)) {
                    System.out.printf("$%,.2f has been added to %s\n", iAmount,
                            gCustomer.getFirstName() + " " + gCustomer.getLastName());
                    System.out.printf("The new balance is $%,.2f \n\n", DB_Costumer.getCustomerBalance(gUserID));
                    blnValid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please ");
            }
        } while (!blnValid);

    }

    /**
     * Display the transactions for the Guest User based on the User ID
     */
    private static void displayViewTransactions() {

        ArrayList<Transaction> arrayTransactions = new ArrayList<Transaction>();
        ArrayList<Det_Transaction> det_TransactionsList = new ArrayList<Det_Transaction>();

        boolean blnValid = false;
        do {
            try {
                printHeaders("VIEW TRANSACTIONS", 70);

                if (gUserType == 2) { // if User Type is Admin
                    System.out.print("Please enter Primary User ID: ");
                    gUserID = Integer.parseInt(System.console().readLine());
                    gCustomer = dbCustomer.getCustomer(gUserID);
                }

                System.out.print("Please enter start date MM/DD/YYYY: ");
                String iStartDate = System.console().readLine();

                // Input validation start date RegEx
                while (!iStartDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                    System.out.println("Use the format MM/DD/YYYY ");
                    System.out.print("Please enter start date MM/DD/YYYY: ");
                    iStartDate = System.console().readLine();
                }

                String[] arrOfiStartDate = iStartDate.split("/", 3);
                String mysqlStartDate = arrOfiStartDate[2] + arrOfiStartDate[0] + arrOfiStartDate[1];

                // Input validation end date RegEx
                System.out.print("Please enter end date MM/DD/YYYY: ");
                String iEndDate = System.console().readLine();

                while (!iEndDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                    System.out.println("Use the format MM/DD/YYYY ");
                    System.out.print("Please enter end date MM/DD/YYYY: ");
                    iEndDate = System.console().readLine();
                }
                // Validate end date after or same as start date
                while ((iEndDate.compareTo(iStartDate) < 0)) {
                    System.out.println("End date should be the same or after start date");
                    System.out.print("Please enter end date MM/DD/YYYY: ");
                    iEndDate = System.console().readLine();
                }

                // Modifying the string input for use it in a mysql script
                String[] arrOfiEndtDate = iEndDate.split("/", 3);
                String mysqlEndDate = arrOfiEndtDate[2] + arrOfiEndtDate[0] +
                        arrOfiEndtDate[1];

                // Call script to get transactions given date
                arrayTransactions = dbTransactions.getTransByCustomer(gUserID, mysqlStartDate, mysqlEndDate);
                System.out.println();
                System.out.println("-".repeat(70));
                System.out.println("Transactions for " + gCustomer.getFirstName() + " " + gCustomer.getLastName()
                        + " from " + iStartDate + " to " + iEndDate);
                System.out.println("-".repeat(70));

                // Loop through the result set and display the transactions
                for (Transaction transaction : arrayTransactions) {
                    System.out.printf("\n%-10s %-25s %-6s\n", "Date", "Merchant", "Amount");
                    System.out.println("-".repeat(70));
                    System.out.printf("%-10s %-25s %-6.2f \n", transaction.getDateTrans(),
                            transaction.getMerchantName(), transaction.getAmount());
                    System.out.printf("\n%50s\n", "*********  Det Transaction *********");

                    det_TransactionsList = dbTransactions.getDetTransaction(transaction.getTransID());
                    for (Det_Transaction det_Transaction : det_TransactionsList) {
                        System.out.printf("Product: %-25s Price: %6.2f Quantity: %4.2f\n",
                                det_Transaction.getProduct_name(),
                                det_Transaction.getProduct_price(), det_Transaction.getQuantity());

                    }
                    System.out.println("*".repeat(70));
                    blnValid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.  " + e);
            }
        } while (!blnValid);
    }

    /**
     * Display the family members asociated with the Guest User based on the User ID
     */
    private static ArrayList<Customer> displayFamilyMembers() {
        ArrayList<Customer> arrayFamilyMembers = new ArrayList<Customer>();
        arrayFamilyMembers = dbCustomer.getFamilyMembers(gCustomer.getCustomerID());

        printHeaders("FAMILY MEMBERS", 100);
        System.out.printf("%-4s %-15s %-15s %-20s %-15s %-15s %-10s\n", "ID", "First Name", "Last Name", "Email",
                "Phone",
                "Balance", "Active");
        System.out.println("-".repeat(100));

        // Loop through the result set and display the family members
        for (Customer familyMember : arrayFamilyMembers) {
            System.out.printf("%-4d %-15s %-15s %-20s %-15s $%,12.2f %10s\n", familyMember.getCustomerID(),
                    familyMember.getFirstName(),
                    familyMember.getLastName(),
                    familyMember.getEmail(), familyMember.getPhone(), familyMember.getBalance(),
                    (familyMember.getActive() == 1) ? "Active" : "Inactive");
        }
        return arrayFamilyMembers;
    }

    /**
     * Manage the family members operations (Add, Deactivate/Activate, View
     * Transactions) for the Guest User based on the User ID
     */
    private static void FamilyMembersManage() {

        boolean blnValid = false;
        char cChoice;
        ArrayList<Customer> arrayFamilyMembers = new ArrayList<Customer>();

        do {

            try {
                arrayFamilyMembers = displayFamilyMembers();// Call display Family Members method
                System.out.println("-".repeat(100));
                System.out.printf("%25s %12s %20s %6s \n", "[A] Add", "[D] Deact/Active", "[V] View Transactions",
                        "[B] Back");

                // Prompt the user for the choice
                System.out.print("> ");
                cChoice = System.console().readLine().charAt(0);

                switch (Character.toUpperCase(cChoice)) {

                    case 'A':
                        // Add Family Member
                        System.out.print("Please enter the id of the new family member: ");
                        int iNewFamilyMemberID = Integer.parseInt(System.console().readLine());

                        // Check if the family member is already in the list with a stream and lambda
                        // expression to check if the customerID is in the list
                        if (arrayFamilyMembers.stream().anyMatch(x -> x.getCustomerID() == iNewFamilyMemberID)) {
                            System.out.println("The family member is already in the list");
                            break;
                        }

                        // Call the method to get the customer details
                        Customer newFamilyMember = dbCustomer.getCustomer(iNewFamilyMemberID);

                        System.out.println("\nThe new family member is:");

                        System.out.printf("%-15s %-15s %-20s %-15s %-15s\n", "First Name", "Last Name",
                                "Email", "Phone", "Balance");
                        System.out.printf("%-15s %-15s %-20s %-15s $%,.2f\n",
                                newFamilyMember.getFirstName(),
                                newFamilyMember.getLastName(), newFamilyMember.getEmail(),
                                newFamilyMember.getPhone(), newFamilyMember.getBalance());

                        System.out.print("Is this the correct family member? Y/N: ");
                        if (Character.toLowerCase(System.console().readLine().charAt(0)) == 'y') {
                            // Call the script to add the family member
                            dbCustomer.updateParentId(iNewFamilyMemberID, gCustomer.getCustomerID());

                        }

                        break;
                    case 'D':

                        System.out.print("Enter ID of family member to deactivate or activate: ");
                        int iFamilyNumber = Integer.parseInt(System.console().readLine());

                        // Check if the family member is not in the list
                        if (!arrayFamilyMembers.stream().anyMatch(x -> x.getCustomerID() == iFamilyNumber)) {
                            System.out.println("The family member ID is not in the list");
                            break;
                        } else {
                            // Gettting the customer object from the array
                            Customer modifyCustomer = arrayFamilyMembers.stream()
                                    .filter(x -> x.getCustomerID() == iFamilyNumber).findFirst().get();
                            // Call the method to deactivate the family member
                            deactivateFamilyMember(modifyCustomer);

                        }

                        break;
                    case 'V':
                        viewFamilyTransactions();
                        break;
                    case 'B':
                        // clear the screen
                        System.out.flush();
                        blnValid = true;
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);

    }

    /**
     * Update the Guest User details based on the User ID
     */
    private static void updateCustomerDetails() {
        boolean blnValid = false;
        boolean blnConfirmSave = false;
        String strConfirm;
        String strTempDetail;
        int iChoice;
        Customer tempCustomer = gCustomer;

        do {
            try {
                printHeaders("GUEST USER DETAILS", 80);
                System.out.printf("%-15s %-15s %-25s %-15s\n", "First Name", "Last Name",
                        "Email", "Phone");
                System.out.println("-".repeat(80));
                System.out.printf("%-15s %-15s %-25s %-15s\n", "[1]" + gCustomer.getFirstName(),
                        "[2]" + gCustomer.getLastName(), "[3]" + gCustomer.getEmail(), "[4]" + gCustomer.getPhone());
                System.out.println("\n\t[5] Back\t\t[0] Exit\t\t\n\n");
                System.out.print("Please choose a detail to edit: ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1: // Enter new first name
                        System.out.print("Please enter new first name: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If
                                // input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.toUpperCase().equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempCustomer.setFirstName(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 2: // Enter last name
                        System.out.print("Please enter new last name: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempCustomer.setLastName(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 3: // Enter new email
                        System.out.print("Please enter new email: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempCustomer.setEmail(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 4: // Enter new phone
                        System.out.print("Please enter new phone: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) {
                                // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempCustomer.setPhone(Integer.parseInt(strTempDetail));
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 5:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
        dbCustomer.updateCustomerInfo(gUserID, gCustomer.getPhone(), gCustomer.getFirstName(), gCustomer.getLastName(),
                gCustomer.getEmail(), gCustomer.getActive());
    }

    /**
     * Deactivate or Activate a family member based on the User ID
     * 
     * @param customer - Customer object to deactivate or activate
     * 
     */
    private static void deactivateFamilyMember(Customer customer) {

        System.out.println("-".repeat(100));
        System.out.printf("\nThe family member you want to %s is:\n",
                (customer.getActive() == 1) ? "deactivate" : "activate");
        System.out.printf("\n%-15s %-15s %-20s %-15s $%,.2f\n", customer.getFirstName(),
                customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getBalance());
        System.out.println("\nAre you sure? Y/N: ");
        char cChoice = System.console().readLine().charAt(0);

        if (Character.toLowerCase(cChoice) == 'y') {
            // Call the script to deactivate the family member
            dbCustomer.updateCustomerInfo(customer.getCustomerID(), customer.getPhone(), customer.getFirstName(),
                    customer.getLastName(), customer.getEmail(), (customer.getActive() == 1) ? 0 : 1);

        }

    }

    /**
     * Display the transactions for the family members based on the User ID
     */
    private static void viewFamilyTransactions() {
        ArrayList<Transaction> arrayFamilyTrans = new ArrayList<Transaction>();

        boolean blnValid = false;

        do {
            try {
                arrayFamilyTrans = dbTransactions.getFamilyTransList(gCustomer.getCustomerID());

                System.out.printf("\n%-10s %-13s %-20s %-20s %-10s %-20s %-10s\n", "Trans ID", "ID",
                        "First Name", "Last Name", "Date", "Merchant", "Amount");
                System.out.println("-".repeat(100));
                // Loop through the result set and display the transactions
                for (Transaction transaction : arrayFamilyTrans) {

                    System.out.printf("%-10d %-13d %-20s %-20s %-10s %-20s %,.2f\n", transaction.getTransID(),
                            transaction.getCustomerID(), transaction.getCustomerFirstName(),
                            transaction.getCustomerLastName(), transaction.getDateTrans(),
                            transaction.getMerchantName(), transaction.getAmount());

                }

                System.out.println("-".repeat(100));
                blnValid = true;
            } catch (Exception e) {
                // Display error message
                System.out.println("Error getting family transactions: " + e);
            }
        } while (!blnValid);
    }

    /**
     * Display the settings for the Guest User based on the User ID
     * Manage the Guest User details (Update Details, Change Password, Back, Exit)
     */
    private static void displaySettings() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                printHeaders("SETTINGS", 80);

                System.out.printf("%-15s %-15s %-20s %-15s %-15s \n", "First Name", "Last Name", "Email", "Phone",
                        "Balance");
                System.out.println("-".repeat(80));
                System.out.printf("%-15s %-15s %-20s %-15s $%,9.2f \n\n", gCustomer.getFirstName(),
                        gCustomer.getLastName(), gCustomer.getEmail(), gCustomer.getPhone(), gCustomer.getBalance());

                System.out.print("[1] Update Details\t[2] Change Password\t[3] Back\t[0] Exit\n\n");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        updateCustomerDetails();
                        break;
                    case 2:
                        changePassword();
                        break;
                    case 3:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    /**
     * Change the password for the Guest User based on the User ID
     */
    private static void changePassword() {
        boolean blnValid = false;
        do {
            clearScreen();
            System.out.println("------------------------------");
            // Receive the new password
            System.out.print("Please enter new password: ");
            String strNewPassword = System.console().readLine();
            // Receive the confirm password
            System.out.print("Please confirm new password: ");
            String strConfirmPassword = System.console().readLine();
            // Compare the New and Confirm Password to be equal
            if (strNewPassword.equals(strConfirmPassword)) {
                // Call Customer Update Password script with new password
                if (DB_Costumer.updatePassword(gUserID, strConfirmPassword)) {
                    System.out.print("SUCCESS: Password updated\n");
                }
                blnValid = true;
            } else {
                System.out.print("ERROR: Passwords did not match");
            }
        } while (!blnValid);
    }

    // ********** MERCHANT USER INTERFACE **********

    /**
     * Display the main menu for the Merchant User based on the User ID
     */
    public static void displayMainMenuMerchantUser() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                //displayHeader();
                printHeaders("MERCHANT MAIN MENU", 40);
                System.out.println("[1] Accomplish Transaction");
                System.out.println("[2] View Transaction History");
                System.out.println("[3] Settings");
                System.out.println("[4] Back");
                System.out.println("[0] Exit");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        processTransaction();
                        break;
                    case 2:
                        displayViewMerchantTransactions();
                        break;
                    case 3:
                        displayMerchantUserSettings();
                        break;
                    case 4:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("MerchantError: Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    /**
     * Process the transaction for the Merchant User based on the User ID
     */
    private static void processTransaction() {

        ArrayList<Product> arrProductList = DB_Product.getProductsByMercant(gMerchantUser.getMerchantID());
        ArrayList<Product> arrTotalCart = new ArrayList<Product>();
        ArrayList<Integer> arrQuantity = new ArrayList<Integer>();
        boolean blnValid = false;
        boolean blnYesNoValid = false;
        int iChoice = -1;
        int iQuantity = 0;
        String strConfirm;
        
        do {
            // Display Product List
            displayHeader();
            printHeaders("PRODUCT LIST", 70);
            for (Product product : arrProductList) {
                System.out.printf("Product ID: %-4s Product: %-25s Price: $%6.2f\n", product.getProductID(), product.getName(), product.getPrice());
            }
            try {
                // Choose Product
                System.out.print("Choose Product: ");
                iChoice = Integer.parseInt(System.console().readLine());
                // Check if iChoice is in Product List
                int iIterator = 0;
                boolean blnInList = false;
                do {
                    if (iChoice == arrProductList.get(iIterator).getProductID()) {
                        blnInList = true;
                    }
                    iIterator++;
                } while ((!blnInList) || (iIterator < arrProductList.size()));
                // Input Quantity
                if (blnInList) {
                    System.out.print("Enter Quantity: ");
                    iQuantity = Integer.parseInt(System.console().readLine());
                    // Save into shoppingCart variable
                    arrTotalCart.add(DB_Product.getProduct(iChoice));
                    arrQuantity.add(iQuantity);
                    do {
                        // Ask if choose another or finish transaction
                        System.out.print("Choose another product Y/N?  ");
                        strConfirm = (System.console().readLine()).toUpperCase();
                        if (strConfirm.equals("Y")) {
                            // Loop again to present product list
                            blnYesNoValid = true;
                        } else if (strConfirm.equals("N")) {
                            blnValid = true;
                            blnYesNoValid = true;
                        } else {
                            System.out.println("Error: Please enter Y/N");
                        }
                    } while (!blnYesNoValid);
                } else {
                    System.out.println("Please select ProductID from Product List");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);

        checkoutShoppingCart(arrTotalCart, arrQuantity);
    }

    private static void checkoutShoppingCart(ArrayList<Product> arrTotalCart, ArrayList<Integer> arrQuantity) {
        String strConfirm;
        boolean blnYesNoValid = false;
        do {
            displayHeader();
            // Display Total Transaction and ask for Confirmation
            printHeaders("SHOPPING CART", 100);
            double dSubTotal = 0.0d;
            double dTotalAmount = 0.0d;
            for (int i = 0; i < arrTotalCart.size(); i++) {
                Product product = arrTotalCart.get(i);
                dSubTotal = (arrQuantity.get(i) * product.getPrice());
                System.out.printf("Product ID: %-4s Product: %-25s Price: $%6.2f Quantity: %-4s SubTotal: $%6.2f\n",
                        product.getProductID(),
                        product.getName(), product.getPrice(), arrQuantity.get(i), dSubTotal);
                dTotalAmount = dTotalAmount + dSubTotal;
            }
            System.out.println("-".repeat(100));
            System.out.print(" ".repeat(85));
            System.out.printf("TOTAL: $%6.2f\n", dTotalAmount);

            System.out.print("Confirm purchase Y/N?  ");
            strConfirm = (System.console().readLine()).toUpperCase();
            if (strConfirm.equals("Y")) {
                // Get Guest ID
                System.out.print("Please enter Guest ID: ");
                int iGuestID = Integer.parseInt(System.console().readLine());
                // Validate if GuestID exists in DB
                boolean blnCustomerExists = dbCustomer.checkIfCustomerExist(iGuestID);
                if (blnCustomerExists) { // If Customer exists
                    // Check if Customer has enough funds for purchase
                    Customer tempCustomer = dbCustomer.getCustomer(iGuestID);
                    if (tempCustomer.getBalance() >= dTotalAmount) {
                        // Commit purchase to Transaction and Detailed Transaction table
                        LocalDate txnDate = LocalDate.now();
                        DateTimeFormatter txnDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
                        String strTransactionDate = txnDate.format(txnDateFormat);
                        int iTransactionID = DB_Transactions.insertTransaction(iGuestID, strTransactionDate,
                                dTotalAmount,
                                gMerchantUser.getMerchantID());
                        // Go through the Shopping Cart to add each product to the detailed transaction
                        for (int i = 0; i < arrTotalCart.size(); i++) {
                            Product product = arrTotalCart.get(i);
                            System.out.printf("Product ID: %-4s Product: %-25s Price: $%6.2f\n", product.getProductID(),
                                    product.getName(), product.getPrice());
                            DB_Transactions.insertDetailTransaction(iTransactionID, product.getProductID(),
                                    product.getPrice(), arrQuantity.get(i));
                        }
                        // Deduct amount from Guest balance
                        double newBalance = tempCustomer.getBalance() - dTotalAmount;
                        if (DB_Costumer.updateBalance(iGuestID, newBalance)) {
                            System.out.println("SUCCESS: Transaction debited");
                        }
                        blnYesNoValid = true;
                    } else {
                        System.out.println("ERROR: Not enough funds");
                    }

                } else { // If customer does not exist
                    System.out.println("ERROR: Guest does not exist");
                }
                // Deduct total purchase from Guest debit
            } else if (strConfirm.equals("N")) {
                blnYesNoValid = true;
            } else {
                System.out.println("ERROR: Please enter Y/N");
            }
        } while (!blnYesNoValid);
    }
    private static void displayViewMerchantTransactions() {
        ArrayList<Transaction> arrayTransactions = new ArrayList<Transaction>();
        ArrayList<Det_Transaction> det_TransactionsList = new ArrayList<Det_Transaction>();
        
        boolean blnValid = false;

        do {
            try {
                clearScreen();
                displayHeader();
                printHeaders("MERCHANT TRANSACTIONS", 50);

                System.out.print("Please enter start date MM/DD/YYYY: ");
                String iStartDate = System.console().readLine();

                // Input validation start date RegEx
                while (!iStartDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                    System.out.println("Use the format MM/DD/YYYY ");
                    System.out.print("Please enter start date MM/DD/YYYY: ");
                    iStartDate = System.console().readLine();
                }

                String[] arrOfiStartDate = iStartDate.split("/", 3);
                String mysqlStartDate = arrOfiStartDate[2] + arrOfiStartDate[0] + arrOfiStartDate[1];

                // Input validation end date RegEx
                System.out.print("Please enter end date MM/DD/YYYY: ");
                String iEndDate = System.console().readLine();

                while (!iEndDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
                    System.out.println("Use the format MM/DD/YYYY ");
                    System.out.print("Please enter end date MM/DD/YYYY: ");
                    iEndDate = System.console().readLine();
                }
                // Validate end date after or same as start date
                while ((iEndDate.compareTo(iStartDate) < 0)) {
                    System.out.println("End date should be the same or after start date");
                    System.out.print("Please enter end date MM/DD/YYYY: ");
                    iEndDate = System.console().readLine();
                }

                // Modifying the string input for use it in a mysql script
                String[] arrOfiEndtDate = iEndDate.split("/", 3);
                String mysqlEndDate = arrOfiEndtDate[2] + arrOfiEndtDate[0] + arrOfiEndtDate[1];

                // Call script to get transactions given date
                arrayTransactions = DB_Transactions.getTransByMerchant(gMerchantUser.getMerchantID(), mysqlStartDate,
                        mysqlEndDate);
                Merchant tempMerchant = DB_Merchant.getMerchant(gMerchantUser.getMerchantID());
                System.out.println();
                System.out.println("-".repeat(70));
                System.out.println(
                        "Transactions for " + tempMerchant.getName() + " from " + iStartDate + " to " + iEndDate);
                System.out.println("-".repeat(70));

                // Loop through the result set and display the transactions
                for (Transaction transaction : arrayTransactions) {
                    System.out.printf("\n%-10s %-25s %-25s %-6s\n", "Date", "First Name", "Last Name", "Amount");
                    System.out.println("-".repeat(70));
                    System.out.printf("%-10s %-25s %-25s %-6.2f \n", transaction.getDateTrans(),
                            transaction.getCustomerFirstName(), transaction.getCustomerLastName(),
                            transaction.getAmount());
                    System.out.printf("\n%50s\n", "*********  Det Transaction *********");

                    det_TransactionsList = dbTransactions.getDetTransaction(transaction.getTransID());
                    for (Det_Transaction det_Transaction : det_TransactionsList) {
                        System.out.printf("Product: %-25s Price: %6.2f Quantity: %4.2f\n",
                                det_Transaction.getProduct_name(),
                                det_Transaction.getProduct_price(), det_Transaction.getQuantity());

                    }
                    System.out.println("*".repeat(70));
                    blnValid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.  " + e);
            }
        } while (!blnValid);
    }
    
    private static void displayMerchantUserSettings() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            clearScreen();
            displayHeader();
            try {
                String format = "%-15s %-15s %-20s %-15s %-15s \n";
                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf(format, "First Name", "Last Name", "Email", "Phone", "Role");
                System.out.printf(format + "\n\n", gMerchantUser.getFirstName(),
                        gMerchantUser.getLastName(), gMerchantUser.getEmail(),gMerchantUser.getPhone(), gMerchantUser.getRole());

                System.out.print("[1] Update Details\t[2] Change Password\t[3] Back\t[0] Exit\n\n");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        updateMerchantUserDetails();
                        break;
                    case 2:
                        changeMerchantPassword();
                        break;
                    case 3:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    private static void updateMerchantUserDetails() {
        boolean blnValid = false;
        boolean blnConfirmSave = false;
        String strConfirm;
        String strTempDetail;
        int iChoice;
        Merchant_User tempMerchant_User = gMerchantUser;

        do {
            try {
                System.out.println("");
                System.out.println("-".repeat(82));
                System.out.println("-".repeat(31) + " Guest User Details " + "-".repeat(31));
                System.out.println("-".repeat(82));
                System.out.printf("%-15s %-15s %-25s %-15s %-15s\n", "First Name", "Last Name", "Email", "Phone", "Role");
                System.out.println("-".repeat(82));
                System.out.printf("%-15s %-15s %-25s %-15s %-15s\n", "[1]" + tempMerchant_User.getFirstName(),
                        "[2]" + tempMerchant_User.getLastName(), "[3]" + tempMerchant_User.getEmail(), "[4]" + tempMerchant_User.getPhone(), "[5]" + tempMerchant_User.getRole());
                System.out.println("\n\t[6] Back\t\t[0] Exit\t\t\n\n");
                System.out.print("Please choose a detail to edit: ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1: // Enter new first name
                        System.out.print("Please enter new first name: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If
                                // input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.toUpperCase().equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempMerchant_User.setFirstName(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 2: // Enter last name
                        System.out.print("Please enter new last name: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempMerchant_User.setLastName(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 3: // Enter new email
                        System.out.print("Please enter new email: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempMerchant_User.setEmail(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 4: // Enter new phone
                        System.out.print("Please enter new phone: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) {
                                // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempMerchant_User.setPhone(Integer.parseInt(strTempDetail));
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 5: // Enter new role
                        System.out.print("Please enter new role: ");
                        strTempDetail = System.console().readLine();
                        // Confirm with user
                        do {
                            System.out.print("Save Y/N?: ");
                            strConfirm = (System.console().readLine()).toUpperCase();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) {
                                // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
                                blnConfirmSave = true;
                                tempMerchant_User.setRole(strTempDetail);
                            } else { // If user chose not to save
                                blnConfirmSave = true;
                            }
                        } while (!blnConfirmSave);
                        break;
                    case 6:
                        blnValid = true;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
        DB_Merchant_Users.updateMerchantUserInfo(gUserID, tempMerchant_User.getFirstName(), tempMerchant_User.getLastName(), tempMerchant_User.getEmail(), tempMerchant_User.getPhone(), tempMerchant_User.getRole());
    }
    
    private static void changeMerchantPassword() {
        boolean blnValid = false;
        do {
            //clearScreen();
            System.out.println("------------------------------");
            // Receive the new password
            System.out.print("Please enter new password: ");
            String strNewPassword = System.console().readLine();
            // Receive the confirm password
            //System.out.print("Please confirm new password: ");
            //String strConfirmPassword = System.console().readLine();
            Console console = System.console();
            char[] arrConfirmPassword = console.readPassword("Please confirm new password: ");
            String strConfirmPassword = new String(arrConfirmPassword);
            // Compare the New and Confirm Password to be equal
            if (strNewPassword.equals(strConfirmPassword)) {
                // Call Merchant User Update script with new password
                if (DB_Merchant_Users.updatePassword(gMerchantUser.getMerchantUserID(), strNewPassword)) {
                    System.out.print("SUCCESS: Password updated\n");
                }
                blnValid = true;
            } else {
                System.out.print("ERROR: Passwords did not match");
            }
        } while (!blnValid);
    }
    // ******** ADMINISTRATOR USER INTERFACE **********

    /**
     * Display the main menu for the Admin User for managing the Guest Users
     */
    public static void displayViewPrimaryGuestDetails() {

        boolean blnValid = false;
        int iChoice = -1;

        do {
            try {
                printHeaders("MANAGE GUEST USERS", 40);
                System.out.println("[1] Create Guest User");
                System.out.println("[2] View Transaction History");
                System.out.println("[3] Load Funds");
                System.out.println("[4] View/Update Details");
                System.out.println("[5] View Family Members");
                System.out.println("[6] Back");
                System.out.println("[0] Exit");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        displayCreateGuestUser();
                        break;
                    case 2:
                        displayViewTransactions();
                        break;
                    case 3:
                        loadGuestDetails();
                        displayLoadFunds();
                        break;
                    case 4:
                        loadGuestDetails();
                        updateCustomerDetails();
                        break;
                    case 5:
                        loadGuestDetails();
                        displayFamilyMembers();
                        break;
                    case 6:
                        blnValid = true;
                        break;
                    default:
                        break;
                }

            } catch (Exception e) {

                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValid);
    }

    /**
     * Method to create a new Guest User
     */
    private static void displayCreateGuestUser() {

        boolean blnValid = false;
        printHeaders("CREATE GUEST USER", 50);
        System.out.println("Enter the following details to create a new user");
        System.out.print("First Name: ");
        String strFirstName = System.console().readLine();
        System.out.print("Last Name: ");
        String strLastName = System.console().readLine();
        System.out.print("Email: ");
        String strEmail = System.console().readLine();
        System.out.print("Phone: ");
        int iPhone = Integer.parseInt(System.console().readLine());
        System.out.print("Password: ");
        String strPassword = System.console().readLine();

        // Display the details entered
        System.out.println("-".repeat(80));
        System.out.printf("%-15s %-15s %-20s %-15s\n", "First Name", "Last Name", "Email", "Phone");
        System.out.printf("%-15s %-15s %-20s %-15s\n", strFirstName, strLastName, strEmail, iPhone);
        System.out.println("-".repeat(80));
        System.out.print("Is this information correct? Y/N: ");
        char cChoice = System.console().readLine().charAt(0);

        if (Character.toLowerCase(cChoice) == 'y') {
            // Call the script to create the new user
            Customer newCustomer = new Customer(strFirstName, strLastName, strEmail, iPhone, strPassword, 1);

            try {
                dbCustomer.addCustomer(newCustomer);
                System.out.println("New user created successfully");
            } catch (Exception e) {
                System.out.println("Error creating new user: " + e);
            }

        }
        blnValid = true;

    }

}
