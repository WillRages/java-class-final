import ui.LoginManager;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HamburgerHelperEmployee extends JPanel { // class start
    private static String name = null;
    private final Runnable logout;

    private static JPanel getOrderMenu() {
        var panel = new JPanel(new GridBagLayout());

        var dataView = PaneWrapper.getFromDatabase(HamburgerHelperMain.orders);

        var nameLabel = new JLabel("Name:");
        var nameField = PaneWrapper.makeStringField("");

        var handle = PaneWrapper.makeButton("Handle Order", e -> {
            var name = nameField.getText();
            nameField.setText("");
            var order = HamburgerHelperMain.orders.getRow(name);

            if (order == null) {
                PaneWrapper.err("No order for name " + name);
                return;
            }

            HamburgerHelperMain.orders.deleteRow(name);

            PaneWrapper.say("Handled order for " + name);
        });

        var c = new GridBagConstraints();

        c.gridheight = 4;
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
        panel.add(handle, c);

        return panel;
    }

    private static JPanel askVacant() {
        var form = new MultiForm(s -> {
            var row = HamburgerHelperMain.employees.getRow(name);

            var hours = s[0];
            var cause = s[1];
            var role = row.getString("Job");

            HamburgerHelperMain.vacancy.addRow(hours, cause, role);
        });

        form.addInput("Requested Hours: ", PaneWrapper.makeIntField(0));
        form.addInput("Cause of the Request: ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    private static JPanel checkInandCheckOut() {
        var panel = new JPanel();
        var checkIn = new JButton("Check in");
        var checkOut = new JButton("Check out");

        checkIn.addActionListener(e -> {
            var dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            var now = LocalDateTime.now();
            String time = dtf.format(now);
        });

        checkOut.addActionListener(e -> {
            var dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            var now = LocalDateTime.now();
            String time = dtf.format(now);
        });
        panel.add(checkIn);
        panel.add(checkOut);

        return panel;
    }

    public void logout() {
        logout.run();
    }

    public HamburgerHelperEmployee() {
        var cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        var pane = new JTabbedPane();
        pane.addTab("Check In/Check Out", HamburgerHelperMain.pinTop(checkInandCheckOut()));
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Handle Orders", getOrderMenu());
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(HamburgerHelperMain.employees));
        pane.addTab("View Inventory", PaneWrapper.getFromDatabase(HamburgerHelperMain.inventory));
        pane.addTab("Ask Vacant Hours", HamburgerHelperMain.pinTop(askVacant()));

        var loginManager = new LoginManager(
                HamburgerHelperMain.employees,
                row -> true,
                nameLogin -> {
                    name = nameLogin;
                    cardLayout.show(this, "MainApp");
                }
        );

        logout = () -> {
            cardLayout.show(this, "Login");
            loginManager.clear();
        };

        this.add(loginManager, "Login");
        this.add(pane, "MainApp");
    }
}
