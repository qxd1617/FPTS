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
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**The RefineSearchScreen Class creates the JavaFX UI for the searching
 * functionality.
 * Created by Matt on 3/13/2016.
 */
public class RefineSearchScreen extends Screen{

    /**
     * Creates the scene for the UI.
     * @return The newly created scene.
     */
    public Scene refineSearchScene(){

        GridPane loginGrid = createLoginGrid();



        BorderPane border = new BorderPane();
        border.setTop(menubar());
        border.setCenter(loginGrid);
        return new Scene(border, 900, 600);
    }

    /**
     * Creates the text boxes that the User uses to enter
     * information to refine the search with as well as
     * implements refining the search.
     * @return GridPane with text boxes and a few buttons.
     */
    public GridPane createLoginGrid(){
        final GridPane loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(5);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(40,40,40,40));

        Label tickerText = new Label("Ticker Symbol");
        loginGrid.add(tickerText, 0, 1);

        final TextField tickerInput = new TextField();
        loginGrid.add(tickerInput, 1, 1);

        Label equityNameText = new Label("Equity Name");
        loginGrid.add(equityNameText, 0, 2);

        Label equityIndexText = new Label("Equity Index");
        loginGrid.add(equityIndexText, 0, 3);

        final TextField indexInput= new TextField();
        loginGrid.add(indexInput, 1, 3);

        final TextField nameInput= new TextField();
        loginGrid.add(nameInput, 1, 2);

        Text title = new Text("Advandced Search Parameters");
        title.setFont(Font.font(20));
        loginGrid.add(title, 1,0);

        Button loginBtn = new Button("Refine Search");
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if ((!tickerInput.getText().isEmpty())&&(!nameInput.getText().isEmpty())&&(!indexInput.getText().isEmpty())){

                    ArrayList searchResults = Search.search(tickerInput.getText(),nameInput.getText(),indexInput.getText());
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if((tickerInput.getText().isEmpty())&&(!nameInput.getText().isEmpty())&&(!indexInput.getText().isEmpty())){
                    ArrayList searchResults = Search.search("",nameInput.getText(),indexInput.getText());
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if((!tickerInput.getText().isEmpty())&&(nameInput.getText().isEmpty())&&(!indexInput.getText().isEmpty())){
                    ArrayList searchResults = Search.search(tickerInput.getText(),"",indexInput.getText());
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if ((!tickerInput.getText().isEmpty())&&(!nameInput.getText().isEmpty())&&(indexInput.getText().isEmpty())){

                    ArrayList searchResults = Search.search(tickerInput.getText(),nameInput.getText(),"");
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if ((tickerInput.getText().isEmpty())&&(nameInput.getText().isEmpty())&&(!indexInput.getText().isEmpty())){

                    ArrayList searchResults = Search.search("","",indexInput.getText());
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if ((tickerInput.getText().isEmpty())&&(!nameInput.getText().isEmpty())&&(indexInput.getText().isEmpty())){

                    ArrayList searchResults = Search.search("",nameInput.getText(),"");
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if ((!tickerInput.getText().isEmpty())&&(nameInput.getText().isEmpty())&&(indexInput.getText().isEmpty())){

                    ArrayList searchResults = Search.search(tickerInput.getText(),"","");
                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.updatedSearchScene(searchResults));
                }
                else if ((tickerInput.getText().isEmpty())&&(nameInput.getText().isEmpty())&&(indexInput.getText().isEmpty())){


                    SearchScreen p = new SearchScreen();
                    ViewControl.thestage.setScene(p.SearchScene());
                }
            }
        });
        loginGrid.add(loginBtn, 1,4);

        return loginGrid;
    }
}

