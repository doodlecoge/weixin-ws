package wang.huaichao;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import wang.huaichao.misc.Config;
import wang.huaichao.misc.ConfigLoader;
import wang.huaichao.net.HttpUtils;
import wang.huaichao.utils.AccessTonkenManager;
import wang.huaichao.utils.FileUtils;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.utils.XmlUtils;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.web.model.DateTime;
import wang.huaichao.wx.*;


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
import java.util.*;

/**
 * Created by Administrator on 2015/5/5.
 */
public class Main {

    public static final String sPostMsgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    public static void main(String[] args) throws Exception {
        String msg = "hello joe! http://www.tcl.com";
        String who = "junz@tcl.com";
        final String sJson = "{\n" +
                "    \"touser\": \""+who+"\",\n" +
                "    \"msgtype\": \"text\",\n" +
                "    \"agentid\": \"2\",\n" +
                "    \"text\": {\n" +
                "        \"content\": \""+msg+"\"\n" +
                "    },\n" +
                "    \"safe\": \"0\"\n" +
                "}";

        final Config config = ConfigLoader.loadFromClassPath("/weixin-ws.properties");
        final String sCorpID = config.getString("wx.corp_id");
        final String sSecret = config.getString("wx.secret");
        String sAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + sCorpID + "&corpsecret=" + sSecret;

        final HttpUtils httpUtils = new HttpUtils();
        String accessToken = httpUtils.get(sAccessToken);
        accessToken = GsonUtils.j20(accessToken, AccessToken.class).getAccessToken();

        System.out.println(accessToken);



        final String post = httpUtils.post(sPostMsgUrl + accessToken, sJson);


        System.out.println(post);
    }


}
