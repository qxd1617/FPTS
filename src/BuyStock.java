/**The BuyStock Class creates BuyStock Objects and contains
 * the implementation to call the increase() function in the
 * Transaction Class that contains the implementation to
 * increase the amount owned by a User of a certain Stock as
 * well as reduce the amount of money a User owns.
 * Created by Owen on 3/4/2016.
 */
public class BuyStock implements Order {
    private Transaction abcStock;

    /**
     * Constructor for BuyStock Object.
     * @param abcStock - The Transaction Object being handled.
     */
    public BuyStock(Transaction abcStock){
        this.abcStock = abcStock;
    }

    /**
     * The execute function calls the increase function in the
     * Transaction Class to handle the manipulation of variables
     * related to buying a Stock.
     */
    public void execute() {
        abcStock.buyEq();
    }

    public void undo() { abcStock.sellEq(); }

    public Transaction getTransaction(){
        return abcStock;
    }
}