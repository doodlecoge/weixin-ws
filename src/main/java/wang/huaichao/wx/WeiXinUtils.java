package wang.huaichao.wx;

import com.google.gson.JsonObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wang.huaichao.utils.GsonUtils;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.net.HttpUtils;
import wang.huaichao.utils.AccessTonkenManager;
import wang.huaichao.utils.StringUtils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class WeiXinUtils {
    private static final Logger log = LoggerFactory.getLogger(WeiXinUtils.class);

    private static final String sToken = AppInitializer.WeiXinConfig.getString("wx.token");
    private static final String sEncodingAESKey = AppInitializer.WeiXinConfig.getString("wx.encoding_aes_key");
    private static final String sCorpID = AppInitializer.WeiXinConfig.getString("wx.corp_id");
    private static final String sSecret = AppInitializer.WeiXinConfig.getString("wx.secret");
    public static final String sAccessToken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + sCorpID + "&corpsecret=" + sSecret;
    public static final String sPostMsgUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";
    public static final String sGetUserInfo = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=${ACCESS_TOKEN}&code=${CODE}&agentid=${AGENTID}";

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

    public static String EncryptMsg(String data,
                                    String timestamp,
                                    String nonce) throws AesException {
        try {
            return WeiXinCrypt.EncryptMsg(
                    data, timestamp, nonce
            );
        } catch (AesException e) {
            log.error("failed to encrypt message", e);
            log.error(data);
            log.error(timestamp);
            log.error(nonce);
            throw e;
        }
    }

    public static String getAccessToken() throws IOException {
        try {
            final HttpUtils httpUtils = new HttpUtils();
            return httpUtils.get(sAccessToken);
        } catch (IOException e) {
            log.error("get access token failed", e);
            throw e;
        }
    }

    public static String postMessage(String sAccessToken, String data) throws IOException {
        final HttpUtils httpUtils = new HttpUtils();
        try {
            return httpUtils.post(sPostMsgUrl + sAccessToken, data);
        } catch (IOException e) {
            log.error("post message failed: " + data);
            throw e;
        }
    }

    public static String buildTextMsg(List<String> toUsers,
                                      List<String> toParties,
                                      List<String> toTags,
                                      WeiXinMessageType type,
                                      String agentId,
                                      String content) {
        final JsonObject txt = new JsonObject();
        txt.addProperty("content", content);

        final JsonObject jobj = _buildCommonMsg(toUsers, toParties, toTags, agentId);
        jobj.addProperty("msgtype", "text");
        jobj.add("text", txt);

        return jobj.toString();
    }

    private static JsonObject _buildCommonMsg(List<String> toUsers,
                                              List<String> toParties,
                                              List<String> toTags,
                                              String agentId) {
        final JsonObject jobj = new JsonObject();
        if (toUsers != null && toUsers.size() > 0) {
            jobj.addProperty("touser", StringUtils.join(toUsers, "|"));
        }

        if (toParties != null && toParties.size() > 0) {
            jobj.addProperty("toparty", StringUtils.join(toParties, "|"));
        }

        if (toTags != null && toTags.size() > 0) {
            jobj.addProperty("totag", StringUtils.join(toTags, "|"));
        }

        jobj.addProperty("agentid", agentId);
        jobj.addProperty("safe", 0);

        return jobj;
    }


    // https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?
    // access_token=ACCESS_TOKEN&code=CODE&agentid=AGENTID

    public static String getUserInfo(String code, String agentid)
            throws IOException {
        final String url = org.apache.commons.lang3.StringUtils.replaceEach(
                sGetUserInfo,
                new String[]{
                        "${ACCESS_TOKEN}",
                        "${CODE}",
                        "${AGENTID}"
                },
                new String[]{
                        AccessTonkenManager.GetAccessToken(),
                        code, agentid
                });

        return new HttpUtils().get(url);
    }

    public static String getJsApiTicket() throws IOException {
        String url = AppInitializer.WeiXinConfig
                .getString("wx.jsapi_ticket.url") +
                AccessTonkenManager.GetAccessToken();
        final HttpUtils httpUtils = new HttpUtils();
        return httpUtils.get(url);
    }

    public static String getSignature(String noncestr, long timestamp) throws IOException, NoSuchAlgorithmException {
        // noncestr=Wm3WZYTPz0wzccnW
        // jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg
        // timestamp=1414587457
        // url=http://mp.weixin.qq.com

        String jsapi_ticket = AccessTonkenManager.GetJsApiTicket();
        String url = AppInitializer.WeiXinConfig.getString("site.url");

        String str = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + noncestr +
                "&timestamp=" + timestamp +
                "&url=" + url;


        final String str1 = _sha1(str);
        return str1;
    }

    private static String _sha1(String str)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }

    public static String getUserAvatar(String wxid) throws IOException {
        String userInfoUrl =
                AppInitializer.WeiXinConfig.getString("wx.get_user.url");
        userInfoUrl = userInfoUrl.replace(
                "${access_token}", AccessTonkenManager.GetAccessToken()
        ) + wxid;

        final HttpUtils httpUtils = new HttpUtils();
        final String json = httpUtils.get(userInfoUrl);
        final UserInfo userInfo = GsonUtils.j20(json, UserInfo.class);
        return userInfo.getAvatar();
    }
}
