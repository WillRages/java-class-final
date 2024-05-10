package ui;

import database.Database;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.function.Consumer;

/**
 * The purpose of this file is to ease the creation of JOptionPane
 * functionalities, such as dropdowns, inputs, and messages
 */

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
        JOptionPane.showInputDialog(null, prompt, windowName, JOptionPane.QUESTION_MESSAGE, null, options, defaultValue);
    }

    public static Boolean checkbox(String prompt) {
        int r = JOptionPane.showConfirmDialog(null, prompt, windowName, JOptionPane.YES_NO_OPTION);
        return r == 0;
    }

    public static JButton makeButton(String text, Consumer<ActionEvent> onClick) {
        JButton button = new JButton();

        button.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onClick.accept(e);
            }
        });

        button.setText(text);

        return button;
    }

    public static JTextField makeStringField(String initial, Consumer<String> onSubmit) {
        JTextField field = new JTextField(initial);

        field.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSubmit.accept(field.getText());
            }
        });

        return field;
    }


    public static JFormattedTextField makeIntField(int initial, Consumer<Integer> onSubmit) {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);

        NumberFormatter numberFormatter = new NumberFormatter(format);
        numberFormatter.setValueClass(Long.class);
        numberFormatter.setAllowsInvalid(false); //this is the key

        JFormattedTextField field = new JFormattedTextField(numberFormatter);
        field.setText("" + initial);

        field.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSubmit.accept(Integer.parseInt(field.getText()));
            }
        });

        return field;
    }

    public static JScrollPane getFromDatabase(Database database) {
        JTable table = new JTable();
        TableModel model = database.getModel();
        table.setModel(model);

        return new JScrollPane(table);
    }
}
