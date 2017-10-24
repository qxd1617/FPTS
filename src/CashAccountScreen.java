import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * This class creates the scene that represents
 * the interface for adding a new cash account
 * to the portfolio
 * Created by Matt on 3/13/2016.
 */
public class CashAccountScreen {

    public Scene cashScene(){

        GridPane loginGrid = createGrid();

        BorderPane border = new BorderPane();
        border.setTop(menubar());
        border.setCenter(loginGrid);
        return new Scene(border, 900, 600);
    }

    /**
     * Creates the set of menu buttons found on the main pages of the UI
     * @return - Set of menu buttons
     */
    public HBox menubar(){
        HBox menu = new HBox();
        Button search = new Button("Search");
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SearchScreen p = new SearchScreen();
                ViewControl.thestage.setScene(p.SearchScene());
            }
        });
        Button portfolio = new Button("Portfolio");
        portfolio.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PortfolioScreen p = new PortfolioScreen(ViewControl.log.getActiveUser());
                ViewControl.thestage.setScene(p.getScene());
            }
        });
        Button logout = new Button("Logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewControl.log.logout();
                ViewControl.saveLoginFile();
                LoginScreen p = new LoginScreen();
                ViewControl.thestage.setScene(p.loginScene());
            }
        });
        menu.getChildren().addAll(portfolio,search,logout);
        menu.setSpacing(10);
        return menu;
    }

    /**
     * Creates the GridPane that holds the TextInput bars
     * and labels for the interface
     * @return
     */
    public GridPane createGrid(){
        final GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(5);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(40,40,40,40));

        Label userNameText = new Label("Name of Account");
        loginGrid.add(userNameText, 0, 1);

        final TextField userNameInput = new TextField();
        loginGrid.add(userNameInput, 1, 1);

        Label passwordText = new Label("Initial Account Balance");
        loginGrid.add(passwordText, 0, 2);

        final NumberTextField numberInput= new NumberTextField();
        loginGrid.add(numberInput, 1, 2);

        Text title = new Text("Cash Account Creation");
        title.setFont(Font.font(20));
        loginGrid.add(title, 1,0);

        //Saves the account by updating the currentuser
        Button loginBtn = new Button("Save Account");
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((!numberInput.getText().isEmpty())&&(!userNameInput.getText().isEmpty())){
                    CashAccount acc = new CashAccount(userNameInput.getText(), Float.parseFloat(numberInput.getText()));
                    User currentUser = ViewControl.log.getActiveUser();
                    ViewControl.log.getUserList().remove(currentUser);
                    currentUser.portfolio.addCashAccount(acc);
                    ViewControl.log.addUser(currentUser);
                    PortfolioScreen p = new PortfolioScreen(currentUser);
                    ViewControl.thestage.setScene(p.getScene());
                }
            }
        });
        loginGrid.add(loginBtn, 1,3);

        return loginGrid;
    }
}
