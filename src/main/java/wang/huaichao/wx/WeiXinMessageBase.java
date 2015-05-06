package wang.huaichao.wx;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import org.xml.sax.SAXException;
import wang.huaichao.utils.XmlUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

/**
 * Created by Administrator on 2015/5/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WeiXinMessageBase {
    @XmlElement(name = "ToUserName")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String toUserName;

    @XmlElement(name = "FromUserName")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private String createTime;

    @XmlElement(name = "MsgType")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String msgType;

    @XmlElement(name = "MsgId")
    private String msgId;

    @XmlElement(name = "AgentID")
    private String agentId;


    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String toXml() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(
                WeiXinTextMessage.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(
                "com.sun.xml.bind.marshaller.CharacterEscapeHandler",
                new CharacterEscapeHandler() {
                    @Override
                    public void escape(char[] ch, int start, int length,
                                       boolean isAttVal, Writer writer)
                            throws IOException {
                        writer.write(ch, start, length);
                    }
                });

        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        jaxbMarshaller.marshal(this, baos);
        return baos.toString();
    }

    public static WeiXinMessageBase getInstance(String xml)
            throws JAXBException, ParserConfigurationException, SAXException,
            XPathExpressionException, IOException {
        String type = XmlUtils.queryString(xml, "/xml/MsgType/text()");

        if (WeiXinMessageType.text.val.equals(type)) {
            return _getInstance(xml, WeiXinTextMessage.class);
        } else if (WeiXinMessageType.event.val.equals(type)) {
            return _getInstance(xml, WeiXinEventMessage.class);
        } else {
            return null;
        }
    }


    private static <T> T _getInstance(String xml, Class<T> cls)
            throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        final StringReader xmlStream = new StringReader(xml);
        return (T) jaxbUnmarshaller.unmarshal(xmlStream);
    }

    public static void main(String[] args) throws JAXBException {

    }
}
