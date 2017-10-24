import java.util.HashMap;

/**
 * Created by Owen on 4/7/2016.
 */
public class Watchlist implements java.io.Serializable {
    HashMap<Equity, WatchData> watched;
    public Watchlist(){
        watched = new HashMap<>();
    }

    public void addEquity(Equity eq, WatchData data){
        if(data != null) {
            watched.put(eq, data);
        }
        else{
            watched.put(eq, null);
        }
    }

    public void removeEquity(Equity eq){
        watched.remove(eq);
    }

    public void check(Equity eq){
        WatchData data = watched.get(eq);
        if (data.getLow() != null) {
            if (eq.cost < data.getLow()) {
                data.setLow();
            }
        }
        else if (data.getHigh() != null) {
            if (eq.cost > data.getHigh()) {
                data.setHigh();
            }
        }
    }
}
