import database.Database;
import ui.LoginManager;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class HamburgerHelperCustomer {
    private static final Database employees = new Database("assets/employees.csv");
    private static final Database inventory = new Database("assets/inventory.csv");

    private static JPanel addEmployeeMenu() {
        var form = new MultiForm(employees::addRow);
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
        var form = new MultiForm(inventory::addRow);

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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hamburger Helper! (Customer)");
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
            }
        });
    }
}
