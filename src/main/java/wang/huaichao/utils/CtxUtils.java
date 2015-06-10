package wang.huaichao.utils;

import wang.huaichao.web.AppCtxHolder;
import wang.huaichao.web.action.LocaleController;

/**
 * Created by Administrator on 2015/6/10.
 */
public class CtxUtils {
    public static String getMessage(String key) {
        return AppCtxHolder.getApplicationContext().getMessage(
                key,
                null,
                LocaleController.locale
        );
    }
}
