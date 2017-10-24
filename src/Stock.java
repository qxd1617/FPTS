import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**The Stock Class creates Stock Objects, contains an ArrayList of Indexes
 * that each Stock could belong to and contains the functionality to create
 * new Indexes.
 * Created by Owen on 3/4/2016.
 */
public class Stock extends Equity implements java.io.Serializable  {

    /**
     * ArrayList of Indexes that each Stock could belong to.
     */
    public ArrayList<Index> belongsTo = new ArrayList<Index>();
    public String symbol;

    /**
     * Constructor of Stock Object.
     * @param tickerInput - Ticker Symbol of the Stock.
     * @param name_input - Name of the Stock.
     * @param costInput - Cost of the Stock.
     */
    public Stock(String tickerInput, String name_input, float costInput) {
        super(name_input, costInput);
        this.symbol = tickerInput;
    }

    /**
     * Creates a new Index that Stocks could belong to.
     * @param newIndex - The new Index being created.
     */
    public void setIndex(Index newIndex){
        belongsTo.add(newIndex);
    }
    public ArrayList getIndex(){
        return this.belongsTo;
    }
    public String getSymbol(){
        return this.symbol;
    }


    public boolean equals(Stock other) {
        if(getClass() == other.getClass()) {
            return (this.symbol.equals(other.getSymbol()) && this.name.equals(other.getName()));
        }
        return false;
    }
    //34
    public void accept(Visitor object){
        object.visit(this);
    }
}
