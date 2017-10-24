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
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates the UI page for interfacing with cash accounts
 * This class gets called when a user specifies a cash account
 * User can add and withdraw from the specified account through the UI page that is created
 * Created by DeQuatts on 4/4/2016.
 */
public class CashAccountTransaction extends Screen {

    CashAccount account;

    /**
     * Sets the account for withdrawing and depositing from
     * @param account - Cash account to deposit into and/or withdraw from
     */
    public CashAccountTransaction(CashAccount account){
        this.account = account;
    }

    /**
     * Creates the UI page for depositing and withdrawing from a cash account
     * Provides an input area for the amount of money to be deposited or withdrawn
     * @return Scene of the Cash Account UI page
     */
    public Scene CashAccountTransactionScene(){
        final GridPane cashAccountTransactionGrid = new GridPane();
        cashAccountTransactionGrid.setAlignment(Pos.CENTER);
        cashAccountTransactionGrid.setHgap(5);
        cashAccountTransactionGrid.setVgap(10);
        cashAccountTransactionGrid.setPadding(new Insets(40, 40, 40, 40));
        Text title;
        Label amountText;
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        final TextField amountInput = new NumberTextField();
        cashAccountTransactionGrid.add(amountInput, 1, 1);

        amountText = new Label("Amount of Money to Deposit/Withdraw: $");
        title = new Text(ViewControl.log.getActiveUser().portfolio.ownedCash.get(ViewControl.log.getActiveUser().portfolio.ownedCash.indexOf(account)).getName()
                + ": $" + account.amount);
        depositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if((!amountInput.getText().isEmpty()) && (account != null)){
                    User currentUser = ViewControl.log.getActiveUser();
                    Transaction t = new Transaction(account, Float.parseFloat(amountInput.getText()), currentUser.getPortfolio());
                    Deposit d = new Deposit(t);
                    ViewControl.agent.takeOrder(d);
                    ViewControl.agent.placeOrders();
                    ViewControl.log.setActiveUser(currentUser);
                    PortfolioScreen p = new PortfolioScreen(currentUser);
                    ViewControl.thestage.setScene(p.getScene());
                }
            }
        });

        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if((!amountInput.getText().isEmpty()) && (account != null)){
                    User currentUser = ViewControl.log.getActiveUser();
                    Transaction t = new Transaction(account, Float.parseFloat(amountInput.getText()), currentUser.getPortfolio());
                    Withdraw w = new Withdraw(t);
                    ViewControl.agent.takeOrder(w);
                    ViewControl.agent.placeOrders();
                    ViewControl.log.setActiveUser(currentUser);
                    PortfolioScreen p = new PortfolioScreen(currentUser);
                    ViewControl.thestage.setScene(p.getScene());
                }
            }
        });

        cashAccountTransactionGrid.add(amountText, 0, 1);
        cashAccountTransactionGrid.add(depositButton, 1, 3);
        cashAccountTransactionGrid.add(withdrawButton, 2, 3);
        title.setFont(Font.font(20));
        cashAccountTransactionGrid.add(title, 1, 0);

        BorderPane border = new BorderPane();
        border.setCenter(cashAccountTransactionGrid);
        border.setTop(menubar());

        return new Scene(border, 1000, 600);
    }
}
