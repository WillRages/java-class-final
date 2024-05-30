import ui.LoginManager;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;

public class HamburgerHelperManager extends JPanel {
    private static JPanel addEmployeeMenu() {
        var form = new MultiForm(HamburgerHelperMain.employees::addRow);

        form.addInput("Name: ", PaneWrapper.makeStringField(""));
        form.addInput("Role: ", PaneWrapper.makeStringField(""));
        form.addInput("Wage: ", PaneWrapper.makeIntField(0));
        form.addInput("Passkey: ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    private static JPanel addItemMenu() {
        var form = new MultiForm(HamburgerHelperMain.inventory::addRow);

        form.addInput("Item Type: ", PaneWrapper.makeStringField(""));
        form.addInput("Amount: ", PaneWrapper.makeStringField(""));
        form.addInput("Units (kg, oz, etc.): ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    private static JPanel addRequestMenu() {
        var panel = new JPanel(new GridBagLayout());
        var dataView = PaneWrapper.getFromDatabase(HamburgerHelperMain.vacancy);

        var nameLabel = new JLabel("Name:");
        var nameField = PaneWrapper.makeStringField("");

        var approve = PaneWrapper.makeButton("Approve", e -> {
            var name = nameField.getText();

            var request = HamburgerHelperMain.vacancy.getRow(name);
            var employee = HamburgerHelperMain.employees.getRow(name);

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

            HamburgerHelperMain.employees.addRow(
                    employee.getString("Name"), employee.getString("Job"), employee.getString("Wage"),
                    employee.getString("Passkey"), "" + vacancy
            );

            HamburgerHelperMain.vacancy.deleteRow(nameField.getText());

            nameField.setText("");
            PaneWrapper.say("Approved");
        });

        var deny = PaneWrapper.makeButton("Deny", e -> {
            HamburgerHelperMain.vacancy.deleteRow(nameField.getText());

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

    public HamburgerHelperManager() {
        var cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        JTabbedPane pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Employee", HamburgerHelperMain.pinTop(addEmployeeMenu()));
        pane.addTab("Clear Employees", PaneWrapper.makeButton("Clear", e -> {
            boolean choice = PaneWrapper.checkbox("Really clear employee data?");
            if (choice) {
                HamburgerHelperMain.employees.clear();
                PaneWrapper.say("Cleared employee table.");
            }
        }));

        pane.addTab("View Employees", PaneWrapper.getFromDatabase(HamburgerHelperMain.employees));
        pane.addTab("Order Item", HamburgerHelperMain.pinTop(addItemMenu()));
        pane.addTab("View Inventory", PaneWrapper.getFromDatabase(HamburgerHelperMain.inventory));
        pane.addTab("Time Off Requests", HamburgerHelperMain.pinTop(addRequestMenu()));

        LoginManager login = new LoginManager(
                HamburgerHelperMain.employees,
                row -> {
                    var valid = row.getString("Job").equals("Manager");
                    if (!valid) PaneWrapper.err("User " + row.getString("Name") + " is not a manager");
                    return valid;
                },
                name -> cardLayout.show(this, "MainApp")
        );

        this.add(login, "Login");
        this.add(pane, "MainApp");
    }
}
