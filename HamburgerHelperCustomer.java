import database.Database;
import order.Order;
import ui.DisplayMenuItem;
import ui.MultiForm;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HamburgerHelperCustomer extends JPanel {
    private static JPanel makeOrder() {
        var form = new MultiForm(s -> {
            HamburgerHelperMain.orders.addRow(s);
            PaneWrapper.say(Arrays.toString(s));
        });

        form.addInput("Main Dish: ", PaneWrapper.makeStringField(""));
        form.addInput("Side Dish: ", PaneWrapper.makeStringField(""));
        form.addInput("Drink: ", PaneWrapper.makeStringField(""));
        form.addInput("Address: ", PaneWrapper.makeStringField(""));
        form.addInput("Price: ", PaneWrapper.makeIntField(0)); // REMOVE LATER, for test sake only

        form.addButtons();
        return form;
    }

    public HamburgerHelperCustomer() {
        var cardLayout = new CardLayout();
//        this.setLayout(cardLayout);

        var pane = new JTabbedPane();
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        pane.addTab("Order: ", HamburgerHelperMain.pinTop(makeOrder()));

//        this.add(pane, "Customer Page");

        var menuItem = new DisplayMenuItem(
                Order.MenuItem.ChickenCheeseSandwich,
                () -> PaneWrapper.say("Purchased Chicken Cheese Sandwich")
        );

        this.add(menuItem);
    }
}
