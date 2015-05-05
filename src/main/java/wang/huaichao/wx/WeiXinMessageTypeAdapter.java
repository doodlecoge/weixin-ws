package wang.huaichao.wx;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Administrator on 2015/5/5.
 */
public class WeiXinMessageTypeAdapter extends XmlAdapter<String, String> {

    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
}