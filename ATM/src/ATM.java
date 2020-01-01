import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank theBank = new Bank("BANGLADESH BANK");

        User aUser = theBank.addUser("Nibir", "Imtiaz", "abcd");
        Account newAccount = new Account("Checking", aUser, theBank);

        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User curUser;
        while (true) {

            curUser = ATM.mainMenuPrompt(theBank, sc);
            ATM.printUserMenu(curUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc){
        String userID;
        String pin;
        User authUser;

        do {

            System.out.println("\n\n Welcome to Bangladesh Bank\n\n"+theBank.getName());
            System.out.println("Enter user ID: ");
            userID = sc.nextLine();
            System.out.println("Enter pin: ");
            pin = sc.nextLine();

            authUser = theBank.userLogin(userID,pin);
            if (authUser==null){
                System.out.println("Incorrect user Id/pin combination "+"Please try again");
            }
        } while (authUser == null);
        return authUser;
    }

    public static void printUserMenu(User theUser , Scanner sc){
        theUser.printAccountSummery();

        int choice;

        do {
            System.out.println("Welcome %s, what would you like to do"+theUser.getFirstname());
            System.out.println("1- show account transaction history");
            System.out.println("2- withdraw");
            System.out.println("3- deposit");
            System.out.println("4- transfer");
            System.out.println("5- Quit");
            System.out.println();
            System.out.println("Enter choice : ");
            choice = sc.nextInt();

            if (choice<1 || choice>5){
                System.out.println("Invalid choice.....");
            }
        }while (choice<1||choice>5);

        switch (choice){
            case 1:
                ATM.showTransactionHistory(theUser,sc);
            case 2:
                ATM.withdrawFunds(theUser , sc);
            case 3:
                ATM.depositFunds(theUser,sc);
            case 4:
                ATM.transferFunds(theUser,sc);
                break;
        }

        if (choice !=5){
            ATM.printUserMenu(theUser,sc);
        }
    }
    public  static void showTransactionHistory(User theUser, Scanner sc){
        int theAcct;
        do {
            System.out.println("Enter the number (1-%d) of the account\n"+"Whose transaction you want to see : "+theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct <0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account . please try again!!!");
            }
        } while (theAcct <0 || theAcct >= theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);
    }

    public static void transferFunds(User theUser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.println("Enter the number (1-%d) of the account\n"+"to transfer from : ");
            fromAcct = sc.nextInt()-1;
            if (fromAcct <0 || fromAcct>= theUser.numAccounts()){
                System.out.println("Invalid account . please try again!!!");
            }
        }while ( fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter the number (1-%d) of the account\n"+"to transfer to : ");
           toAcct = sc.nextInt()-1;
            if (toAcct <0 || toAcct>= theUser.numAccounts()){
                System.out.println("Invalid account . please try again!!!");
            }
        }while ( toAcct <0 || toAcct >= theUser.numAccounts());

        do {
            System.out.println("Enter the amount to transfer (max $%.02f) : $"+acctBal);
            amount = sc.nextDouble();
            if (amount <0 ){
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal){
                System.out.println("Amount must not be greater than\n"+"balance of $%.o2f.\n"+acctBal);

            }
        }while (amount<0 || amount>acctBal);

        theUser.addActTransaction(fromAcct, -1*amount, String.format("Transfer to account %s "+theUser.getAcctUUID(toAcct)));
        theUser.addActTransaction(toAcct, amount, String.format("Transfer to account %s "+theUser.getAcctUUID(fromAcct)));
    }

    public static void withdrawFunds(User theUser, Scanner sc){
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.println("Enter the number (1-%d) of the account\n"+"to transfer from : ");
            fromAcct = sc.nextInt()-1;
            if (fromAcct <0 || fromAcct>= theUser.numAccounts()){
                System.out.println("Invalid account . please try again!!!");
            }
        }while ( fromAcct <0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter the amount to transfer (max $%.02f) : $"+acctBal);
            amount = sc.nextDouble();
            if (amount <0 ){
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal){
                System.out.println("Amount must not be greater than\n"+"balance of $%.o2f.\n"+acctBal);

            }
        }while (amount<0 || amount>acctBal);

        sc.nextLine();

        System.out.println("Enter a memo : ");
        memo = sc.nextLine();

        theUser.addActTransaction(fromAcct,-1*amount,memo);
    }

    public static void depositFunds(User theUser,Scanner sc){
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        do {
            System.out.println("Enter the number (1-%d) of the account\n"+"to transfer from : ");
            toAcct = sc.nextInt()-1;
            if (toAcct <0 || toAcct>= theUser.numAccounts()){
                System.out.println("Invalid account . please try again!!!");
            }
        }while ( toAcct <0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        do {
            System.out.println("Enter the amount to transfer (max $%.02f) : $"+acctBal);
            amount = sc.nextDouble();
            if (amount <0 ){
                System.out.println("Amount must be greater than zero");
            } else if (amount > acctBal){
                System.out.println("Amount must not be greater than\n"+"balance of $%.o2f.\n"+acctBal);

            }
        }while (amount<0 || amount>acctBal);

        sc.nextLine();

        System.out.println("Enter a memo : ");
        memo = sc.nextLine();

        theUser.addActTransaction(toAcct,amount,memo);
    }
}
