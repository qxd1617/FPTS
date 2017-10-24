import javafx.scene.control.Alert;

/**
 * Created by Owen on 4/7/2016.
 */
public class Update implements Visitor {

    public void visit(Stock obj){
        try {
            obj.cost = WebServices.Update(obj.getSymbol());
            if (Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj) != null){
                Login.getActiveUser().getPortfolio().getWatchlist().check(obj);

                Boolean highTrigger = Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj).highFlag;
                Boolean lowTrigger = Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj).lowFlag;

                if(highTrigger){
                    Alert highAlert = new Alert(Alert.AlertType.INFORMATION);
                    highAlert.setTitle("High Trigger for " + obj.name);
                    highAlert.setHeaderText("High Trigger for " + obj.name);
                    highAlert.setContentText("The high trigger of " + Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj).high + " for " + obj.name + " has been triggered.");
                    highAlert.showAndWait();
                }
                else if(lowTrigger){
                    Alert lowAlert = new Alert(Alert.AlertType.INFORMATION);
                    lowAlert.setTitle("Low Trigger for " + obj.name);
                    lowAlert.setHeaderText("Low Trigger for " + obj.name);
                    lowAlert.setContentText("The low trigger of " + Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj).high + " for " + obj.name + " has been triggered.");
                    lowAlert.showAndWait();
                }
            }
        }
        catch (Exception e){}
    }

    public void visit(Index obj){
        obj.calculateCost();
        if (Login.getActiveUser().getPortfolio().getWatchlist().watched.get(obj) != null){
            Login.getActiveUser().getPortfolio().getWatchlist().check(obj);
        }
    }
}
