package wang.huaichao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by Administrator on 2015/5/4.
 */
public class ConfigLoader {
    private static final Map<String, Config> configs =
            new HashMap<String, Config>();

    private ConfigLoader() {
    }

    public static Config load(String path) {
        if (configs.containsKey(path)) return configs.get(path);

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            final Config config = new Config();
            final Set<String> kesy = properties.stringPropertyNames();
            for (String key : kesy) {
                config.add(key, properties.getProperty(key));
            }
            configs.put(path, config);
            return config;
        } catch (IOException e) {
            throw new RuntimeException("loading config failed " + path, e);
        }
    }
}
