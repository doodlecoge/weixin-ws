package wang.huaichao;

import com.google.gson.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import wang.huaichao.misc.Config;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.net.HttpUtils;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.utils.XmlUtils;
import wang.huaichao.wx.AccessToken;
import wang.huaichao.wx.WeiXinMessageBase;
import wang.huaichao.wx.WeiXinTextMessage;
import wang.huaichao.wx.WeiXinUtils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/5.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String xml = "<xml><ToUserName><![CDATA[wx608f8974cefe0d82]]></ToUserName>\n" +
                "<FromUserName><![CDATA[huaichao.wang]]></FromUserName>\n" +
                "<CreateTime>1430875398</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[ABC]]></Content>\n" +
                "<MsgId>4389487259758362639</MsgId>\n" +
                "<AgentID>10</AgentID>\n" +
                "</xml>\n";


        final WeiXinMessageBase instance = WeiXinMessageBase.getInstance(xml);
        System.out.println(instance instanceof WeiXinTextMessage);
    }





    public static void aaa() throws IOException {
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
