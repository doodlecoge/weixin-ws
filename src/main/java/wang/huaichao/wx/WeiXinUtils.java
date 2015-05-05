package wang.huaichao.wx;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.huaichao.AppInitializer;

/**
 * Created by Administrator on 2015/5/5.
 */
public class WeiXinUtils {
    private static final Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

    private static final String sToken = AppInitializer.WeiXinConfig.getString("wx.token");
    private static final String sEncodingAESKey = AppInitializer.WeiXinConfig.getString("wx.encoding_aes_key");
    private static final String sCorpID = AppInitializer.WeiXinConfig.getString("wx.corp_id");

    private static WXBizMsgCrypt WeiXinCrypt = null;


    // IMPORTANT: call this method when servlet context initialized.
    public static void init() {
        try {
            WeiXinCrypt = new WXBizMsgCrypt(
                    sToken, sEncodingAESKey, sCorpID
            );
        } catch (AesException e) {
            log.error("failed to instantiate WXBizMsgCrypt", e);
            throw new RuntimeException("failed to instantiate WXBizMsgCrypt", e);
        }
    }

    public static String verifyURL(
            String msg_signature, String timestamp,
            String nonce, String echostr) throws AesException {
        try {
            return WeiXinCrypt.VerifyURL(
                    msg_signature, timestamp, nonce, echostr
            );
        } catch (AesException e) {
            log.error("failed to verify url", e);
            log.error(msg_signature);
            log.error(timestamp);
            log.error(nonce);
            log.error(echostr);
            throw e;
        }
    }


    public static String DecryptMsg(
            String msg_signature, String timestamp,
            String nonce, String data) throws AesException {
        try {
            return WeiXinCrypt.DecryptMsg(
                    msg_signature, timestamp, nonce, data
            );
        } catch (AesException e) {
            log.error("failed to decrypt message", e);
            log.error(msg_signature);
            log.error(timestamp);
            log.error(nonce);
            log.error(data);
            throw e;
        }
    }
}
