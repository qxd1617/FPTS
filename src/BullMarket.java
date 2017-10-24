import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew I. on 3/31/2016.
 */
public class BullMarket implements SimulationInterface{

    //-Variables
    public HashMap<Equity,Float> lastUsedPortfolio = new HashMap<Equity,Float>();   //Arraylist of the last used values
    public HashMap<Equity,Float> currentPortfolio = new HashMap<Equity,Float>();
    public String time;
    public Float percent;

    public BullMarket(HashMap<Equity,Float> Portfolio, String timeInterval, Float percentage)
    {
        //All three arraylists are set with the current equity prices
        lastUsedPortfolio = copyPortfolio(Portfolio);
        currentPortfolio = copyPortfolio(Portfolio);
        //the time interval that defines this simulation
        time = timeInterval;
        percent = percentage;
    }

    public HashMap<Equity,Float> Simulate()
    {
        float divider = 1;//set the time interval as a float for figuring the price
        if (time.equals("Day"))
            divider = (1f / 365f);
        else if (time.equals("Month"))
            divider = (1f / 12f);

        for (Equity object : currentPortfolio.keySet())//Update all stocks with the price rise
        {
            float newprice = object.cost * percent * divider;
            object.cost = Math.round((object.cost+newprice)*100f)/100f;
            if (object.cost < 0)
                object.cost = 0;//make sure a stock cant cost negative
        }
        return copyPortfolio(currentPortfolio);
    }

    public HashMap<Equity,Float> copyPortfolio(HashMap<Equity,Float> port){
        HashMap<Equity,Float> newPort = new HashMap<>();
        for(Equity e : port.keySet()){
            newPort.put(e,port.get(e));
        }
        return newPort;
    }
}
