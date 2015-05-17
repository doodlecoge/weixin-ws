package wang.huaichao.web.action;

import com.qq.weixin.mp.aes.AesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.utils.AccessTonkenManager;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.wx.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 2015/5/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @ResponseBody
    @RequestMapping(value = "/index",
            produces = "text/xml; charset=UTF-8")
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

                WeiXinMessageBase msgObj = WeiXinMessageBase.getInstance(msg);


                if (msgObj instanceof WeiXinTextMessage)
                    return _echo((WeiXinTextMessage) msgObj);
                else if (msgObj instanceof WeiXinEventMessage) {
                    final WeiXinEventMessage obj = (WeiXinEventMessage) msgObj;
                    final String sJson = WeiXinUtils.buildTextMsg(
                            Arrays.asList(obj.getFromUserName()),
                            null,
                            null,
                            WeiXinMessageType.valueOf(obj.getMsgType()),
                            obj.getAgentId(),
                            obj.getEvent() + "," + obj.getEventKey() + ": "
                                    + Calendar.getInstance().getTime()
                    );
                    final String resp = WeiXinUtils.postMessage(
                            AccessTonkenManager.GetAccessToken(), sJson
                    );
                    log.info(resp);
                } else {
                    log.error("unknown message");
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    private String _echo(WeiXinTextMessage msg) {
        try {
            final String fromUserName = msg.getFromUserName();
            msg.setFromUserName(msg.getToUserName());
            msg.setToUserName(fromUserName);
            msg.setContent(_revert(msg.getContent()));
            return WeiXinUtils.EncryptMsg(
                    msg.toXml(),
                    Calendar.getInstance().getTimeInMillis() + "",
                    (new Random().nextInt(90000) + 10000) + ""
            );

        } catch (Exception e) {
            log.error("---", e);
            return "error";
        }
    }

    private String _revert(String str) {
        String s = "";
        int len = str.length();
        for (int i = 0; i < len; i++) {
            s += str.charAt(len - 1 - i);
        }
        return s;
    }


    @RequestMapping("/test")
    @WxIdRequired(false)
    @ResponseBody
    public String test() {
        return "test, " + Calendar.getInstance().getTimeInMillis() + ", " +
                AppInitializer.AppConfig.getString("app.version");
    }

}
