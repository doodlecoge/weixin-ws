package wang.huaichao.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Administrator on 2015/5/4.
 */
public class Config {
    private Map<String, String> kvs = new HashMap<String, String>();

    public Config() {

    }

    public void add(String key, String val) {
        kvs.put(key, val);
    }

    public String getString(String key) {
        return kvs.get(key);
    }

    public boolean getBoolean(String key, boolean def) {
        if (kvs.containsKey(key)) {
            final String val = getString(key);
            return "1".equals(val) || "true".equalsIgnoreCase(val);
        } else return def;
    }

    public int getInt(String key) {
        return Integer.valueOf(kvs.get(key));
    }
}
