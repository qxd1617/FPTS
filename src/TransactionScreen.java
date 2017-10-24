import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates a UI page for the User to buy or sell an equity from
 * UI page provides a text input area for the User to specify the amount of equities to sell
 * UI page also provides the User with a table of their cash accounts
 * Created by Matt C on 3/31/2016.
 */
public class TransactionScreen extends Screen{

    Equity stock;
    CashAccount account = null;
    Boolean buying;


    /**
     * Constructor for the transaction screen
     * @param stock - Equity to buy or sell
     * @param buying - Boolean that specifies whether the equity will be bought or sold
     */
    public TransactionScreen(Equity stock, Boolean buying){
        this.buying = buying;
        this.stock = stock;
    }

    /**
     * Creates the UI Scene for the Transaction interface
     * Creates a table that contains the User's cash accounts and an input field for the amount of stock to buy or sell
     * @return
     */
    public Scene TransactionScene(){
        final GridPane transactionGrid = new GridPane();
        transactionGrid.setAlignment(Pos.CENTER);
        transactionGrid.setHgap(5);
        transactionGrid.setVgap(10);
        transactionGrid.setPadding(new Insets(40,40,40,40));
        Label amountText;
        Text title;
        Button btn;
        final TextField amountInput = new NumberTextField();
        transactionGrid.add(amountInput, 1, 1);
        if(buying == Boolean.TRUE) {
            amountText = new Label("Number of Stock to Buy");
            title = new Text("Buying " + stock.name + ": $" + stock.cost);
            btn = new Button("Buy");
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if((!amountInput.getText().isEmpty()) && (account != null)){
                        User currentUser = ViewControl.log.getActiveUser();
                        Transaction t = new Transaction(stock,account, Float.parseFloat(amountInput.getText()),currentUser.getPortfolio());
                        BuyStock b = new BuyStock(t);
                        ViewControl.agent.takeOrder(b);
                        ViewControl.agent.placeOrders();
                        ViewControl.log.setActiveUser(currentUser);
                        PortfolioScreen p = new PortfolioScreen(currentUser);
                        ViewControl.thestage.setScene(p.getScene());
                    }
                }
            });
        }
        else{
            amountText = new Label("Number of Stock to Sell");
            title = new Text("Selling " + stock.name + ". Number of Stock Owned: "+ ViewControl.log.getActiveUser().portfolio.ownedEquities.get(stock));
            btn = new Button("Sell");
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if((!amountInput.getText().isEmpty()) && (account != null)){
                        User currentUser = ViewControl.log.getActiveUser();
                        Transaction t = new Transaction(stock,account, Float.parseFloat(amountInput.getText()),currentUser.portfolio);
                        SellStock s = new SellStock(t);
                        ViewControl.agent.takeOrder(s);
                        ViewControl.agent.placeOrders();
                        ViewControl.log.setActiveUser(currentUser);
                        PortfolioScreen p = new PortfolioScreen(currentUser);
                        ViewControl.thestage.setScene(p.getScene());
                    }
                }
            });
        }
        Label message = new Label("Click one of the cash accounts on the right to use");
        transactionGrid.add(message, 1, 4);
        transactionGrid.add(amountText, 0, 1);
        transactionGrid.add(btn, 1,3);
        title.setFont(Font.font(20));
        transactionGrid.add(title, 1,0);


        ScrollPane scrollAccounts = new ScrollPane();

        ObservableList accs = FXCollections.observableArrayList(ViewControl.log.getActiveUser().portfolio.ownedCash);
        final TableView<CashAccount> cashAccounts = new TableView<CashAccount>();
        cashAccounts.setEditable(true);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Holdings, String>("name")
        );
        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(
                new PropertyValueFactory<Holdings, String>("amount")
        );
        cashAccounts.getColumns().addAll(nameCol,amountCol);
        cashAccounts.setItems(accs);

        cashAccounts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                account = cashAccounts.getSelectionModel().getSelectedItem();
            }
        });

        scrollAccounts.setContent(cashAccounts);

        BorderPane border = new BorderPane();
        border.setCenter(transactionGrid);
        border.setRight(scrollAccounts);
        border.setTop(menubar());

        return new Scene(border,1000,600);
    }
}
