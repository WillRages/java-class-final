import ui.PaneWrapper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class HamburgerHelperMain {
    public static void main(String[] args) {
        var strings = new ArrayList<String>();
        strings.add("blah 1");
        strings.add("blah 2");
        strings.add("blah 3");
        strings.add("blah 4");
        strings.add("blah 5");

        var stuffs = new JTable();
        var model = new DefaultTableModel();
        model.addColumn("blah", strings.toArray());
        model.addColumn("blah2", strings.toArray());
        stuffs.setModel(model);

        var saveButton = PaneWrapper.makeButton("Save", e -> {
        });

        var cancelButton = PaneWrapper.makeButton("Cancel", e -> {
            PaneWrapper.err("Canceled");
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;

        c.weightx = 1.0;
        c.weighty = 1.0;

        c.gridy = 0;
        panel.add(stuffs, c);
        c.weightx = 0;
        c.weighty = 0;

        c.gridy = 1;
        panel.add(saveButton, c);

        c.gridy = 2;
        panel.add(cancelButton, c);

        JFrame frame = new JFrame("Title");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addTab("asdf 1", panel);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }
}
