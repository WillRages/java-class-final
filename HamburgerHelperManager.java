import database.Database;

public class HamburgerHelperManager {
	public static Database database = new Database("assets/data.csv");

    public static void addEmployee() {
        String name = PaneWrapper.inputString("Enter employee first name");
        String role = PaneWrapper.inputString("Enter employee role");
        Integer wage = PaneWrapper.inputInt("Enter employee wage");

        database.addRow(name, role, wage.toString());
    }

    public static void main(String[] args) {
        while (PaneWrapper.checkbox("Add employee?"))
            addEmployee();

        database.writeToFile();
    }
}
