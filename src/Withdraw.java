/**The Withdraw Class creates Withdraw Objects as well as
 * contains the implementation to call the withdraw()
 * function in the Transaction Class which reduces the amount
 * of money in a User's Cash Account.
 * Created by Owen on 3/13/2016.
 */
public class Withdraw implements Order {
    private Transaction abcStock;

    /**
     * Constructor for Withdraw Object.
     * @param abcStock - Transaction Object being handled.
     */
    public Withdraw(Transaction abcStock){
        this.abcStock = abcStock;
    }

    /**
     * The execute function calls the withdraw function in
     * the Transaction Class which reduces the amount of
     * money in a User's Cash Account.
     */
    public void execute() {
        abcStock.withdraw();
    }

    public void undo() {
        abcStock.deposit();
    }
    public Transaction getTransaction(){
        return abcStock;
    }
}