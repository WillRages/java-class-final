package ui;

import database.Database;
import database.Row;

import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

public class LoginManager extends JPanel {

    public LoginManager(Database database, Function<Row, Boolean> validator, Runnable onSubmit) {
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passkeyLabel = new JLabel("Password: ");

        JTextField usernameInput = PaneWrapper.makeStringField("");
        JTextField passkeyInput = PaneWrapper.makeStringField("");

        JButton submit = PaneWrapper.makeButton("Submit", e -> {
            var username = usernameInput.getText();
            var passkey = passkeyInput.getText();
            var row = database.getRow(username);
            if (row == null) {
                PaneWrapper.err("Unknown user " + username);
                return;
            }
            if (!validator.apply(row)) {
                PaneWrapper.err("Invalid user");
                return;
            }
            if (!row.getString("Passkey").equals(passkey)) {
                PaneWrapper.err("Wrong password");
                return;
            }
            onSubmit.run();
        });

        var layout = new GridBagLayout();
        this.setLayout(layout);

        var c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;

        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;

        c.gridy = 0;
        this.add(usernameLabel, c);
        c.gridy = 1;
        this.add(passkeyLabel, c);
        c.gridy = 2;
        this.add(submit, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;

        c.gridy = 0;
        this.add(usernameInput, c);
        c.gridy = 1;
        this.add(passkeyInput, c);
    }
}
