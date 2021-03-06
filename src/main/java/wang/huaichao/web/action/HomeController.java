package wang.huaichao.web.action;

import com.qq.weixin.mp.aes.AesException;
import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.xmlapi.command.CreateMeetingResult;
import com.tcl.meeting.wbxclient.xmlapi.command.GetJoinMeetingUrlResult;
import com.tcl.meeting.wbxclient.xmlapi.command.GetUserResult;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.utils.StringUtils;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.utils.AccessTonkenManager;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;
import wang.huaichao.wx.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2015/5/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/index", produces = "text/xml; charset=UTF-8")
    @WxIdRequired(false)
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
//                    final WeiXinEventMessage obj = (WeiXinEventMessage) msgObj;
//                    final String sJson = WeiXinUtils.buildTextMsg(
//                            Arrays.asList(obj.getFromUserName()),
//                            null,
//                            null,
//                            WeiXinMessageType.valueOf(obj.getMsgType()),
//                            obj.getAgentId(),
//                            obj.getEvent() + "," + obj.getEventKey() + ": "
//                                    + Calendar.getInstance().getTime()
//                    );
//                    final String resp = WeiXinUtils.postMessage(
//                            AccessTonkenManager.GetAccessToken(), sJson
//                    );
//                    log.info(resp);
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


    @RequestMapping("/share")
    @WxIdRequired(false)
    public String share(ModelMap map) throws IOException, NoSuchAlgorithmException {
        String nonceStr = "2nDgiWM7gCxhL8v0";
        long ms = Calendar.getInstance().getTimeInMillis();
        String signature = WeiXinUtils.getSignature(nonceStr, ms);
        String appid = AppInitializer.WeiXinConfig.getString("wx.corp_id");

        map.put("nonceStr", nonceStr);
        map.put("timestamp", ms);
        map.put("signature", signature);
        map.put("appId", appid);

        log.debug("nonceStr: " + nonceStr);
        log.debug("timestamp: " + ms);
        log.debug("signature: " + signature);
        log.debug("appId: " + appid);


        return "share";
    }


}
