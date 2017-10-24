/**The SellStock Class creates SellStock Objects and contains
 * the implementation to call the decrease() function
 * in the Transaction Class that contains the implementation
 * to reduce the amount owned by a User of a certain Stock as
 * well as increase the amount of money a User owns.
 * Created by Owen on 3/4/2016.
 */
public class SellStock implements Order {
    private Transaction abcStock;

    /**
     * Constructor for SellStock Objects.
     * @param abcStock - The Transaction Object being handled.
     */
    public SellStock(Transaction abcStock){
        this.abcStock = abcStock;
    }

    /**
     * The execute function calls the decrease function in the
     * Transaction Class to handle the manipulation of variables
     * related to selling a Stock.
     */
    public void execute() {
        abcStock.sellEq();
    }

    public void undo() {abcStock.buyEq();}

    public Transaction getTransaction(){
        return abcStock;
    }
}