/**
 * Created by Owen on 4/7/2016.
 */
public class WatchData implements java.io.Serializable {
    Float high;
    Float low;
    boolean highFlag;
    boolean lowFlag;

    public WatchData(Float highInput, Float lowInput){
        if (highInput != null) {
            this.high = highInput;
        }
        if (lowInput != null) {
            this.low = lowInput;
        }
        highFlag = false;
        lowFlag = false;
    }

    public void setHigh(){
        if (highFlag == true){
            highFlag = false;
        }
        else{
            highFlag = true;
        }
    }
    public void setLow(){
        if (lowFlag == true){
            lowFlag = false;
        }
        else{
            lowFlag = true;
        }
    }

    public Float getHigh(){
        return high;
    }

    public Float getLow(){
        return low;
    }
}
