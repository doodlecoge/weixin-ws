package wang.huaichao.web.action;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.huaichao.AppInitializer;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/4.
 */
@Controller
@RequestMapping("/")
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam String msg_signature,
                        @RequestParam String timestamp,
                        @RequestParam String nonce,
                        @RequestParam String echostr) {

        log.info(msg_signature);
        log.info(timestamp);
        log.info(nonce);
        log.info(echostr);

        String sToken = AppInitializer.WeiXinConfig.getString("wx.token");
        String sEncodingAESKey = AppInitializer.WeiXinConfig.getString("wx.encoding_aes_key");
        String sCorpID = AppInitializer.WeiXinConfig.getString("wx.corp_id");

        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(
                    sToken, sEncodingAESKey, sCorpID
            );
            final String echoMsg = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);
            return echoMsg;
        } catch (AesException e) {
            log.error("===", e);
        }
        return "error";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "test" + Calendar.getInstance().getTimeInMillis();
    }
}
