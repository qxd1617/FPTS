import java.io.*;

/**The User Class creates User Objects.
 * Created by Matthew on 3/12/2016.
 */
public class User implements java.io.Serializable{

    public String userID;
    public String password;
    public Portfolio portfolio;

    /**
     * Constructor for User Objects.
     * @param userID - UserID for the User.
     * @param password - Password for the User.
     * @param portfolio - Portfolio for the User.
     */
    User(String userID, String password, Portfolio portfolio){
        this.userID = userID;
        this.password = password;
        this.portfolio = portfolio;
    }

    public void exportPortfolio(){
        try {
            FileOutputStream fout = new FileOutputStream("out/" + this.userID);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(portfolio);
            oos.close();
        }

        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void importPortfolio(String inputID) {

        try {
            FileInputStream fin = new FileInputStream("out/" + inputID);
            ObjectInputStream ois = new ObjectInputStream(fin);
            portfolio =  (Portfolio) ois.readObject();
            ois.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
