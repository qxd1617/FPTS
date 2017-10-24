import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Date;
import java.util.Map;

/**
 * UI page that provides an interface for Users to see their past transactions
 * Created by Matthew D on 4/6/2016.
 */
public class OrderLogScreen extends Screen {

    /**
     * Creates the UI Scene for the OrderLog interface
     * Creates a table and fills it out with the Current Users past transactions
     * @return
     */
    public Scene OrderLogScene(){

        final User currentUser = ViewControl.log.getActiveUser();
        ScrollPane orderScroll = new ScrollPane();

        ObservableList orders = FXCollections.observableArrayList(currentUser.portfolio.OrderHistory);

        final GridPane orderLogGrid = new GridPane();
        orderLogGrid.setAlignment(Pos.CENTER);
        orderLogGrid.setHgap(5);
        orderLogGrid.setVgap(10);
        orderLogGrid.setPadding(new Insets(40, 40, 40, 40));

        final TableView<OrderLog> orderTable = new TableView<OrderLog>(orders);
        orderTable.setEditable(true);


        TableColumn dateCol = new TableColumn("Date");
        dateCol.setMinWidth(150);
        dateCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderLog, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderLog, String> p) {
                        return new ReadOnlyObjectWrapper<String>(p.getValue().date.toString());
                    }
                }
        );


        TableColumn equityCol = new TableColumn("Equity");
        equityCol.setMinWidth(150);
        equityCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderLog, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderLog, String> p) {
                        return new ReadOnlyObjectWrapper<String>(p.getValue().transaction.eq.toString());
                    }
                }
        );

        TableColumn amountCol = new TableColumn("Amount Bought/Sold");
        amountCol.setMinWidth(150);
        amountCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderLog, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderLog, String> p) {
                        return new ReadOnlyObjectWrapper<String>(p.getValue().transaction.count + "");
                    }
                }
        );

        TableColumn costCol = new TableColumn<>("Total Cost");
        costCol.setMinWidth(150);
        costCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderLog, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderLog, String> p) {
                        float x = Math.round(p.getValue().transaction.cost * p.getValue().transaction.count * 100f)/100f;
                        return new ReadOnlyObjectWrapper<String>(x + "");
                    }
                }
        );

        TableColumn accCol = new TableColumn<>("Cash Account Used");
        accCol.setMinWidth(150);
        accCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OrderLog, String>, ObservableValue<String>>() {
                    @Override
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<OrderLog, String> p) {
                        return new ReadOnlyObjectWrapper<String>(p.getValue().transaction.cash.getName());
                    }
                }
        );

        orderTable.getColumns().addAll(dateCol, equityCol, amountCol,  costCol, accCol);
        orderTable.setItems(orders);
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        orderScroll.setContent(orderTable);
        orderScroll.setFitToWidth(true);

        orderLogGrid.add(orderScroll, 1, 1);

        BorderPane border = new BorderPane();
        border.setCenter(orderLogGrid);
        border.setTop(menubar());

        return new Scene(border, 1000, 600);
    }
}
