import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Creates the UI for adding an equity to the User's watchlist
 * User can fill out the high and low trigger inputs to specify when to alert the user
 * Created by Matthew D on 4/9/2016.
 */
public class AddtoWatchlistScreen extends Screen{

    Equity stock;

    /**
     * Sets the Equity to be added to the watchlist
     * @param stock - Equity to be added to the watchlist
     */
    public AddtoWatchlistScreen(Equity stock){
        this.stock = stock;
    }

    /**
     * Creates the UI page for adding to the watchlist
     * Provides input areas for high and low trigger points
     * @return Scene of the AddtoWatchlist page
     */
    public Scene AddtoWatchlistScene(){
        final User currentUser = ViewControl.log.getActiveUser();

        final GridPane addWatchGrid = new GridPane();
        addWatchGrid.setAlignment(Pos.CENTER);
        addWatchGrid.setHgap(5);
        addWatchGrid.setVgap(10);
        addWatchGrid.setPadding(new Insets(40, 40, 40, 40));

        Text title = new Text("Add " + stock + " to the Watchlist");
        title.setFont(Font.font(20));
        addWatchGrid.add(title, 1, 0);

        Label highTriggerLabel = new Label("High Trigger: ");
        addWatchGrid.add(highTriggerLabel, 0, 1);

        final NumberTextField highTriggerField = new NumberTextField();
        addWatchGrid.add(highTriggerField, 1, 1);

        Label lowTriggerLabel = new Label("Low Trigger: ");
        addWatchGrid.add(lowTriggerLabel, 0, 2);

        final NumberTextField lowTriggerField = new NumberTextField();
        addWatchGrid.add(lowTriggerField, 1, 2);

        Button addToWatchlistButton = new Button("Add to Watchlist");
        addToWatchlistButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(highTriggerField.getText().isEmpty() && lowTriggerField.getText().isEmpty()){
                    currentUser.portfolio.getWatchlist().addEquity(stock, null);
                    PortfolioScreen p = new PortfolioScreen(currentUser);
                    ViewControl.thestage.setScene(p.getScene());
                }
                else if(!highTriggerField.getText().isEmpty() && lowTriggerField.getText().isEmpty()){
                    if(Float.parseFloat(highTriggerField.getText()) < stock.cost){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("High Trigger is Smaller than Current Stock Price");
                        alert.setHeaderText("High Trigger is Smaller than Current Stock Price");
                        alert.setContentText("Please make sure that the high trigger is larger than the current stock price.");
                        alert.showAndWait();
                    }
                    else {
                        WatchData newData = new WatchData(Float.parseFloat(highTriggerField.getText()), null);
                        currentUser.portfolio.getWatchlist().addEquity(stock, newData);
                        PortfolioScreen p = new PortfolioScreen(currentUser);
                        ViewControl.thestage.setScene(p.getScene());
                    }
                }
                else if(highTriggerField.getText().isEmpty() && !lowTriggerField.getText().isEmpty()){
                    WatchData newData = new WatchData(null ,Float.parseFloat(lowTriggerField.getText()));
                    currentUser.portfolio.getWatchlist().addEquity(stock, newData);
                    PortfolioScreen p = new PortfolioScreen(currentUser);
                    ViewControl.thestage.setScene(p.getScene());
                }
                else{
                    if(Float.parseFloat(highTriggerField.getText()) < Float.parseFloat(lowTriggerField.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Low Trigger Larger than High Trigger");
                        alert.setHeaderText("Low Trigger Larger than High Trigger");
                        alert.setContentText("Please make sure that the high trigger is larger than the low trigger.");
                        alert.showAndWait();
                    }
                    else {
                        if(Float.parseFloat(highTriggerField.getText()) < stock.cost){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("High Trigger is Smaller than Current Stock Price");
                            alert.setHeaderText("High Trigger is Smaller than Current Stock Price");
                            alert.setContentText("Please make sure that the high trigger is larger than the current stock price.");
                            alert.showAndWait();
                        }
                        else {
                            WatchData newData = new WatchData(Float.parseFloat(highTriggerField.getText()), Float.parseFloat(lowTriggerField.getText()));
                            currentUser.portfolio.getWatchlist().addEquity(stock, newData);
                            PortfolioScreen p = new PortfolioScreen(currentUser);
                            ViewControl.thestage.setScene(p.getScene());
                        }
                    }
                }
            }
        });

        addWatchGrid.add(addToWatchlistButton, 1, 4);

        BorderPane border = new BorderPane();
        border.setCenter(addWatchGrid);
        border.setTop(menubar());

        return new Scene(border, 1000, 600);
    }
}
