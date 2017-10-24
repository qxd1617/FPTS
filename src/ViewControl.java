import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Matt on 3/31/2016.
 */
public class ViewControl extends Application{
    static Login log;
    static Stage thestage;
    static Broker agent;
    static Timer timer = new Timer();

    /**
     * Loads the Login object that is stored in the serializable file
     */
    public void loadSavedLogin(){
        Login log = new Login();

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
        catch(FileNotFoundException except){
            System.out.println("File not found");
        }
        catch(IOException except){

        }
        
        this.log = log;
    }

    /**
     * Saves the current Login object to the serializable file
     */
    public static void saveLoginFile(){
        try {
            FileOutputStream out = new FileOutputStream("tmp/passwords.ser");
            ObjectOutputStream output = new ObjectOutputStream(out);
            output.writeObject(log);
            output.close();
            out.close();
        }
        catch(FileNotFoundException except){
            System.out.println("File not found");
        }
        catch(IOException except){

        }
    }

    /**
     * Starts the JavaFx interface and saves the Login object after the User exits the program
     * @param args
     */
    public static void main(String[] args) {
        Reader.main();
        launch(args);
        UpdateTimer.main(60000);
        timer.cancel();
        saveLoginFile();
    }

    @Override
    /**
     * Loads the Login object from its save file and runs the JavaFx
     * application
     */
    public void start(Stage primaryStage) {
        loadSavedLogin();
        thestage = primaryStage;
        primaryStage.setTitle("Welcome to FPTS");
        agent = new Broker();
        LoginScreen p = new LoginScreen();
        primaryStage.setScene(p.loginScene());
        primaryStage.show();
    }
}
