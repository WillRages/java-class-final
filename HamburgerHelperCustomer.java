import order.Order;
import ui.DisplayMenuGrid;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HamburgerHelperCustomer extends JPanel {
    private final ArrayList<Order.MenuItem> order = new ArrayList<>();

    private DisplayMenuGrid addCategory(Order.MenuItem[] items) {
        return new DisplayMenuGrid(items, () -> this.getHeight() - 40, item -> {
            var buy = PaneWrapper.checkbox("Purchase %s for %.2f dollars?".formatted(item.name(), item.getPrice()));
            if (buy) order.add(item);
        });
    }

    public HamburgerHelperCustomer() {
        var cardLayout = new CardLayout();
//        this.setLayout(cardLayout);

        var pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

//        this.add(pane, "Customer Page");

        pane.addTab("Burgers", HamburgerHelperMain.pinTop(addCategory(Order.burgers)));
        pane.addTab("Fries", HamburgerHelperMain.pinTop(addCategory(Order.fries)));
        pane.addTab("Drinks", HamburgerHelperMain.pinTop(addCategory(Order.drinks)));
        pane.addTab("Additions", HamburgerHelperMain.pinTop(addCategory(Order.additions)));
        pane.addTab("Sandwiches", HamburgerHelperMain.pinTop(addCategory(Order.sandwiches)));

        this.add(PaneWrapper.makeButton("Purchase", e -> {
            var total = order.stream().map(Order.MenuItem::getPrice).reduce(Double::sum);

            if (total.isEmpty()) PaneWrapper.say("Empty order.");
            else {
                var name = PaneWrapper.inputString("What is your name?");
                PaneWrapper.say("Purchased order for %s, your total is %.2f".formatted(name, total.get()));
                HamburgerHelperMain.orders.addRow(
                        name,
                        order.stream().map(Enum::name).reduce((a, b) -> a + "," + b).get()
                );
            }
        }));
        this.add(pane);
    }
}
