import java.util.ArrayList;
import java.util.HashMap;

/**The Portfolio Class creates Portfolio Objects and contains
 * the ArrayList of all the Holdings a User owns and the
 * ArrayList for a Users Order History.
 * Created by Matt D/Owen/Matt C/Andrew on 3/3/2016.
 */
public class Portfolio implements java.io.Serializable{

    /**
     * ArrayList of all of the Holdings a User owns.
     */
    public HashMap<Equity, Float> ownedEquities;
    public ArrayList<CashAccount> ownedCash;
    public Watchlist watchlist;

    /**
     * ArrayList of OrderLog Objects which acts as a
     * history of past orders.
     */
    ArrayList<OrderLog> OrderHistory;


    /**
     * Constructor for Portfolio Objects.
     */
    public Portfolio(){
        ownedCash = new ArrayList<CashAccount>();
        ownedEquities = new HashMap<Equity, Float>();
        OrderHistory = new ArrayList<OrderLog>();
        watchlist = new Watchlist();
    }

    public Watchlist getWatchlist(){
        return watchlist;
    }

    public void addCashAccount(CashAccount acc){
        ownedCash.add(acc);
    }

    public void removeCashAccount(CashAccount acc){
        ownedCash.remove(ownedCash.indexOf(acc));
    }

    public void buyEquity(Equity newHolding, Float count){
        if(ownedEquities.containsKey(newHolding)){
            ownedEquities.put(newHolding, ownedEquities.get(newHolding)+count);
        }
        else {
            ownedEquities.put(newHolding, count);
        }
    }

    public void sellEquity(Equity newHolding, Float count){
                System.out.println(newHolding);
                System.out.println("");
                float value = ownedEquities.get(newHolding);
                ownedEquities.put(newHolding, value - count);
                if(ownedEquities.get(newHolding) <= 0){
                    ownedEquities.remove(newHolding);
                }
    }

    public void makeCashAct(String name, float balance){
        ownedCash.add(new CashAccount(name, balance));
    }

    public void deleteCashAct(CashAccount del){
        ownedCash.remove(del);
    }

    public CashAccount getCashAccount(String name){
        for (CashAccount i: ownedCash){
            if (i.getName().equals(name)) {
                return i;
            }
        }
        return null;
    }

    public void Export(){


    }
    public void Import(Portfolio importedPortfolio){}
}
