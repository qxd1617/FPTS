import java.util.ArrayList;

/**The Search Class contains the implementation for searching through
 * the list of all Equities and returning the results for what was
 * searched for.
 * Created by Owen on 3/2/2016.
 */

public class Search {

    /**
     *Searches through all of the Equities.
     * @param eq_symbol - Ticker Symbol of desired Equity
     * @param eq_name - Name of desired Equity
     * @param eq_index - Index of desired Equity
     * @return ArrayList of Equities that were searched for.
     */
    public static ArrayList search(String eq_symbol, String eq_name, String eq_index){
        ArrayList result = new ArrayList<Equity>();
        for (Equity eq: Reader.market) {
            if (eq instanceof Stock) {
                Stock stock = (Stock) eq;
                if (eq_symbol != "") {
                    if (stock.symbol.equals(eq_symbol)) {
                        result.add(stock);
                    }
                }
                if (eq_name != "") {
                    if (stock.name.equals(eq_name)) {
                        result.add(stock);
                    }
                }
                if (eq_index != "") {
                    for (Index subIndex : stock.belongsTo) {
                        if (subIndex.name.equals(eq_index)) {
                            result.add(stock);
                        }
                    }
                }
            } else if (eq instanceof Index) {
                Index index = (Index) eq;
                if (eq_name != "") {
                    if (index.name.equals(eq_name)) {
                        result.add(index);
                    }
                }
            }
        }
        return result;
    }

}
