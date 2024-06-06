package ui;

import order.Ingredient;
import order.Order;

import javax.swing.*;
import java.awt.*;

public class DisplayMenuItem extends JPanel {
    private final JButton purchaseButton;

    public DisplayMenuItem(Order.MenuItem item, Runnable onBuy) {
        String[] strings = item.getIngredients().stream()
                .map(Ingredient::type)
                .map(Enum::name)
                .toArray(String[]::new);

        var ingredients = new JList<>(strings);

        var price = item.getPrice();
        var priceLabel = new JLabel("" + price);

        var nameLabel = new JLabel(item.name());

        this.setLayout(new GridBagLayout());

        this.purchaseButton = PaneWrapper.makeButton("Purchase", e -> onBuy.run());

        var c = new GridBagConstraints();

        c.gridx = 0;

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.ipadx = 10;
        c.ipady = 10;
        this.add(nameLabel, c);
        c.gridy = 2;
        this.add(priceLabel, c);
        c.gridy = 3;
        this.add(purchaseButton, c);
        c.ipadx = 0;
        c.ipady = 0;
        c.gridy = 1;
        this.add(ingredients, c);

        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 3));
    }

    public void setPurchasable(boolean purchasable) {
        this.purchaseButton.setEnabled(purchasable);
    }
}
