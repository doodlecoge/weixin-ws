package wang.huaichao.web.action;

import com.google.gson.JsonObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.huaichao.AppInitializer;
import wang.huaichao.wx.WeiXinMessage;
import wang.huaichao.wx.WeiXinUtils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @ResponseBody
    @RequestMapping("/index")
    public String index(@RequestParam String msg_signature,
                        @RequestParam String timestamp,
                        @RequestParam String nonce,
                        @RequestParam(required = false) String echostr,
                        @RequestBody(required = false) String data) {

        log.info(msg_signature);
        log.info(timestamp);
        log.info(nonce);
        log.info(echostr);
        log.info(data);
        log.info("\n\n");

        if (echostr != null) {
            try {
                return WeiXinUtils.verifyURL(msg_signature, timestamp, nonce, echostr);
            } catch (AesException e) {
                return null;
            }
        } else {
            try {
                final String msg = WeiXinUtils.DecryptMsg(
                        msg_signature, timestamp, nonce, data
                );
                log.info(msg);

                echo(msg);
            } catch (AesException e) {

            }
        }

        return null;
    }

    private void echo(String xml) {

        try {
            WeiXinMessage msg = WeiXinMessage.getInstance(xml);


//            {
//                "touser": "UserID1|UserID2|UserID3",
//                    "toparty": " PartyID1 | PartyID2 ",
//                    "totag": " TagID1 | TagID2 ",
//                    "msgtype": "text",
//                    "agentid": "1",
//                    "text": {
//                "content": "Holiday Request For Pony(http://xxxxx)"
//            },
//                "safe":"0"
//            }


            final String sJson = WeiXinUtils.buildTextMsg(
                    Arrays.asList(msg.getFromUserName()),
                    null,
                    null,
                    WeiXinUtils.MessageType.valueOf(msg.getMsgType()),
                    msg.getAgentId(),
                    msg.getContent() + ": " + Calendar.getInstance().getTime()
            );


            String token = WeiXinUtils.getAccessToken();
            final String resp = WeiXinUtils.postMessage(token, sJson);
            log.info(resp);
        } catch (Exception e) {
            log.error("---", e);
        }
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test, " + Calendar.getInstance().getTimeInMillis() + ", " +
                AppInitializer.AppConfig.getString("app.version");
    }
}
