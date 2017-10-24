/**The Broker Class contains an ArrayList of Order Objects, the functionality to add
 * Order Objects to the ArrayList, and then the functionality to execute and then clear
 * all of the Order Objects in the ArrayList.
 * Created by Owen on 3/4/2016.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Broker {

    /**
     * ArrayList that contains a queue of Order Objects to be executed.
     */
    private static List<Order> orderList = new ArrayList<Order>();

    public static Stack<Order> undoStack = new Stack();
    public static Stack<Order> redoStack = new Stack();
    /**
     *Adds new Order Objects to the ArrayList of Orders to be executed.
     * @param order - Order to be added to ArrayList
     */
    public static void takeOrder(Order order){
        orderList.add(order);
    }

    /**
     * Executes all Order Objects in the ArrayList then clears the ArrayList.
     */
    public static void placeOrders(){

        for (Order order : orderList) {
            undoStack.push(order);
            order.execute();
        }
        orderList.clear();
    }

    public static void undoOrder(){
        if(undoStack.size() >0) {
            Order action = undoStack.pop();
            redoStack.push(action);
            action.undo();

        }
    }

    public static void redoOrder(){
        if(redoStack.size()>0) {
            Order action = redoStack.pop();
            try {
                if (action.getTransaction().eq.cost == action.getTransaction().cost) {
                    undoStack.push(action);
                    action.execute();
                } else {
                    System.out.println("Order not redone due to changes in price");
                }
            }
            catch(NullPointerException e){
                undoStack.push(action);
                action.execute();
            }
        }
    }
}

