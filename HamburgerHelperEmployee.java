import ui.LoginManager;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HamburgerHelperEmployee extends JPanel { // class start
    private static String name = null;

    private static JPanel addOrder() {
        var form = new MultiForm(s -> {
            HamburgerHelperMain.orders.addRow(s);
            PaneWrapper.say(Arrays.toString(s));
        });

        form.addInput("Main Dish: ", PaneWrapper.makeStringField(""));
        form.addInput("Side Dish: ", PaneWrapper.makeStringField(""));
        form.addInput("Drink: ", PaneWrapper.makeStringField(""));
        form.addInput("Address: ", PaneWrapper.makeStringField(""));
        form.addInput("Price: ", PaneWrapper.makeIntField(0));

        form.addButtons();

        return form;
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

    public HamburgerHelperEmployee() {
        var cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        var pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Orders", HamburgerHelperMain.pinTop(addOrder()));
        pane.addTab("View Orders", PaneWrapper.getFromDatabase(HamburgerHelperMain.orders));
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(HamburgerHelperMain.employees));
        pane.addTab("View Items", PaneWrapper.getFromDatabase(HamburgerHelperMain.inventory));
        pane.addTab("Ask Vacant Hours", HamburgerHelperMain.pinTop(askVacant()));

        var loginManager = new LoginManager(
                HamburgerHelperMain.employees,
                row -> true,
                nameLogin -> {
                    name = nameLogin;
                    cardLayout.show(this, "MainApp");
                }
        );

        this.add(loginManager, "Login");
        this.add(pane, "MainApp");
    }
}
