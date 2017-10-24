import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**The SearchScreen Class contains the JavaFx UI for displaying all of the
 * different Stocks that are available for a User to buy.
 * Created by Matthew on 3/13/2016.
 */
public class SearchScreen extends Screen{

    /**
     * ArrayList containing all of the Stocks that are available for a
     * User to buy.
     */
    public static ArrayList<Stock> newStockMarket = new ArrayList<Stock>();
    private TableView<Stock> equitiesTable = new TableView<Stock>();
    private ObservableList<Stock> stockList;

    Equity selectedStock;




    /**
     * Sets the scene for the UI.
     * @return The newly created scene
     */
    public Scene SearchScene(){

        stockList = FXCollections.observableArrayList(Reader.stockMarket);
        ScrollPane scroll = new ScrollPane();

        Scene searchScene = new Scene(new Group());

        equitiesTable.setEditable(true);

        TableColumn tickerSymbol = new TableColumn("Ticker Symbol");
        tickerSymbol.setMinWidth(100);
        tickerSymbol.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("symbol")
        );

        TableColumn name = new TableColumn("Name");
        name.setMinWidth(250);
        name.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("name")
        );

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(250);
        cost.setCellValueFactory(
                new PropertyValueFactory<Stock, Float>("cost")
        );

        TableColumn index = new TableColumn("Index");
        index.setMinWidth(250);
        index.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("index")
        );

        equitiesTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    TransactionScreen p = new TransactionScreen(equitiesTable.getSelectionModel().getSelectedItem(), Boolean.TRUE);
                    ViewControl.thestage.setScene(p.TransactionScene());
                }
                else if(event.isPrimaryButtonDown() && event.getClickCount() == 1){
                    selectedStock = equitiesTable.getSelectionModel().getSelectedItem();
                }
            }
        });
        equitiesTable.setItems(stockList);
        equitiesTable.getColumns().addAll(tickerSymbol, name,cost, index);
        equitiesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(equitiesTable);

        ((Group) searchScene.getRoot()).getChildren().addAll(vbox);


        scroll.setContent(equitiesTable);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        BorderPane border = new BorderPane();
        border.setCenter(scroll);
        border.setTop(menubar());

        VBox searchMenu = new VBox();
        Button searchButton = new Button("Refine Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RefineSearchScreen p = new RefineSearchScreen();
                ViewControl.thestage.setScene(p.refineSearchScene());
            }
        });

        Button addToWatchlistButton = new Button("Add to Watchlist");
        addToWatchlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddtoWatchlistScreen p = new AddtoWatchlistScreen(selectedStock);
                ViewControl.thestage.setScene(p.AddtoWatchlistScene());
            }
        });

        Label doubleClick = new Label("Double click a stock to buy");
        Label addToWatchlistLabel = new Label("Click on a stock to select it. Then click 'Add to Watchlist' button to add it to your watchlist ");
        searchMenu.getChildren().addAll(searchButton, doubleClick, addToWatchlistButton, addToWatchlistLabel);

        border.setBottom(searchMenu);
        return new Scene(border, 1000, 600);
    }

    /**
     *Updates the shown Stocks based off of what the User refined the search with.
     * @param parsedStockList - The parsed list of all Stocks from the CSV File
     * @return The newly created scene.
     */
    public Scene updatedSearchScene(ArrayList<Stock> parsedStockList){

        ObservableList<Stock> newStockList = FXCollections.observableArrayList(parsedStockList);
        ScrollPane scroll = new ScrollPane();

        Scene searchScene = new Scene(new Group());
        TableView<Stock> equityTable = new TableView<Stock>();
        equityTable.setEditable(true);

        TableColumn tickerSymbol = new TableColumn("Ticker Symbol");
        tickerSymbol.setMinWidth(100);
        tickerSymbol.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("symbol")
        );

        TableColumn name = new TableColumn("Name");
        name.setMinWidth(250);
        name.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("name")
        );

        TableColumn cost = new TableColumn("Cost");
        cost.setMinWidth(250);
        cost.setCellValueFactory(
                new PropertyValueFactory<Stock, Float>("cost")
        );

        TableColumn index = new TableColumn("Index");
        index.setMinWidth(250);
        index.setCellValueFactory(
                new PropertyValueFactory<Stock, String>("index")
        );
        equityTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2  && equityTable.getSelectionModel().getSelectedItem() != null) {
                    TransactionScreen p = new TransactionScreen(equityTable.getSelectionModel().getSelectedItem(), Boolean.TRUE);
                    ViewControl.thestage.setScene(p.TransactionScene());
                }
            }
        });

        equityTable.setItems(newStockList);
        equityTable.getColumns().addAll(tickerSymbol, name, cost, index);

        Label doubleClick = new Label("Double click an Equity to buy it");
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(equityTable);

        ((Group) searchScene.getRoot()).getChildren().addAll(vbox);


        scroll.setContent(equityTable);

        BorderPane border = new BorderPane();
        border.setCenter(scroll);
        border.setTop(menubar());
        border.setBottom(doubleClick);
        return new Scene(border, 1000, 600);
    }

}
