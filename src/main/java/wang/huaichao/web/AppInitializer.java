package wang.huaichao.web;

import wang.huaichao.misc.Config;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.wx.WeiXinUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Created by Administrator on 2015/5/4.
 */
public class AppInitializer implements ServletContextListener {
    public static Config AppConfig;
    public static Config WeiXinConfig;
    private static final String fs = File.separator;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final String root = System.getProperty("web.root.dir");
        if (!new File(root).exists()) {
            throw new RuntimeException("get app root failed: " + root);
        }
        AppConfig = ConfigLoader.loadFromClassPath("/app.properties");
        String location = AppConfig.getString("config.location");
        String filename = AppConfig.getString("config.filename");
        if (location.isEmpty())
            location = root + fs + "WEB-INF" + fs + "classes" + fs;
        if (!new File(location + fs + filename).exists()) {
            throw new RuntimeException(
                    "weixin-ws.properties does not exists: " +
                            location + fs + filename
            );
        }
        WeiXinConfig = ConfigLoader.load(location + fs + filename);
        WeiXinUtils.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
