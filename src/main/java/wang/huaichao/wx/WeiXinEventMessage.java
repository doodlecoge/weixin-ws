package wang.huaichao.wx;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Administrator on 2015/5/5.
 */

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class WeiXinEventMessage extends WeiXinMessageBase {

    @XmlElement(name = "Event")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String event;

    @XmlElement(name = "EventKey")
    @XmlJavaTypeAdapter(WeiXinMessageTypeAdapter.class)
    private String eventKey;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
