import javax.swing.*;
/**
 * The purpose of this file is to ease the creation of JOptionPane
 * functionalities, such as dropdowns, inputs, and messages
 * */
 
public class PaneWrapper {
    public static String windowName = "";

    public static void say(String message) {
        JOptionPane.showMessageDialog(null, message, windowName, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void err(String message) {
        JOptionPane.showMessageDialog(null, message, windowName, JOptionPane.ERROR_MESSAGE);
    }

    public static String inputString(String prompt) {
        return JOptionPane.showInputDialog(null, prompt, windowName, JOptionPane.INFORMATION_MESSAGE);
    }

    public static Double inputDouble(String prompt) {
        while (true) try {
            String input = inputString(prompt);
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            err("Error: " + e);
        }
    }

    public static Integer inputInt(String prompt) {
        while (true) try {
            String input = inputString(prompt);
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            err("Error: " + e);
        }
    }

    public static void dropdown(String prompt, String defaultValue, String... options) {
        JOptionPane.showInputDialog(null, prompt, null, JOptionPane.QUESTION_MESSAGE, null, options, defaultValue);
    }
}
