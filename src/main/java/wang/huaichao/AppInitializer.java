package wang.huaichao;

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

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        final String root = System.getProperty("web.root.dir");
        if (!new File(root).exists()) {
            throw new RuntimeException("get app root failed: " + root);
        }
        AppConfig = ConfigLoader.loadFromClassPath("/app.properties");
        String location = AppConfig.getString("config.location");
        String filename = AppConfig.getString("config.filename");
        if (location.isEmpty()) location = root;
        if (!new File(location + File.separator + filename).exists()) {
            throw new RuntimeException(
                    "weixin-ws.properties does not exists: " +
                            location + File.separator + filename
            );
        }
        WeiXinConfig = ConfigLoader.load(location + File.separator + filename);
        WeiXinUtils.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
