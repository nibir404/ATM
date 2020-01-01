import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String first_name;
    private String last_name;
    private String uuid;
    private byte pinHash[];

    private ArrayList<Account>accounts;

    public User(String first_name,String last_name,String pin,Bank theBank){
        this.first_name=first_name;
        this.last_name=last_name;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error , caught no such algorithm exception");
            e.printStackTrace();
            System.exit(1);
        }

        this.uuid = theBank.getNewUserUUID();
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created.\n", last_name, first_name, this.uuid);
    }

    public void addAccount(Account anAcc){
        this.accounts.add(anAcc);
    }

    public String getUUID(){
        return this.uuid;
    }

    public boolean validatepin(String aPin){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(aPin.getBytes());
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error , caught no such algorithm exception");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstname(){
        return this.first_name;
    }

    public void printAccountSummery(){
        System.out.println("\n\n%s's accounts summery "+this.first_name);
        for (int i=0; i<this.accounts.size();i++){
            System.out.println("%d- %s\n"+i+1+this.accounts.get(i).getSummerryLine());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIDx){
        return this.accounts.get(acctIDx).getBalance();
    }

    public String getAcctUUID(int acctIDx){
        return this.accounts.get(acctIDx).getUUID();
    }

    public void addActTransaction(int acctIDx , double amount , String memo){
        this.accounts.get(acctIDx).addTransaction(amount,memo);
    }
}

