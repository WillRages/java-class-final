import database.Database;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class HamburgerHelperEmployee { // class start

    public static Database employees = new Database("assets/employees.csv");
    public static Database inventory = new Database("assets/inventory.csv");
    public static Database orders = new Database("assets/orders.csv");
    public static Database vacancy = new Database("assets/vacantRequest.csv");

    public static JPanel addOrder() {
        var form = new MultiForm(s -> {
            orders.addRow(s);
            PaneWrapper.say(Arrays.toString(s));
        });

        form.addInput("Order: ", PaneWrapper.makeStringField(""));
        form.addInput("Address: ", PaneWrapper.makeStringField(""));
        form.addInput("Price: ", PaneWrapper.makeIntField(0));

        form.addButtons();

        return form;
    }

    public static JPanel askVacant() {
        var form = new MultiForm(s -> {
            var row = employees.getRow(s[0]);

            if (row == null) {
                PaneWrapper.err("No employee named " + s[0]);
                return;
            }

            var strings = new String[s.length + 1];
            System.arraycopy(s, 0, strings, 0, s.length);
            strings[s.length] = row.getString("Job");
            vacancy.addRow(strings);
            PaneWrapper.say(Arrays.toString(s));
        });

        form.addInput("Employee's Name: ", PaneWrapper.makeStringField(""));
        form.addInput("Requested Hours: ", PaneWrapper.makeIntField(0));
        form.addInput("Cause of the Request: ", PaneWrapper.makeStringField(""));

        form.addButtons();

        return form;
    }

    public static JPanel pinTop(JPanel panel) { // pinTop start
        var wrapper = new JPanel(new BorderLayout());
        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    } // pinTop end

    public static void main(String[] args) { // main start
        JFrame frame = new JFrame("Hamburger Helper Employee");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Add Orders", pinTop(addOrder()));
        pane.addTab("View Orders", PaneWrapper.getFromDatabase(orders));
        pane.addTab("View Employees", PaneWrapper.getFromDatabase(employees));
        pane.addTab("View Items", PaneWrapper.getFromDatabase(inventory));
        pane.addTab("Ask Vacant Hours", pinTop(askVacant()));

        frame.getContentPane().add(pane);
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setLocationRelativeTo(null);

        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                orders.writeToFile();
                vacancy.writeToFile();
            }
        });
    } // main end
} // class end
