package wang.huaichao.utils;

import wang.huaichao.web.AppInitializer;
import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.JsApiTicket;
import wang.huaichao.wx.WeiXinUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/7.
 */
public class AccessTonkenManager {
    private static AccessToken accessToken = null;
    private static long tokenGetTime = 0;

    private static JsApiTicket jsapiTicket = null;
    private static long ticketGetTime = 0;

    public static String GetAccessToken() throws IOException {
        if (accessToken == null) {
            _updateAccessToken();
        } else {
            final long duaration = Calendar.getInstance().getTimeInMillis()
                    - tokenGetTime;
            if (duaration / 1000 > accessToken.getExpiresIn()) {
                _updateAccessToken();
            }
        }
        return accessToken.getAccessToken();
    }

    public static String GetJsApiTicket() throws IOException {
        if (jsapiTicket == null) {
            _updateJsApiTicket();
        } else {
            final long duaration = Calendar.getInstance().getTimeInMillis()
                    - ticketGetTime;
            if (duaration / 1000 > jsapiTicket.getExpiresIn()) {
                _updateJsApiTicket();
            }
        }
        return jsapiTicket.getTiclet();
    }

    private static synchronized void _updateAccessToken()
            throws IOException {
        String token = WeiXinUtils.getAccessToken();
        accessToken = GsonUtils.j20(token, AccessToken.class);
    }

    private static synchronized void _updateJsApiTicket()
            throws IOException {
        String token = WeiXinUtils.getJsApiTicket();
        jsapiTicket = GsonUtils.j20(token, JsApiTicket.class);
    }
}
