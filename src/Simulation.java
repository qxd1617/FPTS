
/**
 * Created by Andrew Iacovino on 3/13/2016.
 */

import java.util.HashMap;

/**
 * Current functionality: a simulation object is created, passed in with the market type ("Bull" or "Bear"). Any other
 * input is treated  as no-gain. It is also given a time interval ("Day" or "Year") anything else is treated like a year
 * It is also given an ArrayList of stocks. When Simulate is called, it will adjust said stocks with the given market and
 * time interval. For Bull and Bear, it will ask for a percetile increase/decrease. It will then print the new stock
 * worths. Calling simulate again will further modify those stocks with the same market and interval. The stocks can
 * have their values reset or have changes undone.
 *
 * Can be added: The loop and asking for the market and interval can be handled in simulation.
 */

public class Simulation {
    //-Variables
    public HashMap<Equity,Float> unmodifiedPortfolio = new HashMap<Equity,Float>(); //Arraylist of Current stock values
    public HashMap<Equity,Float> lastUsedPortfolio = new HashMap<Equity,Float>();   //Arraylist of the last used values
    public HashMap<Equity,Float> currentPortfolio = new HashMap<Equity,Float>();    //Arraylist of the modified values
    public String time;
    public Float percentage;
    public String market;
    //Constructor, passed in a portfolio

    public Simulation(HashMap<Equity,Float> Portfolio)
    {
        //All three arraylists are set with the current equity prices
        unmodifiedPortfolio= copyPortfolio(Portfolio);
        lastUsedPortfolio = copyPortfolio(Portfolio);
        currentPortfolio = copyPortfolio(Portfolio);
        chooseParameters("","",(float)0);
    }

    public HashMap<Equity,Float> copyPortfolio(HashMap<Equity,Float> port){
        HashMap<Equity,Float> newPort = new HashMap<>();
        Equity x = null;
        for(Equity e : port.keySet()){
            if (e instanceof Stock){
                x = new Stock(((Stock) e).symbol, e.getName(), e.cost);
            }
            else{
                x = new Index(e.getName());
            }
            Float newValue = port.get(e);
            newPort.put(x,newValue);
        }
        return newPort;
    }

    //simulation main
    public void chooseParameters(String marketType, String timeInterval, Float costPercentage)
    {
            time = timeInterval;
            market=marketType;
            percentage = costPercentage;
    }

    public void Simulate()
    {
        lastUsedPortfolio = copyPortfolio(currentPortfolio);
        if (market.equals("Bull"))
        {
            BullMarket bull = new BullMarket(currentPortfolio, time, percentage);
            currentPortfolio = bull.Simulate();
        }

        else if (market.equals("Bear"))
        {
            BearMarket bear = new BearMarket(currentPortfolio, time, percentage);
            currentPortfolio = bear.Simulate();
        }

        else
        {
            NogrowthMarket no = new NogrowthMarket(currentPortfolio);
            currentPortfolio = no.Simulate();
        }

    }

    //display method
    public void Display() //prints out the name and cost of all our stocks
    {
        System.out.println("Simulated  Portfolio Worth");
        for(Equity object: currentPortfolio.keySet()) {
            System.out.println(object.getName()+": $"+object.cost);
        }
    }
    //resets the stock to the live value given when the simulation was created
    //sets last value to what it was before so you can undo the reset
    public void Reset()
    {
        lastUsedPortfolio=copyPortfolio(currentPortfolio);
        currentPortfolio=copyPortfolio(unmodifiedPortfolio);
    }

    //sets the stocks to the values they had before the last change
    //also makes the last value the current one so you can undo the undo
    public void Undo()
    {
        HashMap<Equity,Float> temp = currentPortfolio;
        currentPortfolio=lastUsedPortfolio;
        lastUsedPortfolio= temp;
    }
}
