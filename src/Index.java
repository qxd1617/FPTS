import java.util.ArrayList;

/**The Index Class creates Index objects, contains an ArrayList of
 * Equities that are contained in a certain Index, contains the
 * implementation to add a Stock to the ArrayList of SubEquities,
 * and contains the implementation to calculate the cost of an Index.
 * Created by Owen on 3/2/2016.
 */
public class Index extends Equity implements java.io.Serializable{

    /**
     * ArrayList of all Equities that are contained in a certain Index.
     */
    private ArrayList<Equity> subEquities = new ArrayList<Equity>();


    /**
     * Constructor for Index objects.
     * @param name_input - Name of the Index.
     */
    public Index(String name_input) {
        super(name_input,0);
        calculateCost();
    }

    public ArrayList getComponents(){
        return this.subEquities;
    }

    /**
     * Adds a certain Stock to the ArrayList of Equities contained in a
     * certain Index.
     * @param s - Stock to be added ot the ArrayList.
     */
    public void addComponent(Stock s) {
        subEquities.add(s);
    }

    /**
     * Calculates the cost of a certain Index.
     */
    public void calculateCost(){
        float cost = 0;
        for (Equity equity: subEquities){
            cost += equity.cost;
        }
        cost = cost/subEquities.size();
        this.cost = Math.round(cost*100f)/100f;
    }


    @Override
    public String toString() {
        return name+" Average Cost: "+cost;
    }

    public void accept(Visitor object){
        object.visit(this);
    }
}
