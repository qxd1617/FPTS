/**The Deposit Class creates Deposit Objects as well as
 * contains the implementation to call the deposit()
 * function in the Transaction Class which increases the
 * amount of money in a User's Cash Account.
 * Created by Owen on 3/13/2016.
 */
public class Deposit implements Order {
    private Transaction abcStock;

    /**
     * Constructor for Deposit Object.
     * @param abcStock - Transaction Object being handled.
     */
    public Deposit(Transaction abcStock){
        this.abcStock = abcStock;
    }

    /**
     * The execute function calls the deposit function in
     * the Transaction Class which increases the amount of
     * money in a User's Cash Account.
     */
    public void execute() {
        abcStock.deposit();
    }

    public void undo(){ abcStock.withdraw();
    }

    public Transaction getTransaction(){
        return abcStock;
    }
}
