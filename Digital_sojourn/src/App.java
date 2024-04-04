
import java.util.ArrayList;

import com.mysql.cj.exceptions.ExceptionFactory;

import Models.Admin_User;
import Models.Customer;
import Models.Det_Transaction;
import Models.Merchant;
import Models.Merchant_User;
import Models.Transaction;
import db_services.DB_Admin_Users;
import db_services.DB_Costumer;
import db_services.DB_Merchant_Users;
import db_services.DB_Merchant;
import db_services.DB_Product;
import db_services.DB_Transactions;

public class App {
    private static int gUserType;
    private static int gUserID;

    private static Customer gCustomer;
    private static Merchant gMerchant;
    private static Merchant_User gMerchantUser;
    private static Admin_User gAdmin;

    private static DB_Admin_Users dbAdminUser;
    private static DB_Costumer dbCustomer;
    private static DB_Merchant_Users dbMerchantUser;

    public static void main(String[] args) throws Exception {

        dbCustomer = new DB_Costumer();
        dbAdminUser = new DB_Admin_Users();
        dbMerchantUser = new DB_Merchant_Users();

        displayLoginMenu();
    }

    private static void displayHeader() {
        System.out.println("____/--------------------\\___");
        System.out.println("|                            |");
        System.out.println("|       DIGITAL SOJOURN      |");
        System.out.println("|                            |");
        System.out.println("----\\--------------------/----");
    }

    public static void displayLoginMenu() {
        boolean blnValidInput = false;
        boolean blnVerifiedPassword = false;
        do {
            try { // Determine type of User
                displayHeader();
                System.out.println("");
                System.out.println("-------------------------------");
                System.out.println("--------     LOGIN     --------");
                System.out.println("-------------------------------");
                System.out.println("[1] Guest");
                System.out.println("[2] Admin");
                System.out.println("[3] Merchant");
                System.out.println("[0] Exit");
                System.out.print("Please enter your choice: ");

                gUserType = Integer.parseInt(System.console().readLine());
                if (gUserType == 0) {
                    blnValidInput = true;
                } else if ((gUserType > 0) && (gUserType < 4)) {
                    blnValidInput = true;
                    blnVerifiedPassword = verifyPassword();
                } else {
                    System.out.println("Invalid input. Select from choices.\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while (!blnValidInput);

        if (blnVerifiedPassword) {
            switch (gUserType) {
                case 1: // Guest
                    displayMainMenuGuest();
                    break;
                case 2: // Admin

                    break;
                case 3: // Merchant

                    break;
                default:
                    break;
            }
        }
    }

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
                if (strPassword.equals("X")) {
                    gUserType = 0;
                }
                ;

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
                        ;
                        break;
                    case 2: // Login Admin
                        // Call the GetPassword script
                        dbPassword = dbAdminUser.getAdminPassword(gUserID);
                        if (strPassword.equals(dbPassword)) {
                            blnValidInput = true;
                            blnVerifiedPassword = true;
                        } else { // Invalid Password
                            System.out.println("Invalid Password");
                        }
                        break;
                    case 3: // Login Merchant
                        // Call the GetPassword script
                        dbPassword = dbMerchantUser.getMerchantUserPassword(gUserID);
                        if (strPassword.equals(dbPassword)) {
                            blnValidInput = true;
                            blnVerifiedPassword = true;
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

    public static void displayMainMenuGuest() {
        boolean blnValid = false;
        int iChoice = -1;

        displayHeader();
        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");
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
                        displayFamilyMembers();
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

    private static void viewBalance() {
        System.out.println("");
        System.out.println("-------------------------------");
        System.out.println("-------  VIEW BALANCE  --------");
        System.out.println("-------------------------------");

        System.out.printf("First Name: %s\n", gCustomer.getFirstName());
        System.out.printf("Last Name: %s\n", gCustomer.getLastName());
        System.out.printf("Current Balance: $%,.2f\n\n", gCustomer.getBalance());
    }

    public static void displayMainMenuAdminUser() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");
                System.out.println("[1] Search Primary Guest");
                System.out.println("[2] Settings");
                System.out.println("[3] Back");
                System.out.println("[0] Exit");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        displayLoadFunds();
                        break;
                    case 2:
                        displayViewTransactions();
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

    public static void displayMainMenuMerchantUser() {
        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");
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
                        displayLoadFunds();
                        break;
                    case 2:
                        displayViewTransactions();
                        break;
                    case 3:
                        displayViewTransactions();
                        break;
                    case 4:
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

    private static void displayLoadFunds() {
        boolean blnValid = false;

        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");

                if (gUserType == 2) { // if User Type is Admin
                    System.out.print("Please enter Primary User ID: ");
                    gUserID = Integer.parseInt(System.console().readLine());
                    gCustomer = DB_Costumer.getCustomer(gUserID);
                }

                System.out.print("Please enter amount to load: $");
                double iAmount = Double.parseDouble(System.console().readLine());

                // Call Script: Load Funds
                double newBalance = gCustomer.getBalance() + iAmount;
                String updateBalanceResult = DB_Costumer.updateBalance(gUserID, newBalance);
                if (updateBalanceResult == "success") {
                    System.out.printf("$%,.2f has been added to %d %s\n", iAmount, gUserID,
                            gCustomer.getFirstName() + " " + gCustomer.getLastName());
                    System.out.printf("The new balance is $%,.2f \n\n", DB_Costumer.getCustomerBalance(gUserID));
                    blnValid = true;
                } else {
                    System.out.println(updateBalanceResult + "\n");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please ");
            }
        } while (!blnValid);

    }

    private static void displayViewTransactions() {
        DB_Transactions dbTransactions = new DB_Transactions();
        ArrayList<Transaction> arrayTransactions = new ArrayList<Transaction>();
        ArrayList<Det_Transaction> det_TransactionsList = new ArrayList<Det_Transaction>();

        boolean blnValid = false;
        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");

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
                // Valite end date after or same as start date
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
                arrayTransactions = dbTransactions.getTransByCustomer(gUserID,
                        mysqlStartDate, mysqlEndDate);

                // Loop through the result set and display the transactions

                for (Transaction transaction : arrayTransactions) {
                    System.out.printf("\n%-10s %-15s %-6s\n", "Date", "Merchant", "Amount");
                    System.out.println("-".repeat(40));
                    System.out.printf("%-10s %-25s %-6.2f \n", transaction.getDateTrans(),
                            transaction.getMerchantName(), transaction.getAmount());
                    System.out.println("\n*********  Det Transaction *********");

                    det_TransactionsList = dbTransactions.getDetTransaction(transaction.getTransID());
                    for (Det_Transaction det_Transaction : det_TransactionsList) {
                        System.out.printf("Product: %-25s Price: %6.2f Quantity: %4.2f\n",
                                det_Transaction.getProduct_name(),
                                det_Transaction.getProduct_price(), det_Transaction.getQuantity());

                    }
                    System.out.println("*".repeat(40));
                    blnValid = true;
                }
            } catch (Exception e) {
                System.out.println("Invalid input.  " + e);
            }
        } while (!blnValid);
    }

    private static void displayFamilyMembers() {

        boolean blnValid = false;
        char cChoice;
        // TODO: Re think the logic of this method, just add family member with customer
        // add or remove family member with customer id

        do {

            // Call Family Member script
            ArrayList<Customer> arrayFamilyMembers = new ArrayList<Customer>();
            arrayFamilyMembers = DB_Costumer.getFamilyMembers(gCustomer.getCustomerID());
            int index = 1;

            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("%-4s %-15s %-15s %-20s %-15s %-15s\n", "#", "First Name", "Last Name", "Email", "Phone",
                    "Balance");
            System.out.println("----------------------------------------------------------------------------------");
            // Loop through the result set and display the family members
            for (Customer familyMember : arrayFamilyMembers) {
                System.out.printf("%-4d %-15s %-15s %-20s %-15s %-15s\n", index, familyMember.getFirstName(),
                        familyMember.getLastName(),
                        familyMember.getEmail(), familyMember.getPhone(), familyMember.getBalance());
                index++;
            }

            try {
                System.out
                        .println("----------------------------------------------------------------------------------");
                System.out.print("[A] Add\t[U] Update\t[V] View Transactions\t[D] Deactivate\t[B] Back\t[E] Exit\n");

                System.out.print("> ");
                cChoice = System.console().readLine().charAt(0);

                switch (cChoice) {
                    case 'E':
                        System.exit(0);
                        break;
                    case 'A':
                        // TODO: WORKING HERE!!!
                        System.out.print("Please enter the id of the new family member:");
                        int iNewFamilyMemberID = Integer.parseInt(System.console().readLine());
                        // Call the script to get the customer details
                        Customer newFamilyMember = DB_Costumer.getCustomer(iNewFamilyMemberID);
                        System.out.println("The new family member is: " + newFamilyMember.getFirstName() + " " +
                                newFamilyMember.getLastName() + " " + newFamilyMember.getEmail() + " " +
                                newFamilyMember.getPhone() + " " + newFamilyMember.getBalance() + "\n");

                        System.out.println("Is this the correct family member? Y/N");
                        if (System.console().readLine().charAt(0) == 'Y'
                                || System.console().readLine().charAt(0) == 'y') {
                            // Call the script to update the family member's parent_ID field in the customer
                            // table
                            DB_Costumer.updateParentId(iNewFamilyMemberID, gCustomer.getCustomerID());
                        } else {
                            System.out.println("Family member not added");
                        }

                        break;

                    // TODO: ELIMINATE THIS CASE
                    case 'U':
                        updateFamilyMember();
                        break;

                    case 'V':
                        viewFamilyTransactions();
                        break;
                    case 'D':
                        System.out.print("Enter number of family member to deactivate: ");
                        int iCustomerID = Integer.parseInt(System.console().readLine());
                        deactivateFamilyMember();
                        break;
                    case 'B':
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

    private static void displaySettings() {
        // Call Customer Details script

        // Display the details

        boolean blnValid = false;
        int iChoice = -1;
        do {
            try {
                String format = "%-15s %-15s %-20s %-15s %-15s \n";
                System.out.println("-------------------------------------------------------------------------------");
                System.out.printf(format, "First Name", "Last Name", "Email", "Phone", "Balance");
                System.out.printf(format + "\n\n", gCustomer.getFirstName(),
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

    private static void updateCustomerDetails() {
        // Call Customer Details script
        boolean blnValid = false;
        boolean blnConfirmSave = false;
        String strConfirm;
        String strTempDetail;
        int iChoice;
        Customer tempCustomer = gCustomer;
        do {
            try {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("First Name\t\tLast Name\t\tEmail\t\tPhone");
                System.out.printf("[1] %s\t\t[2] %s\t\t[3] %s\t\t[4] %d\n", gCustomer.getFirstName(),
                        gCustomer.getLastName(), gCustomer.getEmail(), gCustomer.getPhone());
                System.out.println("[5] Back\t\t[0] Exit\t\t\n\n");
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
                            strConfirm = System.console().readLine();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
                                System.out.println("Please enter Y/N");
                            } else if (strConfirm.equals("Y")) { // Save to the Customer object
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
                            strConfirm = System.console().readLine();
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
                            strConfirm = System.console().readLine();
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
                            strConfirm = System.console().readLine();
                            if ((!strConfirm.equals("Y")) && (!strConfirm.equals("N"))) { // If incorrect input
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
                gCustomer.getEmail());
    }

    private static void changePassword() {
        System.out.println("------------------------------");
        // Receive the new password
        System.out.print("Please enter Primary User ID: ");
        String strNewPassword = System.console().readLine();
        // Receive the confirm password
        System.out.print("Please enter Primary User ID: ");
        String strConfirmPassword = System.console().readLine();
        // Compare the New and Confirm Password to be equal
        if (strNewPassword.equals(strConfirmPassword)) {
            // Call Customer Update script with new password
        }
    }

    private static void deactivateFamilyMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deactivateFamilyMember'");
    }

    private static void viewFamilyTransactions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewFamilyTransactions'");
    }

    private static void updateFamilyMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'viewFamilyMembers'");
    }

    // TODO: This method is unnecessary id the same functionality of addCustomer
    // method

    // private static void addFamilyMember() {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method " +
    // "'addFamilyMember'");
    // }

    private static void processTransaction() {
    }
}
