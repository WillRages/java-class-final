package database;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Row {
    public final HashMap<String, String> data;

    public Row(HashMap<String, String> data) {
        this.data = data;
    }

    public String getString(String key) {
        return null;
    }

    public Integer getInteger(String key) {
        return null;
    }

    public Double getDouble(String key) {
        return null;
    }
}
