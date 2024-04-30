package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Wraps a CSV file with accessor methods.
 */
public class Database {
    public final ArrayList<String> headers;
    public final HashMap<String, ArrayList<String>> rows = new HashMap<>();

    private ArrayList<String> readList(BufferedReader reader) throws IOException {
        return new ArrayList<>(List.of(reader.readLine().split(",")));
    }

    public Database(String fileName) {
        File file = new File(fileName);
        try (var reader = new BufferedReader(new FileReader(file))) {
            this.headers = readList(reader);
            while (reader.ready()) {
                ArrayList<String> cols = readList(reader);
                this.rows.put(cols.getFirst(), cols);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Row getRow(String primaryKey) {
        HashMap<String, String> map = new HashMap<>(this.headers.size());
        final var row = rows.get(primaryKey);

        for (int i = 0; i < this.headers.size(); i++) {
            map.put(headers.get(i), row.get(i));
        }

        return new Row(map);
    }

    public void addColumn(String name, String defaultValue) {
        headers.add(name);
        for (var row : rows.values()) {
            row.add(defaultValue);
        }
    }
}
