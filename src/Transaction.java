import java.util.Date;

/**The Transaction Class creates Transaction Objects as well as
 * contains the implementation to manipulate the cash a User has
 * in a certain Cash Account and the amount of a certain Equity
 * that a User owns.
 * Created by Owen on 3/12/2016.
 */
public class Transaction implements java.io.Serializable{

    Equity eq;
    float cost;
    CashAccount cash;
    float count;
    float money;
    Portfolio user;

    /**
     * Constructor of Transaction Objects.
     * @param eq - The Equity being handled in the Transaction.
     * @param cash - The Cash Account being handled in the transaction.
     * @param count - The amount of a certain Equity being handled.
     */
    Transaction(Equity eq, CashAccount cash, float count, Portfolio user){
        this.cost = eq.cost;        this.eq = eq;

        this.cash = cash;
        this.count = count;
        this.user = user;
    }

    Transaction(CashAccount cash, float count, Portfolio user){
        this.cash = cash;
        this.count = count;
        this.user = user;
    }

    /**
     * Increases the amount of a certain Equity a User owns while
     * reducing the amount of money in a User's Cash Account that is
     * owed.
     */
    void buyEq() {
        money = eq.cost * count;
        if (cash.amount >= money) {
            user.buyEquity(eq, count);
            cash.withdraw(money);
        }
        user.OrderHistory.add(new OrderLog(this, new Date()));
    }

    /**
     * Decreases the amount of a certain Equity a User owns while
     * increasing the amount of money in a User's Cash Account by the
     * amount owed to them.
     */
    void sellEq() {
        user.sellEquity(eq, count);
        float totalCost = eq.cost * count;
        cash.deposit(totalCost);
        user.OrderHistory.add(new OrderLog(this, new Date()));
    }

    /**
     * Reduces the amount of money a User has in their Cash Account.
     */
    void withdraw() {
        if (cash.amount >= count) {
            cash.withdraw(count);
        }
        user.OrderHistory.add(new OrderLog(this, new Date()));
    }

    /**
     * Increases the amount of money a User has in their Cash Account.
     */
    void deposit(){
        cash.deposit(count);
        user.OrderHistory.add(new OrderLog(this, new Date()));
    }

    public String toString(){
        return eq + " " + cost + " " + cash + " " + count + " " + user;
    }
}
