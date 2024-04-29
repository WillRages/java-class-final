package database;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Wraps a CSV file with accessor methods.
 */
public class Database {
    public final String[] headers;
    public final HashMap<String, String[]> rows = new HashMap<>();

    public Database(String fileName) {
        File file = new File(fileName);
        try (var reader = new BufferedReader(new FileReader(file))) {
            this.headers = reader.readLine().split(",");
            while (reader.ready()) {
                String[] cols = reader.readLine().split(",");
                this.rows.put(cols[0], cols);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Row getRow(String primaryKey) {
        HashMap<String, String> map = new HashMap<>(this.headers.length);
        final String[] row = rows.get(primaryKey);

        for (int i = 0; i < this.headers.length; i++) {
            map.put(headers[i], row[i]);
        }

        return new Row(map);
    }
}
