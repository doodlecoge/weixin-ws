package wang.huaichao.net;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.WeiXinUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/5.
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private final byte[] buff = new byte[1024];

    public String get(String sUrl) throws IOException {
        return get(sUrl, "utf8");
    }

    public String get(String sUrl, String sEncoding) throws IOException {
        log.debug("get url: " + sUrl);
        URL url = new URL(sUrl);
        URLConnection conn = url.openConnection();
        InputStream is = conn.getInputStream();
        return _getResponse(is, sEncoding);
    }

    public String post(String sUrl, String sData) throws IOException {
        return post(sUrl, sData, "utf8");
    }

    public String post(String sUrl, String sData, String sEncoding) throws IOException {
        log.debug("post url: " + sUrl);
        log.debug("post data: " + sData);

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
}
