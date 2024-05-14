import database.Database;
import ui.PaneWrapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;

public class HamburgerHelperEmployee { // class start

	public static Database employees = new Database("assets/employees.csv");
	public static Database inventory = new Database("assets/inventory.csv");
	public static Database orders = new Database("assets/orders.csv");

	

	public static JPanel pinTop(JPanel panel) { // pinTop start
		var wrapper = new JPanel(new BorderLayout());
		wrapper.add(panel, BorderLayout.NORTH);
		return wrapper;
	} // pinTop end
	public static void main(String[] args) { // main start
		JFrame frame = new JFrame("Title");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane pane = new JTabbedPane();
		pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		pane.addTab("Add Item", pinTop(HamburgerHelperManager.addItemMenu()));
		pane.addTab("View Orders", PaneWrapper.getFromDatabase(orders));

		frame.getContentPane().add(pane);
		frame.setPreferredSize(new Dimension(640, 480));
		frame.setLocationRelativeTo(null);

		frame.pack();
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				inventory.writeToFile();
			}
		}
	} // main end
} // class end
