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
import javafx.scene.layout.HBox;

import javax.swing.text.View;

/**
 * Creates a UI page for removing a cash account from a User's holdings list
 * Created by Matthew D on 4/5/2016.
 */
public class RemoveCashAccountScreen extends Screen{

    CashAccount account = null;

    /**
     * Creates a UI page for interfacing with the cash accounts and removing them
     * @return
     */
    public Scene removeCashAccountScreen(){

        final User currentUser = ViewControl.log.getActiveUser();

        ScrollPane scrollCash = new ScrollPane();
        ObservableList accs = FXCollections.observableArrayList(currentUser.portfolio.ownedCash);
        final GridPane remCashGrid = new GridPane();
        remCashGrid.setAlignment(Pos.CENTER);
        remCashGrid.setHgap(5);
        remCashGrid.setVgap(10);
        remCashGrid.setPadding(new Insets(40, 40, 40, 40));

        final TableView<CashAccount> cashAccounts = new TableView<CashAccount>();
        cashAccounts.setEditable(true);
        TableColumn nameCol = new TableColumn("Name");
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
        cashAccounts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cashAccounts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                account = cashAccounts.getSelectionModel().getSelectedItem();
            }
        });

        scrollCash.setContent(cashAccounts);

        Button removeButton = new Button("Remove Cash Account");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentUser.portfolio.removeCashAccount(account);
                ViewControl.log.setActiveUser(currentUser);
                PortfolioScreen p = new PortfolioScreen(currentUser);
                ViewControl.thestage.setScene(p.getScene());
            }
        });

        remCashGrid.add(scrollCash, 1, 1);
        remCashGrid.add(removeButton, 1, 2);

        BorderPane main = new BorderPane();
        main.setCenter(remCashGrid);
        main.setTop(menubar());

        return new Scene(main, 1000, 600);
    }
}
