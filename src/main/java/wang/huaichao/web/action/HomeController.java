package wang.huaichao.web.action;

import com.qq.weixin.mp.aes.AesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.huaichao.misc.AppInitializer;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.wx.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private AccessToken accessToken = null;
    private long tokenGetTime = 0;

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

                WeiXinMessageBase msgObj = WeiXinMessageBase.getInstance(msg);


                if (msgObj instanceof WeiXinTextMessage)
                    _echo((WeiXinTextMessage) msgObj);
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
                    final String resp = WeiXinUtils.postMessage(_getAccessToken(), sJson);
                    System.out.println(resp);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }

        return null;
    }

    private void _echo(WeiXinTextMessage msg) {
        try {

            final String sJson = WeiXinUtils.buildTextMsg(
                    Arrays.asList(msg.getFromUserName()),
                    null,
                    null,
                    WeiXinMessageType.valueOf(msg.getMsgType()),
                    msg.getAgentId(),
                    msg.getContent() + ": " + Calendar.getInstance().getTime()
            );

            final String resp = WeiXinUtils.postMessage(_getAccessToken(), sJson);
            log.info(resp);
        } catch (Exception e) {
            log.error("---", e);
        }
    }

    private String _getAccessToken() throws IOException {
        if (accessToken == null) {
            _updateAccessToken();
        } else {
            final long duaration = Calendar.getInstance().getTimeInMillis() - tokenGetTime;
            if (duaration / 1000 > accessToken.getExpiresIn()) {
                _updateAccessToken();
            }
        }
        return accessToken.getAccessToken();
    }

    private void _updateAccessToken() throws IOException {
        String token = WeiXinUtils.getAccessToken();
        accessToken = GsonUtils.j20(token, AccessToken.class);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test, " + Calendar.getInstance().getTimeInMillis() + ", " +
                AppInitializer.AppConfig.getString("app.version");
    }

}
