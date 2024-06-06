package ui;

import order.Order;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DisplayMenuGrid extends JScrollPane {
    private final Supplier<Integer> innerWidth;
    private final Supplier<Integer> availableHeight;
    public DisplayMenuGrid(Order.MenuItem[] items, Supplier<Integer> availableHeight, Consumer<Order.MenuItem> onBuy) {
        this.availableHeight = availableHeight;
        var inner = new JPanel();
        var layout = new GridLayout(items.length / 2 + 1, 1);
        inner.setLayout(layout);

        for (var item : items) {
            var menuItem = new DisplayMenuItem(item, () -> onBuy.accept(item));
            inner.add(menuItem);
        }

        innerWidth = inner::getWidth;

        this.setViewportView(inner);
    }

    @Override
    public Dimension getPreferredSize() {
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        Dimension dim = new Dimension(innerWidth.get() + getVerticalScrollBar().getSize().width, availableHeight.get());
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return dim;
    }
}
