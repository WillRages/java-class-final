import database.Database;
import ui.LoginManager;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HamburgerHelperManager {
    private static final Database employees = new Database("assets/employees.csv");
    private static final Database inventory = new Database("assets/inventory.csv");
    private static final Database requests = new Database("assets/vacantRequest.csv");

    private static JPanel addEmployeeMenu() {
        var form = new MultiForm(employees::addRow);

        form.addInput("Name: ", PaneWrapper.makeStringField(""));
        form.addInput("Role: ", PaneWrapper.makeStringField(""));
        form.addInput("Wage: ", PaneWrapper.makeIntField(0));
        form.addInput("Passkey: ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    private static JPanel addItemMenu() {
        var form = new MultiForm(inventory::addRow);

        form.addInput("Item Type: ", PaneWrapper.makeStringField(""));
        form.addInput("Amount: ", PaneWrapper.makeStringField(""));
        form.addInput("Units (kg, oz, etc.): ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    private static JPanel addRequestMenu() {
        var panel = new JPanel(new GridBagLayout());
        var dataView = PaneWrapper.getFromDatabase(requests);

        var nameLabel = new JLabel("Name:");
        var nameField = PaneWrapper.makeStringField("");

        var approve = PaneWrapper.makeButton("Approve", e -> {
            var name = nameField.getText();

            var request = requests.getRow(name);
            var employee = employees.getRow(name);

            if (employee == null) {
                PaneWrapper.err("No employee named " + nameField.getText());
                return;
            }

            if (request == null) {
                PaneWrapper.err("No request for employee " + nameField.getText());
                return;
            }

            var vacancy = employee.getDouble("Vacancy");
            vacancy += request.getDouble("Hours");

            employees.addRow(
                    employee.getString("Name"), employee.getString("Job"), employee.getString("Wage"),
                    employee.getString("Passkey"), "" + vacancy
            );

            requests.deleteRow(nameField.getText());

            nameField.setText("");
            PaneWrapper.say("Approved");
        });

        var deny = PaneWrapper.makeButton("Deny", e -> {
            requests.deleteRow(nameField.getText());

            nameField.setText("");
            PaneWrapper.say("Denied D:");
        });

        var c = new GridBagConstraints();

        c.gridheight = 5;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(dataView, c);

        c.gridheight = 1;

        c.gridx = 1;
        panel.add(nameLabel, c);
        c.gridy = 1;
        panel.add(nameField, c);
        c.gridy = 2;
        panel.add(approve, c);
        c.gridy = 3;
        panel.add(deny, c);

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

        var cardLayout = new CardLayout();
        frame.getContentPane().setLayout(cardLayout);

        JTabbedPane pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Employee", pinTop(addEmployeeMenu()));
        pane.addTab("Clear Employees", PaneWrapper.makeButton("Clear", e -> {
            boolean choice = PaneWrapper.checkbox("Really clear employee data?");
            if (choice) {
                employees.clear();
                PaneWrapper.say("Cleared employee table.");
            }
        }));
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(employees));
        pane.addTab("Order Item", pinTop(addItemMenu()));
        pane.addTab("View Inventory", PaneWrapper.getFromDatabase(inventory));
        pane.addTab("Time Off Requests", pinTop(addRequestMenu()));

        LoginManager login = new LoginManager("assets/employees.csv", () -> {
            cardLayout.show(frame.getContentPane(), "MainApp");
        });

        frame.getContentPane().add(login, "Login");
        frame.getContentPane().add(pane, "MainApp");
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                employees.writeToFile();
                inventory.writeToFile();
                requests.writeToFile();
            }
        });
    }
}
