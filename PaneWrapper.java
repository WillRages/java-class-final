import javax.swing.*;
/**
 * The purpose of this file is to ease the creation of JOptionPane
 * functionalities, such as dropdowns, inputs, and messages
 * */
 
public class PaneWrapper {
    public static void say(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void input(String prompt) {
        JOptionPane.showInputDialog(null, prompt);
    }

    public static void dropdown(String prompt, String defaultValue, String... options) {
        JOptionPane.showInputDialog(null, prompt, null, JOptionPane.QUESTION_MESSAGE, null, options, defaultValue);
    }
}
