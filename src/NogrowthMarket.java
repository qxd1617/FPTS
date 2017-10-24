import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew I on 3/31/2016.
 */
public class NogrowthMarket {

    //-Variables
    public HashMap<Equity,Float> currentPortfolio = new HashMap<Equity,Float>();    //Arraylist of the modified values

    public NogrowthMarket(HashMap<Equity,Float> Portfolio)
    {
         currentPortfolio = copyPortfolio(Portfolio);

    }
    public HashMap<Equity,Float> copyPortfolio(HashMap<Equity,Float> port){
        HashMap<Equity,Float> newPort = new HashMap<>();
        for(Equity e : port.keySet()){
            newPort.put(e,port.get(e));
        }
        return newPort;
    }

    public HashMap<Equity,Float> Simulate(){return currentPortfolio;}
}
