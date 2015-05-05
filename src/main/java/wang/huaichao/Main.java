package wang.huaichao;

import com.google.gson.JsonObject;
import wang.huaichao.misc.Config;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.net.HttpUtils;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.WeiXinUtils;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/5.
 */
public class Main {
    public static void main(String[] args) throws IOException {
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
                + "&agentid=" + 10;
        String menuJson = "{" +
                "    \"button\": [{" +
                "        \"type\": \"click\"," +
                "        \"name\": \"今日歌曲\"," +
                "        \"key\": \"V1001_TODAY_MUSIC\"" +
                "    }, {" +
                "        \"name\": \"菜单\"," +
                "        \"sub_button\": [{" +
                "            \"type\": \"view\"," +
                "            \"name\": \"搜索\"," +
                "            \"url\": \"http://www.soso.com/\"" +
                "        }, {" +
                "            \"type\": \"click\"," +
                "            \"name\": \"赞一下我们\"," +
                "            \"key\": \"V1001_GOOD\"" +
                "        }]" +
                "    }]" +
                "}";

        final String post = httpUtils.post(menuUrl, menuJson);
        System.out.println(post);
    }
}
