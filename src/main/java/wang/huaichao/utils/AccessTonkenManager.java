package wang.huaichao.utils;

import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.WeiXinUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/7.
 */
public class AccessTonkenManager {
    private static AccessToken accessToken = null;
    private static long tokenGetTime = 0;

    public static String GetAccessToken() throws IOException {
        if (accessToken == null) {
            _updateAccessToken();
        } else {
            final long duaration = Calendar.getInstance().getTimeInMillis() - tokenGetTime;
            if (duaration / 1000 > accessToken.getExpiresIn()) {
                _updateAccessToken();
            }
        }
        return accessToken.getAccessToken();
    }

    private static void _updateAccessToken() throws IOException {
        String token = WeiXinUtils.getAccessToken();
        accessToken = GsonUtils.j20(token, AccessToken.class);
    }
}
