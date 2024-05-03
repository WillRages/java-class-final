package database;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Wraps a CSV file with accessor methods.
 */
public class Database {
    private final ArrayList<String> headers;
    private final HashMap<String, ArrayList<String>> rows = new HashMap<>();
    private final File file;

    private ArrayList<String> readList(BufferedReader reader) throws IOException {
        return new ArrayList<>(List.of(reader.readLine().split(",")));
    }

    public Database(String fileName) {
        this.file = new File(fileName);
        int line = 0;
        try (var reader = new BufferedReader(new FileReader(file))) {
            if (!reader.ready()) {
                headers = new ArrayList<>();
                return;
            }
            this.headers = readList(reader);
            while (reader.ready()) {
                ++line;
                ArrayList<String> cols = readList(reader);
                if (cols.size() != headers.size()) {
                    throw new Exception(String.format("Wrong column size %d, expected %d", cols.size(), headers.size()));
                }
                this.rows.put(cols.getFirst(), cols);
            }
        } catch (Exception e) {
            System.err.println(e + "\nAt line " + line);
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

    public void addRow(String... data) {
        if (data.length != this.headers.size()) {
            return;
        }

        this.rows.put(data[0], new ArrayList<>(List.of(data)));
    }

    public void deleteRow(String key) {
        this.rows.remove(key);
    }

    public void clear() {
        this.rows.clear();
    }

    public void addColumn(String name, String defaultValue) {
        headers.add(name);
        for (var row : rows.values()) {
            row.add(defaultValue);
        }
    }

    private static String joinRow(ArrayList<String> row) {
        StringBuilder builder = new StringBuilder(row.size());

        for (int i = 0; i < row.size(); ++i) {
            builder.append(row.get(i));
            if (i != row.size() - 1) builder.append(',');
        }

        return builder.toString();
    }

    public void writeToFile() {
        try (var writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(joinRow(this.headers));
            writer.write('\n');
            for (var row : this.rows.values()) {
                writer.write(joinRow(row));
                writer.write('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
