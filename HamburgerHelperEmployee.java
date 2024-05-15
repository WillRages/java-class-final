import database.Database;
import ui.PaneWrapper;
import ui.MultiForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.util.Arrays;
import java.awt.event.WindowEvent;

public class HamburgerHelperEmployee { // class start

	public static Database employees = new Database("assets/employees.csv");
	public static Database inventory = new Database("assets/inventory.csv");
	public static Database orders = new Database("assets/orders.csv");

	public static JPanel addOrder() {
		var form = new MultiForm(s -> {
			orders.addRow(s);
			PaneWrapper.say(Arrays.toString(s));
		});

		form.addInput("Order: ", PaneWrapper.makeStringField(""));
		form.addInput("Address: ", PaneWrapper.makeStringField(""));
		form.addInput("Price: ", PaneWrapper.makeIntField(0));

		form.addButtons();

		return form;
	}

	public static JPanel askVacant(){
		var form = new MultiForm(s -> {

		});
		form.addInput("Employee's Name: ", PaneWrapper.makeStringField(""));
		form.addInput("Requested Hours: ", PaneWrapper.makeIntField(0));
		form.addInput("Cause of the Request: ", PaneWrapper.makeStringField(""));

		form.addButtons();

		return form;
	}


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
		pane.addTab("Add Orders", pinTop(addOrder()));
		pane.addTab("View Orders", PaneWrapper.getFromDatabase(orders));
		pane.addTab("View Employees", PaneWrapper.getFromDatabase(employees));
		pane.addTab("Ask Vacant Hours", pinTop(askVacant()));

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
		});
	} // main end
} // class end
