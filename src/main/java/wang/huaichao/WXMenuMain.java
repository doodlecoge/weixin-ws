package wang.huaichao;

import wang.huaichao.misc.Config;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.net.HttpUtils;
import wang.huaichao.utils.FileUtils;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.wx.AccessToken;

import java.io.IOException;

/**
 * Created by Administrator on 2015/5/20.
 */
public class WXMenuMain {
    public static void main(String[] args) throws IOException {
        buildMenu();
    }

    public static void buildMenu() throws IOException {
        final Config config = ConfigLoader.loadFromClassPath("/weixin-ws.properties");
        final String sAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" +
                config.getString("wx.corp_id")
                + "&corpsecret=" +
                config.getString("wx.secret");

        final HttpUtils httpUtils = new HttpUtils();
        final String resp = httpUtils.get(sAccessToken);

        System.out.println(resp);

        final AccessToken accessToken = GsonUtils.j20(resp, AccessToken.class);
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getExpiresIn());


        String menuUrl = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token="
                + accessToken.getAccessToken()
                + "&agentid=" + 2;
        String menuJson = FileUtils.loadFromClassPath("/menu.json");
        System.out.println(menuJson);

        final String post = httpUtils.post(menuUrl, menuJson);
        System.out.println(post);
    }
}
