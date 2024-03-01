import Models.Customer;
import db_services.DB_Costumer;
import db_services.DB_Merchant;
import db_services.DB_Product;
import db_services.DB_Transactions;

private class ResortApp {
    private static int gUserType;
    private static int gUserID;
    private gCustomer Customer;

    private dbCustomer = new DB_Costumer();


    public ResortApp() {
        
    }

    private static void displayHeader() {
        System.out.println("____/--------------------\\___");
        System.out.println("|                            |");
        System.out.println("|       DIGITAL SOJOURN      |");
        System.out.println("|                            |");
        System.out.println("----\\--------------------/----");
    }

    public void displayMainMenu() {
        boolean blnValid = false;
        do {
            try {
                displayHeader();
                System.out.println("");
                System.out.println("------------------------------");
                System.out.println("[1] Guest");
                System.out.println("[2] Merchant");
                System.out.println("[3] Admin");
                System.out.println("[0] Exit");
                System.out.print("Please input User type or 0 to Exit: ");
                
                gUserType = Integer.parseInt(System.console().readLine());
                switch (gUserType) {
                    case 0:
                        blnValid = true;
                        break;
                    case 1:
                        displayMainMenuPrimaryUser();
                        break;
                    case 2:
                        displayMainMenuAdminUser();
                        break;
                    case 3:
                        displayMainMenuMerchantUser();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Numbers only please.");
            }
        } while(!blnValid);
        System.exit(0);
    }

    public void displayLoginMenu() {
        System.out.println("------------------------------");
        System.out.println("Please enter ID Number: ");
        gUserID = Integer.parseInt(System.console().readLine());
        System.out.println("Please enter Password: ");
        String strPassword = System.console().readLine();

        gCustomer = new Customer();
        gCustomer = dbCustomer.GetCustomer(gUserID);
        System.out.println(prueba.toString());
        // System.out.println(prueba.getFirstName());
    }

    public static void displayMainMenuPrimaryUser() {
        boolean blnValid = false;
        int iChoice = -1;

        displayHeader();
        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");
                System.out.println("[1] Load Funds");
                System.out.println("[2] View Transactions");
                System.out.println("[3] Family Members");
                System.out.println("[4] Settings");
                System.out.println("[5] Back");
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
                        displayFamilyMembers();
                        break;
                    case 4:
                        displaySettings();
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
                }
                System.out.print("Please enter amount to load: $");
                int iAmount = Integer.parseInt(System.console().readLine());
                
                // Call Script: Load Funds
                System.out.printf("$%d has been added to %d\n\n", iAmount, gUserID);
                blnValid = true;
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        } while(!blnValid);
    }

    private static void displayViewTransactions() {
        boolean blnValid = false;

        do {
            try {
                System.out.println("");
                System.out.println("------------------------------");
                System.out.print("Please enter start date MM/DD/YYYY: ");
                String startDate = System.console().readLine();
                // validate startDate
                System.out.print("Please enter start date MM/DD/YYYY: ");
                String endDate = System.console().readLine();
                // validate endDate

                // Call script to get transactions given date

                // Loop through the result set

                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Date\t\tMerchant\t\tProduct\t\t\tAmount\t\t");
                blnValid = true;
            } catch (Exception e) {
                System.out.println("Invalid input.");
            }
        } while(!blnValid);
    }

    private static void displayFamilyMembers() {

        // Call Family Member script
        
        // Loop through the result set and display the family members

        boolean blnValid = false;
        char cChoice;
        do {
            try {

                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Name\t\tEmail\t\tPhone\t\t\tBalance\t\t\n\n");

                System.out.print("[A] Add\t[U] Update\t[V] View Transactions\t[D] Deactivate\t[B] Back\t[E] Exit\n");
                
                System.out.print("> ");
                cChoice = System.console().readLine().charAt(0);
                
                System.out.print("Enter number of family member to deactivate: ");
                int iCustomerID = Integer.parseInt(System.console().readLine());
                switch (cChoice) {
                    case 'E':
                        System.exit(0);
                        break;
                    case 'A':
                        addFamilyMember();
                        break;
                    case 'U':
                        updateFamilyMember();
                        break;
                    case 'V':
                        viewFamilyTransactions();
                        break;
                    case 'D':
                        
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
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("Name\t\tEmail\t\tPhone\t\t\tBalance\t\t\n\n");

                System.out.print("[1] Update Details\t[2] Change Password\t[3] Back\t[0] Exit\n");
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
        int iChoice = -1;
        do {
            try {
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println("[1] Name\t\t[2] Email\t\t[3] Phone\t\t\t[4] Balance\t\t\n\n");
                System.out.println("[5] Back\t\t[0] Exit\t\t\n\n");
                System.out.print("> ");

                iChoice = Integer.parseInt(System.console().readLine());
                switch (iChoice) {
                    case 0:
                        System.exit(0);
                        break;
                    case 1:
                        // Enter new name 
                        // Confirm with user
                        // Save to the Customer object
                        break;
                    case 2:
                        // Enter new email 
                        // Confirm with user
                        // Save to the Customer object
                        break;
                    case 3:
                        // Enter new phone 
                        // Confirm with user
                        // Save to the Customer object
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

    private static void addFamilyMember() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addFamilyMember'");
    }
}
