package wang.huaichao.wx;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;

/**
 * Created by Administrator on 2015/5/5.
 */

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeiXinMessage {
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

    @XmlElement(name = "Content")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        JAXBContext jaxbContext = JAXBContext.newInstance(WeiXinMessage.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(
                "com.sun.xml.internal.bind.characterEscapeHandler",
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

    public static WeiXinMessage getInstance(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(WeiXinMessage.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        final StringReader xmlStream = new StringReader(xml);
        return (WeiXinMessage) jaxbUnmarshaller.unmarshal(xmlStream);
    }

    public static void main(String[] args) throws JAXBException {
        WeiXinMessage msg = new WeiXinMessage();
        msg.setContent("hello");
        System.out.println(msg.toXml());


        msg = WeiXinMessage.getInstance(msg.toXml());
        System.out.println(msg.getContent());
    }
}
