package ui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class MultiForm extends JPanel {
    private final ArrayList<JTextField> fields = new ArrayList<>();
    private final ArrayList<String> defaults = new ArrayList<>();
    private final Consumer<String[]> callback;
    private boolean buttonsAdded = false;

    public MultiForm(Consumer<String[]> onSubmit) {
        this.setLayout(new GridBagLayout());
        callback = onSubmit;
    }

    public void addInput(String label, JTextField field) {
        if (buttonsAdded) return;

        var c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = fields.size();
        this.add(new JLabel(label), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.weightx = 1;
        this.add(field, c);
        this.fields.add(field);
        this.defaults.add(field.getText());
    }

    public void addButtons() {
        if (buttonsAdded) return;
        else buttonsAdded = true;

        Runnable clear = () -> {
            for (int i = 0; i < fields.size(); i++) {
                fields.get(i).setText(defaults.get(i));
            }
        };

        var save = PaneWrapper.makeButton("Save", e -> {
            var strings = this.fields.stream().map(JTextComponent::getText).toList();
            this.callback.accept(strings.toArray(new String[0]));
            clear.run();
        });

        var cancel = PaneWrapper.makeButton("Cancel", e -> clear.run());

        var c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridy = fields.size();

        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        this.add(cancel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        this.add(save, c);
    }
}
