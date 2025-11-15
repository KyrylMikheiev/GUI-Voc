package src.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

enum JSONType {
    BOOL,
    NULL,
    STRING,
    LONG,
    DOUBLE,
    ARRAY,
    DICT
}

public class JSONObject {
    private JSONType type;
    Object value;

    public JSONObject() {
        this.type = JSONType.NULL;
    }

    public JSONObject(boolean val) {
        this.type = JSONType.BOOL;
        this.value = val;
    }

    public JSONObject(String val) {
        this.type = JSONType.STRING;
        this.value = val;
    }

    public JSONObject(Double val) {
        this.type = JSONType.DOUBLE;
        this.value = val;
    }

    public JSONObject(Long val) {
        this.type = JSONType.LONG;
        this.value = val;
    }

    public JSONObject(List<JSONObject> val) {
        this.type = JSONType.ARRAY;
        this.value = val;
    }

    public JSONObject(Map<String, JSONObject> val) {
        this.type = JSONType.DICT;
        this.value = val;
    }

    public JSONType getType() {
        return this.type;
    }

    public boolean isNull() {
        return this.type == JSONType.NULL;
    }

    public JSONObject get(String key) {
        expectType(JSONType.DICT);
        Map<String, JSONObject> map = (Map<String, JSONObject>) this.value;
        return map.get(key);
    }

    public JSONObject get(int index) {
        expectType(JSONType.ARRAY);
        return ((List<JSONObject>) this.value).get(index);
    }

    public boolean getBool() {
        expectType(JSONType.BOOL);
        return (boolean) this.value;
    }

    public String getString() {
        expectType(JSONType.STRING);
        return (String) this.value;
    }

    public Double getDouble() {
        expectType(JSONType.DOUBLE);
        return (Double) this.value;
    }

    public Long getLong() {
        expectType(JSONType.LONG);
        return (Long) this.value;
    }

    public int getInt() {
        if (this.getLong() < Integer.MIN_VALUE || this.getLong() > Integer.MAX_VALUE) {
            throw new RuntimeException("Attempted integer conversion but LONG value exceeds max int range");
        }
        return this.getLong().intValue();
    }

    private void expectType(JSONType expectedType) {
        if (this.type != expectedType) {
            throw new RuntimeException("Operation for Type " + expectedType.name() + " performed on Object of Type " + this.type.name());
        }
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}