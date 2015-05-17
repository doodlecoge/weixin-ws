package wang.huaichao.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.wx.WeiXinUtils;

import java.io.File;

/**
 * Created by Administrator on 2015/5/6.
 */
@Controller
@RequestMapping("/config")
public class ConfigController {
    @RequestMapping(method = RequestMethod.GET)
    public String update() {
        final String root = System.getProperty("web.root.dir");
        String location = AppInitializer.AppConfig.getString("config.location");
        String filename = AppInitializer.AppConfig.getString("config.filename");

        if (location.isEmpty())
            location = root + File.separator + "classes" + File.separator;

        if (!new File(location + File.separator + filename).exists()) {
            throw new RuntimeException(
                    "weixin-ws.properties does not exists: " +
                            location + File.separator + filename
            );
        }

        AppInitializer.WeiXinConfig = ConfigLoader.load(location + File.separator + filename);
        WeiXinUtils.init();

        return "success";
    }
}
