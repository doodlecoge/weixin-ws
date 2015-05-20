package wang.huaichao.web;

import wang.huaichao.misc.ConfigLoader;

/**
 * Created by Administrator on 2015/5/17.
 */
public class AppContext {
    private static boolean isDeveloping;

    static {
        isDeveloping = AppInitializer.WeiXinConfig.getBoolean("is_develping", true);
    }

    public static boolean isDeveloping() {
        return isDeveloping;
    }
}
