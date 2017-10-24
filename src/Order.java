/**The Order Interface provides an Interface for the
 * Command Design Pattern.
 * Created by AndrewI on 3/4/2016.
 */

public interface Order {
    void execute();
    void undo();
    Transaction getTransaction();
}


