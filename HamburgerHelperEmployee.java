import database.Database;

public class HamburgerHelperEmployee {
	public static Database database = new Database("assets/data.csv");

    public static void addEmployee(String name, String role, Integer wage) {
        database.addRow(name, role, wage.toString());
    }

    public static void main(String[] args) {
    }
}
