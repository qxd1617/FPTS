import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates the UI interface for a User to run a simulation on their portfolio
 * User can specify the growth or decay rate of their holdings
 * Created by Matt C on 4/4/2016.
 */
public class SimulationScreen extends Screen{
    Simulation currentSim;
    ScrollPane scrollStock;
    TableView<Map.Entry<Equity, Float>> tableStocks;
    int numberColumns;

    /**
     * Sets the portfolio that will be simulated on
     * @param holds
     */
    public SimulationScreen(Portfolio holds){
        User currentUser = ViewControl.log.getActiveUser();
        numberColumns = currentUser.portfolio.ownedEquities.size();
        currentSim = new Simulation(copyPortfolio(currentUser.portfolio.ownedEquities));
    }

    /**
     * Test function used to see how shallow copying of a hashmap would affect the results of the system
     * @param port - HashMap to make a copy of
     * @return - Shallow copy of the HashMap input
     */
    public HashMap<Equity,Float> copyPortfolio(HashMap<Equity,Float> port){
        HashMap<Equity,Float> newPort = new HashMap<>();
        for(Equity e : port.keySet()){
            newPort.put(e,port.get(e));
        }
        return newPort;
    }

    /**
     * Creates the table that is filled with the User's equities
     * Table will be used to display the results of each phase of the simulation
     */
    public void SimEquities(){
        scrollStock = new ScrollPane();
        ObservableList<Map.Entry<Equity,Float>> stocks = FXCollections.observableArrayList(currentSim.currentPortfolio.entrySet());

        tableStocks = new TableView<Map.Entry<Equity, Float>>(stocks);
        TableColumn<Map.Entry<Equity, Float>,String> nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey().name));

        TableColumn<Map.Entry<Equity, Float>,String> priceCol = new TableColumn("Price");
        priceCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(""+p.getValue().getKey().cost));

        TableColumn<Map.Entry<Equity, Float>,String> ownedCol = new TableColumn("Amount Owned");
        ownedCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(p.getValue().getValue().toString()));

        TableColumn<Map.Entry<Equity, Float>,String> valueCol = new TableColumn("Total Value");
        valueCol.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Equity, Float>, String> p) ->
                        new SimpleStringProperty(""+(p.getValue().getKey().cost *p.getValue().getValue())));

        tableStocks.getColumns().addAll(nameCol,priceCol,ownedCol,valueCol );
        tableStocks.setEditable(true);
        scrollStock.setContent(tableStocks);


    }

    public void updateTable(){
        for(int i = 0;i < numberColumns; i++){
            tableStocks.getColumns().get(i).setVisible(false);
            tableStocks.getColumns().get(i).setVisible(true);
        }
    }

    /**
     * Creates the UI page for the simulation
     * Creates input fields and choice boxes for the user to specify what simulation to run
     * @return UI page
     */
    public Scene SimulationScene(){
        final GridPane simulationGrid = new GridPane();
        simulationGrid.setAlignment(Pos.CENTER);
        simulationGrid.setHgap(5);
        simulationGrid.setVgap(10);
        simulationGrid.setPadding(new Insets(40,40,40,40));

        Label Percentage = new Label("Yearly Growth/Decay Percent (Use Decimal Format)");
        simulationGrid.add(Percentage, 0, 1);

        final TextField PercentageInput = new TextField();
        simulationGrid.add(PercentageInput, 1, 1);

        Label market = new Label("Market Type");
        simulationGrid.add(market, 0, 2);

        ChoiceBox<String> marketChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Bull","Bear"));
        marketChoice.getSelectionModel().selectFirst();
        simulationGrid.add(marketChoice, 1, 2);

        Label time = new Label("Time Duration");
        simulationGrid.add(time, 0, 3);

        ChoiceBox<String> timeChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Day","Month"));
        timeChoice.getSelectionModel().selectFirst();
        simulationGrid.add(timeChoice, 1, 3);

        Text title = new Text("Simulation of Portfolio Growth/Decay");
        title.setFont(Font.font(20));
        simulationGrid.add(title, 1,0);



        Button simulateBtn = new Button("Simulate");
        simulateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((!PercentageInput.getText().isEmpty())){
                        currentSim.chooseParameters(marketChoice.getValue(),timeChoice.getValue(),Float.parseFloat(PercentageInput.getText()));
                        currentSim.Simulate();
                        updateTable();


                    /*
                    catch(NumberFormatException e){
                        message.setText("Please use Decimal Format. Examples: 1.0, .86, .45");
                    }
                    */
                }
            }
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentSim.Reset();
                tableStocks.setItems(FXCollections.observableArrayList(currentSim.currentPortfolio.entrySet()));
                updateTable();
            }
        });

        HBox buttons = new HBox(simulateBtn,resetBtn);
        buttons.setSpacing(10);

        simulationGrid.add(buttons, 1,4);


        BorderPane border = new BorderPane();
        border.setCenter(simulationGrid);
        border.setTop(menubar());

        SimEquities();
        border.setRight(scrollStock);
        return new Scene(border,1000,600);
    }
}
