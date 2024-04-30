package database;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Row {
    private final HashMap<String, String> data;

    public Row(HashMap<String, String> data) {
        this.data = data;
    }

    public String getString(String key) {
        return data.get(key);
    }

    public Integer getInteger(String key) {
        try {
            return Integer.parseInt(data.get(key));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public Double getDouble(String key) {
        try {
            return Double.parseDouble(data.get(key));
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
