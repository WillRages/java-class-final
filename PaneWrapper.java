import javax.swing.*;
/**
 * The purpose of this file is to ease the creation of JOptionPane
 * functionalities, such as dropdowns, inputs, and messages
 * */
 
public class PaneWrapper {
    public static void say(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static String inputString(String prompt) {
        return JOptionPane.showInputDialog(null, prompt);
    }

    public static Double inputDouble(String prompt) {
        while (true) try {
            String input = JOptionPane.showInputDialog(null, prompt);
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            say("Error: " + e);
        }
    }

    public static Integer inputInt(String prompt) {
        while (true) try {
            String input = JOptionPane.showInputDialog(null, prompt);
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            say("Error: " + e);
        }
    }

    public static void dropdown(String prompt, String defaultValue, String... options) {
        JOptionPane.showInputDialog(null, prompt, null, JOptionPane.QUESTION_MESSAGE, null, options, defaultValue);
    }
}
