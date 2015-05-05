package wang.huaichao.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import wang.huaichao.GsonUtils;
import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.WeiXinUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/5.
 */
public class HttpUtils {
    private final byte[] buff = new byte[1024];

    public String get(String sUrl) throws IOException {
        return get(sUrl, "utf8");
    }

    public String get(String sUrl, String sEncoding) throws IOException {
        URL url = new URL(sUrl);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        return _getResponse(is, sEncoding);
    }

    public String post(String sUrl, String sData) throws IOException {
        return post(sUrl, sData, "utf8");
    }

    public String post(String sUrl, String sData, String sEncoding) throws IOException {
        URL url = new URL(sUrl);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection)) {
            throw new RuntimeException("not instanceof HttpURLConnection");
        }

        conn.setDoOutput(true);
        final OutputStream os = conn.getOutputStream();
        os.write(sData.getBytes());
        os.flush();


        return _getResponse(conn.getInputStream(), sEncoding);
    }

    private String _getResponse(InputStream is, String sEncoding)
            throws IOException {
        int len;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        return baos.toString(sEncoding);
    }

    public static void main(String[] args) throws IOException {
        final String sAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" +
                "wx608f8974cefe0d82"
                + "&corpsecret=" +
                "scA4o6Nh5fc8kG4saOAH5BGUX1jjjxV6oxq7XO37yyRKQpXuE3OgMaMuYYtZ_6Ke";


        final HttpUtils httpUtils = new HttpUtils();
        final String resp = httpUtils.get(sAccessToken);

        System.out.println(resp);

        final AccessToken accessToken = GsonUtils.j20(resp, AccessToken.class);
        System.out.println(accessToken.getAccessToken());
        System.out.println(accessToken.getExpiresIn());


        final JsonObject postMsg = new JsonObject();
        postMsg.addProperty("touser", "huaichao.wang");
        postMsg.addProperty("msgtype", "text");
        postMsg.addProperty("agentid", 10);
        final JsonObject text = new JsonObject();
        text.addProperty("content",
                "hello" + Calendar.getInstance().getTimeInMillis()
        );
        postMsg.add("text", text);
        postMsg.addProperty("safe", 0);

        final String post = httpUtils.post(WeiXinUtils.sPostMsgUrl + accessToken.getAccessToken(), postMsg.toString());
        System.out.println(post);
    }
}
