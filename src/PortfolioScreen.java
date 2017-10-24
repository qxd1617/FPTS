import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Creates the Portfolio interface with JavaFx
 *
 * Created by Matt on 3/13/2016.
 */
public class PortfolioScreen extends Screen{
    Scene scene;
    User currentUser;
    TableView<CashAccount> cashAccTable;
    TableView<Map.Entry<Equity, Float>> tableStocks;
    Equity selectedStock;

    /**
     * Constructor for the PortfolioScreen class
     * @param user
     */
    public PortfolioScreen(User user){
        this.currentUser = user;
        this.scene = portfolioScene();
    }

    public void updateTable(){
        int numberColumnsCashAcc = cashAccTable.getColumns().size();
        for(int i = 0;i < numberColumnsCashAcc; i++){
            cashAccTable.getColumns().get(i).setVisible(false);
            cashAccTable.getColumns().get(i).setVisible(true);
        }
        int numberColumnsEquity = tableStocks.getColumns().size();
        for(int i = 0;i < numberColumnsEquity; i++){
            tableStocks.getColumns().get(i).setVisible(false);
            tableStocks.getColumns().get(i).setVisible(true);
            System.out.println("Hello Matt");
            System.out.println(tableStocks.getItems());
        }
    }
    /**
     * Creates the set of menu buttons found on the portfolio page of the UI
     * @return - set of buttons in a VBox object
     */
    public VBox portfolioMenu(){
        VBox portMenu = new VBox();
        Button cashAcc = new Button("Add Cash Account");
        Button removeCashAcc = new Button("Remove Cash Account");
        Button undo = new Button("Undo Last Transaction");
        Button redo = new Button("Redo");
        Button addToWatchlist = new Button("Add to Watchlist");
        cashAcc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CashAccountScreen p = new CashAccountScreen();
                ViewControl.thestage.setScene(p.cashScene());
            }
        });

        removeCashAcc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RemoveCashAccountScreen p = new RemoveCashAccountScreen();
                ViewControl.thestage.setScene(p.removeCashAccountScreen());
            }
        });

        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewControl.agent.undoOrder();
                updateTable();
            }
        });
        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ViewControl.agent.redoOrder();
                updateTable();
            }
        });

        addToWatchlist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddtoWatchlistScreen p = new AddtoWatchlistScreen(selectedStock);
                ViewControl.thestage.setScene(p.AddtoWatchlistScene());
            }
        });


        portMenu.getChildren().addAll(cashAcc, removeCashAcc, addToWatchlist, undo,redo);
        return portMenu;
    }


    public void updatePortfolio(){
        for(Equity e : currentUser.portfolio.ownedEquities.keySet()){
            e.cost = Reader.stockMarket.get(Reader.stockMarket.indexOf(e)).cost;
        }
    }
    /**
     * Creates the initial scene on the Portfolio page
     * Populates the table with all the holdings in the portfolio
     * @return
     */
    public Scene portfolioScene(){
        updatePortfolio();
        ScrollPane scrollCash = new ScrollPane();
        ObservableList holds = FXCollections.observableArrayList(currentUser.portfolio.ownedCash);
        cashAccTable = new TableView<CashAccount>();
        cashAccTable.setEditable(true);
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Holdings, String>("name")
        );
        TableColumn amountCol = new TableColumn("Amount");
        amountCol.setMinWidth(100);
        amountCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<CashAccount, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<CashAccount, String> p) {
                        float x = Math.round(p.getValue().amount * 100f)/100f;
                        return new ReadOnlyObjectWrapper<String>(x + "");
                    }
                }
        );
        cashAccTable.getColumns().addAll(nameCol,amountCol);
        cashAccTable.setItems(holds);

        cashAccTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    CashAccountTransaction cAT = new CashAccountTransaction(currentUser.portfolio.ownedCash.get(currentUser.portfolio.ownedCash.indexOf(cashAccTable.getSelectionModel().getSelectedItem())));
                    ViewControl.thestage.setScene(cAT.CashAccountTransactionScene());
                }
            }
        });

        cashAccTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        scrollCash.setContent(cashAccTable);

        ScrollPane scrollStock = new ScrollPane();
        ObservableList<Map.Entry<Equity,Float>> stocks = FXCollections.observableArrayList(currentUser.portfolio.ownedEquities.entrySet());

        tableStocks = new TableView<Map.Entry<Equity, Float>>(stocks);
        TableColumn<Map.Entry<Equity, Float>,String> equityCol = new TableColumn("Equity");
        equityCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey().toString()));

        TableColumn<Map.Entry<Equity, Float>,String> ownedCol = new TableColumn("Amount Owned");
        ownedCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(p.getValue().getValue().toString()));

        tableStocks.getColumns().addAll(equityCol,ownedCol );
        tableStocks.setEditable(true);
        tableStocks.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2 && tableStocks.getSelectionModel().getSelectedItem() != null) {
                    TransactionScreen p = new TransactionScreen(tableStocks.getSelectionModel().getSelectedItem().getKey(),Boolean.FALSE);
                    ViewControl.thestage.setScene(p.TransactionScene());
                }
                else if(event.isPrimaryButtonDown() && event.getClickCount() == 1  && tableStocks.getSelectionModel().getSelectedItem() != null){
                    selectedStock = tableStocks.getSelectionModel().getSelectedItem().getKey();
                }
            }
        });
        scrollStock.setContent(tableStocks);
        scrollStock.setFitToWidth(true);
        tableStocks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label message1 = new Label("Double click an equity to sell it");
        Label message2 = new Label("Double click a cash account to add or remove funds");

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(scrollStock, message1, message2);
        BorderPane main = new BorderPane();
        main.setRight(scrollCash);
        main.setCenter(vbox);
        BorderPane.setAlignment(scrollCash, Pos.CENTER_RIGHT);

        HBox menu = menubar();
        VBox portfolioMenu = portfolioMenu();
        main.setTop(menu);
        main.setLeft(portfolioMenu);
        BorderPane.setAlignment(menu, Pos.TOP_CENTER);
        BorderPane.setAlignment(portfolioMenu, Pos.CENTER_RIGHT);
        return new Scene(main, 900, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
