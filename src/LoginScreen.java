/**
 * Created by Matt on 3/13/2016.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;

/**
 * Login JavaFX UI class.
 * Provides User with an interface to login and create new User's with
 */
public class LoginScreen{


    private Text message;

    /**
     * Gets the message being displayed at the bottom of the LoginScreen
     * @return Text in the label at the bottom of the UI screen
     */
    public Text getMessage() {
        return message;
    }

    /**
     * Sets the Message at the bottom of the LoginScreen.
     * Used for testing purposes
     * @param message
     */
    public void setMessage(Text message) {
        this.message = message;
    }

    /**
     * Creates the GridPane that holds the labels and textInputFields
     * found in the LoginScreen interface
     * @return
     */
    public GridPane createLoginGrid(){
        final GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(5);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(40,40,40,40));

        Label userNameText = new Label("User Name:");
        loginGrid.add(userNameText, 0, 1);

        final TextField userNameInput = new TextField();
        loginGrid.add(userNameInput, 1, 1);

        Label passwordText = new Label("Password:");
        loginGrid.add(passwordText, 0, 2);

        final PasswordField passwordInput = new PasswordField();
        loginGrid.add(passwordInput, 1, 2);

        Text title = new Text("Welcome to FPTS");
        title.setFont(Font.font(20));
        loginGrid.add(title, 1,0);

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((!passwordInput.getText().isEmpty())&&(!userNameInput.getText().isEmpty())){
                    Boolean loggedIn = ViewControl.log.login(userNameInput.getText(),passwordInput.getText());
                    if(loggedIn){
                        Text message = getMessage();
                        message.setText("Login Successful");
                        setMessage(message);
                        PortfolioScreen newscreen = new PortfolioScreen(ViewControl.log.getActiveUser());
                        ViewControl.thestage.setScene(newscreen.getScene());
                    }
                    else{
                        Text message = getMessage();
                        message.setText("Login Failed");
                        setMessage(message);
                    }
                }
                else{
                    Text message = getMessage();
                    message.setText("Please input an username and password");
                    setMessage(message);
                }
            }
        });
        loginGrid.add(loginBtn, 1,3);


        Button createBtn = new Button("Create Account");
        createBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((!passwordInput.getText().isEmpty())&&(!userNameInput.getText().isEmpty())){
                    boolean b = ViewControl.log.create(userNameInput.getText(),passwordInput.getText());
                    if(b) {
                        Text message = getMessage();
                        message.setText("Account Created");
                        setMessage(message);
                        PortfolioScreen newscreen = new PortfolioScreen(ViewControl.log.getActiveUser());
                        ViewControl.thestage.setScene(newscreen.getScene());
                    }
                    else{
                        Text message = getMessage();
                        message.setText("That User Name is already taken, please enter a new User Name.");
                        setMessage(message);
                    }
                }
                else{
                    Text message = getMessage();
                    message.setText("Please input an username and password");
                    setMessage(message);
                }
            }
        });
        loginGrid.add(createBtn, 1,4);

        return loginGrid;
    }

    /**
     * Creates the LoginScene that is called when the
     * User Interface switches to the Login page
     * @return
     */
    public Scene loginScene(){

        Text message = new Text();
        message.setFont(Font.font(18));
        this.message = message;
        GridPane loginGrid = createLoginGrid();



        BorderPane border = new BorderPane();
        border.setCenter(loginGrid);
        BorderPane.setAlignment(this.message, Pos.BOTTOM_CENTER);
        border.setBottom(this.message);
        return new Scene(border, 1000, 600);
    }



}
