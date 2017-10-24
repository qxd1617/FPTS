
/**
 * The CashAccount represents a User's bank account
 * The User can add and remove funds from the account.
 * Created by Matthew on 3/3/2016.
 */
public class CashAccount implements java.io.Serializable,Holdings{
    String name;
    float amount;

    /**
     * Constructor for the CashAccount
     * @param name - Name of the account
     * @param balance - Initial balance of the account
     */
    CashAccount(String name, float balance){
        this.name = name;
        this.amount = Math.round(balance*100f)/100f;
    }

    /**
     * Withdraws a specified amount from the account
     * @param value - Value to remove from the account
     */
    void withdraw(float value){
        if((this.amount - value) > 0) {
            this.amount -= value;
        }
    }

    /**
     * Deposits a specified amount from the account
     * @param value - Value to add to the account
     */
    void deposit(float value){
        this.amount += value;
    }

    public float getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "has a balance of: " + amount;

    }
}
