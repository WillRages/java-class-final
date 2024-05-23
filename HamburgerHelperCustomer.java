import database.Database;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;


public class HamburgerHelperCustomer extends JPanel {
    private static JPanel addEmployeeMenu() {
        var form = new MultiForm(HamburgerHelperMain.employees::addRow);
        JButton button = new JButton("Starve >:3");

        form.addInput("Name: ", PaneWrapper.makeStringField("Nugget"));
        form.addInput("Role: ", PaneWrapper.makeStringField("Nugget"));
        form.addInput("Wage: ", PaneWrapper.makeIntField(0));
        form.addInput("Passkey: ", PaneWrapper.makeStringField("Nugget"));
        form.add(button);


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

    public static JPanel pinTop(JPanel panel) {
        var wrapper = new JPanel(new BorderLayout());

        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    }

    public HamburgerHelperCustomer() {
        var cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        JTabbedPane pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Employee", pinTop(addEmployeeMenu()));
        pane.addTab("Clear Employees", PaneWrapper.makeButton("Clear", e -> {
            boolean choice = PaneWrapper.checkbox("Really clear employee data?");
            if (choice) {
                HamburgerHelperMain.employees.clear();
                PaneWrapper.say("Cleared employee table.");
            }
        }));
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(HamburgerHelperMain.employees));
        pane.addTab("Order Item", pinTop(addItemMenu()));
        pane.addTab("View Inventory", PaneWrapper.getFromDatabase(HamburgerHelperMain.inventory));

        this.add(pane, "MainApp");
    }
}
