package ui;

import database.Database;

import javax.swing.*;
import java.awt.*;

public class LoginManager extends JPanel {

    public LoginManager(String credentialPath, Runnable onSubmit) {
        var database = new Database(credentialPath);

        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passkeyLabel = new JLabel("Password: ");

        JTextField usernameInput = PaneWrapper.makeStringField("");
        JTextField passkeyInput = PaneWrapper.makeStringField("", s -> onSubmit.run());

        JButton submit = PaneWrapper.makeButton("Submit", e -> onSubmit.run());

        var layout = new GridBagLayout();
        this.setLayout(layout);
    }
}
