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
import java.util.Calendar;
import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Administrator on 2015/5/5.
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        String xml = "<xml><ToUserName><![CDATA[wx608f8974cefe0d82]]></ToUserName>" +
//                "<FromUserName><![CDATA[huaichao.wang]]></FromUserName>" +
//                "<CreateTime>1430875398</CreateTime>" +
//                "<MsgType><![CDATA[text]]></MsgType>" +
//                "<Content><![CDATA[ABC]]></Content>" +
//                "<MsgId>4389487259758362639</MsgId>" +
//                "<AgentID>10</AgentID>" +
//                "</xml>";


//        String xml = " <xml><ToUserName><![CDATA[wx608f8974cefe0d82]]></ToUserName>" +
//                "<FromUserName><![CDATA[huaichao.wang]]></FromUserName>" +
//                "<CreateTime>1430880582</CreateTime>" +
//                "<MsgType><![CDATA[event]]></MsgType>" +
//                "<AgentID>10</AgentID>" +
//                "<Event><![CDATA[click]]></Event>" +
//                "<EventKey><![CDATA[V1001_TODAY_MUSIC]]></EventKey>" +
//                "</xml>";
//
//
//        final WeiXinMessageBase instance = WeiXinMessageBase.getInstance(xml);
//        System.out.println(instance instanceof WeiXinEventMessage);


        final TreeMap<DateTime, String> tm = new TreeMap<>();

        final Calendar cal = Calendar.getInstance();

        DateTime dt = new DateTime(
                new Date("2015/08/05 06:35")
        );
        tm.put(dt, "xxxx1");

        dt = new DateTime(
                new Date("2015/07/04 17:01")
        );
        tm.put(dt, "xxxx3");

        dt = new DateTime(
                new Date("2015/07/22 06:06")
        );
        tm.put(dt, "xxxx2");


        System.out.println(tm);
    }


}
