import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Owen on 4/7/2016.
 */
public class UpdateTimer {
    public static void main(long Timer) {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Update updator = new Update();
                for (Stock obj: Reader.stockMarket){
                    obj.accept(updator);
                }
                for (Index obj: Reader.indexMarket){
                    obj.accept(updator);
                }
            }
        };
        ViewControl.timer.schedule(task, 0l, Timer);
    }
}