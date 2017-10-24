/**
 * The Equity Class creates the Equity objects and contains the
 * implementation that increases and decreases the amount of one
 * Equity that is owned as they are bought and sold.
 * Created by Owen on 3/2/2016.
 */
public class Equity implements java.io.Serializable, Holdings, Visitable{


    String name;
    float cost;


    /**
     * Constructor for Equity object.
     * @param name_input - Name of the Equity.
     * @param costInput - Cost of the Equity.
     */
    public Equity(String name_input, float costInput){
        this.cost = Math.round(costInput*100f)/100f;
        this.name = name_input;
    }

    public Equity(Equity old){
        this.cost = Math.round(old.cost*100f)/100f;
        this.name = old.name;
    }

    public Float getCost(){
        return this.cost;
    }
    public String getName(){
        return this.name;
    }
    /**
     * Creates and returns a string of the variables of a
     * certain Equity.
     * @return String of the Equity variables.
     */
    @Override
    public String toString() {
        return name + " " + cost;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((Equity)obj).getName());
    }

    public int hashCode(){
        return name.hashCode();
    }

    public void accept(Visitor visitor){}
}
