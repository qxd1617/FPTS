import java.util.Date;

/**The OrderLog Class creates OrderLog objects that are then stored
 * as a history of Orders in the Portfolio Class.
 * Created by Matt on 3/12/2016.
 */
public class OrderLog implements java.io.Serializable{
    public Transaction transaction;
    public Date date;

    /**
     * Constructor of OrderLog Objects.
     * //@param name - Name of the Equity/Cash Account.
     * @param date - Date the Order took place.
     * //@param cost - Cost of the Order.
     */
    public OrderLog(Transaction transaction, Date date){
        this.transaction = transaction;
        this.date = date;
    }
}
