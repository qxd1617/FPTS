import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * The Login class stores the Users' userID, password and portfolio
 * and facilitates the creation of new Users to the system
 * Created by Matt on 3/3/2016.
 */
public class Login implements java.io.Serializable{
    //Stores the UserID and password of all Users
    private HashMap<String, String> Credentials = new HashMap<String, String>();

    //Stores all Users in the system
    private ArrayList<User> UserList = new ArrayList<User>();

    //Current User who is using the system
    private static User activeUser;

    //Id number needed for saving the login class
    private static final long serialVersionUID = 867378462;

    /**
     * Creates a new User if the inputted userID has not been taken
     * @param userID - String ID that the User wants as there identifier
     * @param password - String that the User wants as there password to there account
     * @return
     */
    public boolean create(String userID, String password){

        if(Credentials.containsKey(userID)){
            return false;
        }
        else{
            Credentials.put(userID, password);

            Portfolio newPortfolio = new Portfolio();
            User newUser = new User(userID, password, newPortfolio);

            UserList.add(newUser);
            activeUser = newUser;
            return true;
        }

    }

    /**
     * Sets a user as the current user if the supplied userID and password match a pair of Strings in the
     * Credentials HashMap
     * @param userID - Supplied userID that the User wants to login with
     * @param password - Supplied password that the User wants to use to login
     * @return - True if the userID and password matched a pair in the Credentials, otherwise False
     */
    public Boolean login(String userID, String password){

        if (Credentials.containsKey(userID)){
            if (Credentials.get(userID).equals(password)){
                int x;
                for(x = 0; x < UserList.size(); x++){
                    if(userID.equals(UserList.get(x).userID)){
                        activeUser = UserList.get(x);
                        break;
                    }
                }
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Set activeUser to null
     */
    public void logout(){
        activeUser = null;

    }

    public HashMap<String, String> getCredentials() {
        return Credentials;
    }

    public ArrayList<User> getUserList() {
        return UserList;
    }
    public void setActiveUser(User user){
        activeUser = user;
    }

    public static User getActiveUser() {
        return activeUser;
    }

    /**
     * Adds new User to the UserList on the system.
     * Used for Saving and Updating purposes
     * @param user - User to add to the UserList
     */
    public void addUser(User user){
        UserList.add(user);
    }


    /**
     * Provides basic command prompt interface for the login class.
     * Used by Admins to test save files and add users to the system
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        Boolean loggedin = Boolean.FALSE;
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        String userID;
        String password;
        String input;
        Login log = null;
        try {
            FileInputStream fileIn = new FileInputStream("tmp/passwords.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            log = (Login) in.readObject();
            in.close();
            fileIn.close();
        }
        catch(ClassNotFoundException except){
            System.out.println("Login class not found");
        }

        while (!loggedin){
            System.out.println("Enter 1 for login\nEnter 2 for create\n");
            input = c.readLine();
            if (input.equals("1")) {
                System.out.println("Enter your userID: ");
                userID = c.readLine();
                System.out.println("Enter your password: ");
                password = c.readLine();
                loggedin = log.login(userID, password);



            }
            else if(input.equals("2")){
                System.out.println("Enter your desired userID");
                userID = c.readLine();
                System.out.println("Enter your desired password");
                password = c.readLine();
                log.create(userID, password);
                loggedin = log.login(userID, password);
            }
        }

        while(loggedin){
            System.out.println("Enter 1 to logout\n");
            input = c.readLine();
            if(input.equals("1")) {
                log.logout();
                loggedin = Boolean.FALSE;
            }
        }


        FileOutputStream out = new FileOutputStream("tmp/passwords.ser");
        ObjectOutputStream output = new ObjectOutputStream(out);
        output.writeObject(log);
        output.close();
    }
}
