import database.Database;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HamburgerHelperMain {
    public static Database employees = new Database("assets/employees.csv");
    public static Database inventory = new Database("assets/inventory.csv");
    public static Database orders = new Database("assets/orders.csv");
    public static Database vacancy = new Database("assets/vacantRequest.csv");

    public static JPanel pinTop(JPanel panel) {
        var wrapper = new JPanel(new BorderLayout());
        wrapper.add(panel, BorderLayout.NORTH);
        return wrapper;
    }

    public static void main(String[] args) {
        var frame = new JFrame();

        var panel = new JPanel();
        var cardLayout = new CardLayout();
        panel.setLayout(cardLayout);

        var manager = new HamburgerHelperManager();
        var employee = new HamburgerHelperEmployee();
        var customer = new HamburgerHelperCustomer();

        panel.add(manager, "Manager");
        panel.add(employee, "Employee");
        panel.add(customer, "Customer");

        var multibox = PaneWrapper.makeDropdown(s -> cardLayout.show(panel, s),
                "Manager", "Employee", "Customer");

        var content = frame.getContentPane();
        content.setLayout(new BorderLayout());

        content.add(multibox, BorderLayout.NORTH);
        content.add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setPreferredSize(new Dimension(640, 480));
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                employees.writeToFile();
                inventory.writeToFile();
                orders.writeToFile();
                vacancy.writeToFile();
            }
        });
    }
}
