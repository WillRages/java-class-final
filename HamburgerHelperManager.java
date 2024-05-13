import database.Database;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HamburgerHelperManager {
    public static Database employees = new Database("assets/employees.csv");
    public static Database inventory = new Database("assets/inventory.csv");

    public static JPanel addEmployeeMenu() {
        JLabel nameLabel = new JLabel("Name: ");
        JLabel roleLabel = new JLabel("Role: ");
        JLabel wageLabel = new JLabel("Wage: ");

        JTextField nameInput = PaneWrapper.makeStringField("");
        JTextField roleInput = PaneWrapper.makeStringField("");
        JTextField wageInput = PaneWrapper.makeIntField(0);

        JButton add = PaneWrapper.makeButton("Add", e -> {
            String name = nameInput.getText();
            String role = roleInput.getText();
            String wage = wageInput.getText();

            nameInput.setText("");
            roleInput.setText("");
            wageInput.setText("0");

            employees.addRow(name, role, wage);
        });
        JButton cancel = PaneWrapper.makeButton("Cancel", e -> {
            nameInput.setText("");
            roleInput.setText("");
            wageInput.setText("0");
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        // add labels for text boxes
        c.gridy = 0;
        panel.add(nameLabel, c);
        c.gridy = 1;
        panel.add(roleLabel, c);
        c.gridy = 2;
        panel.add(wageLabel, c);

        // add buttons
        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(add, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        panel.add(cancel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridy = 0;
        panel.add(nameInput, c);
        c.gridy = 1;
        panel.add(roleInput, c);
        c.gridy = 2;
        panel.add(wageInput, c);

        return panel;
    }

    public static JPanel addItemMenu() {
        JLabel typeLabel = new JLabel("Item Type: ");
        JLabel amountLabel = new JLabel("Amount: ");
        JLabel unitLabel = new JLabel("Units (kg, oz, etc.): ");

        JTextField typeInput = PaneWrapper.makeStringField("", s -> {
        });
        JTextField amountInput = PaneWrapper.makeStringField("", s -> {
        });
        JTextField unitInput = PaneWrapper.makeStringField("", s -> {
        });

        JButton add = PaneWrapper.makeButton("Add", e -> {
            String type = typeInput.getText();
            String amount = amountInput.getText();
            String units = unitInput.getText();

            inventory.addRow(type, amount, units);
        });
        JButton cancel = PaneWrapper.makeButton("Cancel", e -> {
            typeInput.setText("");
            amountInput.setText("");
            unitInput.setText("");
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        c.gridy = 0;
        panel.add(typeLabel, c);
        c.gridy = 1;
        panel.add(amountLabel, c);
        c.gridy = 2;
        panel.add(unitLabel, c);

        c.gridy = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(add, c);
        c.gridx = 1;
        c.fill = GridBagConstraints.NONE;
        panel.add(cancel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridy = 0;
        panel.add(typeInput, c);
        c.gridy = 1;
        panel.add(amountInput, c);
        c.gridy = 2;
        panel.add(unitInput, c);

        return panel;
    }

    public static JPanel pinTop(JPanel panel) {
        var wrapper = new JPanel(new BorderLayout());

        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Employee", pinTop(addEmployeeMenu()));
        pane.addTab("Add Item", pinTop(addItemMenu()));
        pane.addTab("Clear Employees", PaneWrapper.makeButton("Clear", e -> {
            boolean choice = PaneWrapper.checkbox("Really clear employee data?");
            if (choice) employees.clear();
        }));
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(employees));

        frame.getContentPane().add(pane);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                employees.writeToFile();
                inventory.writeToFile();
            }
        });
    }
}
