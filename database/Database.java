package database;

import java.util.HashMap;

/**
 * Wraps a CSV file with accessor methods.
 */
public class Database {
    public final double minimumWage = 14.0;
    public final HashMap<String, Row> rows;

    public Database(String fileName) {
        this.rows = null;
    }
    public Row getRow(String primaryKey) {
        return null;
    }
}
