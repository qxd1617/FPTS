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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.awt.*;
import java.util.Map;

/**
 * Creates a UI page that lets the User see what equities are on their watchlist
 * Created by Matthew D on 4/9/2016.
 */
public class WatchlistScreen extends Screen {

    Equity selectedStock;

    /**
     * Creates the UI Scene for the Watchlist interface
     * Creates a table that contains the User's watchlist and lets the User specify when to update the stock market's prices
     * @return
     */
    public Scene WatchlistScene(){

        final User currentUser = ViewControl.log.getActiveUser();
        ScrollPane watchScroll = new ScrollPane();

        ObservableList<Map.Entry<Equity, WatchData>> watches = FXCollections.observableArrayList(currentUser.portfolio.getWatchlist().watched.entrySet());

        final GridPane watchGrid = new GridPane();
        watchGrid.setAlignment(Pos.CENTER);
        watchGrid.setHgap(5);
        watchGrid.setVgap(10);
        watchGrid.setPadding(new Insets(40, 40, 40, 40));

        final TableView<Map.Entry<Equity, WatchData>> watchTable = new TableView<>(watches);
        watchTable.setEditable(true);

        //Equity, Price, High Trigger, Low Trigger
        TableColumn<Map.Entry<Equity, WatchData>, String> equityCol = new TableColumn("Equity");
        equityCol.setMinWidth(200);
        equityCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey().toString())
        );

        TableColumn<Map.Entry<Equity, WatchData>, String> costCol = new TableColumn<>("Price per Equity");
        costCol.setMinWidth(100);
        costCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey().cost + "")
        );

        TableColumn<Map.Entry<Equity, WatchData>, String> highTriggerCol = new TableColumn<>("High Trigger");
        highTriggerCol.setMinWidth(100);
        highTriggerCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String> p) {
                        if (p.getValue().getValue().high == null) {
                            return new ReadOnlyObjectWrapper<String>("N/A");
                        } else {
                            return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().high.toString());
                        }
                    }
                }
        );

        TableColumn<Map.Entry<Equity, WatchData>, String> lowTriggerCol = new TableColumn<>("Low Trigger");
        lowTriggerCol.setMinWidth(100);
        lowTriggerCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Equity, WatchData>, String> p) {
                        if(p.getValue().getValue().low == null){
                            return new ReadOnlyObjectWrapper<String>("N/A");
                        }
                        else{
                            return new ReadOnlyObjectWrapper<String>(p.getValue().getValue().low.toString());
                        }
                    }
                }
        );

        watchTable.getColumns().addAll(equityCol, costCol, highTriggerCol, lowTriggerCol);
        watchTable.setItems(watches);
        watchTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        watchTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    selectedStock = watchTable.getSelectionModel().getSelectedItem().getKey();
            }
        });

        watchScroll.setContent(watchTable);
        watchScroll.setFitToWidth(true);

        Button removeFromWatchlistButton = new Button("Remove from Watchlist");
        removeFromWatchlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentUser.portfolio.getWatchlist().removeEquity(selectedStock);
                PortfolioScreen p = new PortfolioScreen(currentUser);
                ViewControl.thestage.setScene(p.portfolioScene());
            }
        });

        Label removeFroMWatchlistLabel = new Label("Click a stock to select it. Click 'Remove from Watchlist' button to remove that stock from the watchlist");

        watchGrid.add(watchScroll, 1, 1);
        watchGrid.add(removeFromWatchlistButton, 1, 2);
        watchGrid.add(removeFroMWatchlistLabel, 1, 3);

        VBox timer = new VBox();
        Label time = new Label("Set Time to Update Watchlist (Use Minutes)");
        TextField timeInput = new TextField();
        Button set = new Button("Set");

        set.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!(timeInput.getText().isEmpty())){
                    long l = Long.parseLong(timeInput.getText())*60*1000;
                    UpdateTimer.main(l);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Watchlist timer set to "+timeInput.getText()+" Minutes");
                    alert.setHeaderText("Watchlist timer set to "+timeInput.getText()+" Minutes");
                    alert.showAndWait();
                }
            }
        });

        timer.getChildren().addAll(time,timeInput,set);
        BorderPane border = new BorderPane();
        border.setCenter(watchGrid);
        border.setTop(menubar());
        border.setLeft(timer);

        return new Scene(border, 1000, 600);
    }
}
