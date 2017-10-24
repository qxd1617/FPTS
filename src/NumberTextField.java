import javafx.scene.control.TextField;

/**
 * Represents an input field that only numbers can be entered into
 */
public class NumberTextField extends TextField {

    @Override
    public void replaceText(int start, int end, String text) {
        if (validate(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (validate(text)) {
            super.replaceSelection(text);
        }
    }

    /**
     * Checks to see if the supplied text matches the regular expression
     * @param text - String to check to see if it matches the regular expression
     * @return
     */
    private boolean validate(String text) {
        return text.matches("([0-9])*(.)*([0-9])*");
    }
}
