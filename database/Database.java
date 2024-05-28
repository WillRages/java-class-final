package database;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
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
    private final Model model = new Model();

    private ArrayList<String> readList(BufferedReader reader) throws IOException {
        return new ArrayList<>(List.of(reader.readLine().split(";")));
    }

    public Database(ArrayList<String> headers) {
        this.file = null;
        this.headers = headers;
        this.model.redoRows();
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
                    throw new Exception(String.format("Wrong column size %d, expected %d", cols.size(),
                            headers.size()));
                }
                this.rows.put(cols.getFirst(), cols);
            }
        } catch (Exception e) {
            System.err.println(e + "\nAt line " + line);
            throw new RuntimeException(e);
        }
        this.model.redoRows();
    }

    public Row getRow(String primaryKey) {
        HashMap<String, String> map = new HashMap<>(this.headers.size());
        final var row = rows.get(primaryKey);

        if (row == null) return null;

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
        this.model.redoRows();
    }

    public void deleteRow(String key) {
        this.rows.remove(key);
        this.model.redoRows();
    }

    public void clear() {
        this.rows.clear();
        this.model.redoRows();
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
            if (i != row.size() - 1) builder.append(';');
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

    public Model getModel() {
        return this.model;
    }

    public class Model implements TableModel {
        private String[][] rows;
        private final ArrayList<TableModelListener> listeners;

        private Model() {
            listeners = new ArrayList<>();
        }

        private void redoRows() {
            this.rows = new String[getRowCount()][getColumnCount()];
            int i = 0;
            for (var value : Database.this.rows.values()) {
                for (int j = 0; j < value.size(); j++) {
                    this.rows[i][j] = value.get(j);
                }
                ++i;
            }

            var event = new TableModelEvent(this);
            this.listeners.forEach(l -> l.tableChanged(event));
        }

        @Override
        public int getRowCount() {
            return Database.this.rows.size();
        }

        @Override
        public int getColumnCount() {
            return Database.this.headers.size();
        }

        @Override
        public String getColumnName(int columnIndex) {
            return Database.this.headers.get(columnIndex);
        }

        @Override
        public Class<String> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public String getValueAt(int rowIndex, int columnIndex) {
            return this.rows[rowIndex][columnIndex];
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        }

        @Override
        public void addTableModelListener(TableModelListener l) {
            listeners.add(l);
        }

        @Override
        public void removeTableModelListener(TableModelListener l) {
        }
    }
}
