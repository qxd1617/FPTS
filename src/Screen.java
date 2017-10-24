import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Created by Matt on 4/3/2016.
 */
public class Screen{

    /**
     * Creates the Menu Bar buttons (Search, Portfolio, Logout)
     * so that the User can go to either the Search page, Portfolio
     * Page, or they can Log Out.
     * @return HBox containing the three buttons.
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
        Button simulation = new Button("Simulation");
        simulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SimulationScreen p = new SimulationScreen(ViewControl.log.getActiveUser().getPortfolio());
                ViewControl.thestage.setScene(p.SimulationScene());
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
        Button orderLog = new Button("Order Log");
        orderLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrderLogScreen p = new OrderLogScreen();
                ViewControl.thestage.setScene(p.OrderLogScene());
            }
        });

        Button watchList = new Button("Watchlist");
        watchList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                WatchlistScreen p = new WatchlistScreen();
                ViewControl.thestage.setScene(p.WatchlistScene());
            }
        });
        menu.getChildren().addAll(portfolio,search,simulation,orderLog, watchList, logout);
        menu.setSpacing(10);
        return menu;
    }
}
