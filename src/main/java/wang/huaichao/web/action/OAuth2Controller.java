package wang.huaichao.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wang.huaichao.misc.AppInitializer;
import wang.huaichao.wx.WeiXinUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2015/5/7.
 */
@Controller
@RequestMapping("/oauth2")
public class OAuth2Controller {
    private static final Logger log = LoggerFactory.getLogger(
            OAuth2Controller.class);

    @RequestMapping(method = RequestMethod.GET)
    public String oauth2(@RequestParam String reqUrl,
                         HttpServletRequest request) {
        final String baseUrl = _getBaseUrl(reqUrl, request);
        log.info("baseUrl: " + baseUrl);
        return "redirect:" + oAuth2ReqUrl(baseUrl + "/oauth2back?reqUrl=" + reqUrl);
    }

    private static String _getBaseUrl(String encodedUrl,
                                      HttpServletRequest request) {
        final String sUrl = URLDecoder.decode(encodedUrl);
        final URL url;
        try {
            url = new URL(sUrl);
        } catch (MalformedURLException e) {
            log.error("", e);
            throw new RuntimeException("invalid url:" + sUrl);
        }
        return url.getProtocol() + "://" + url.getHost()
//                + ":" + (url.getPort() > 0 ? url.getPort() : 80)
                + request.getContextPath();
    }


    @RequestMapping("/oauth2back")
    public String oauth2back(@RequestParam String reqUrl,
                             @RequestParam String code,
                             @RequestParam String state,
                             HttpServletRequest request) {
        try {
            final String userInfo = WeiXinUtils.getUserInfo(code, state);
            log.info(userInfo);
            request.setAttribute("userid", userInfo);
        } catch (IOException e) {
            log.error("failed to get user info", e);
        }
        return "redirect:" + reqUrl;
    }

    private String oAuth2ReqUrl(String redirectUri) {
        try {
            redirectUri = java.net.URLEncoder.encode(redirectUri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
            throw new RuntimeException("failed to encode url: " + redirectUri);
        }

        String oauth2Url = "https://open.weixin.qq.com/connect/oauth2/authorize"
                + "?appid="
                + AppInitializer.WeiXinConfig.getString("wx.corp_id")
                + "&redirect_uri="
                + redirectUri
                + "&response_type=code&scope=snsapi_base&state="
                + AppInitializer.WeiXinConfig.getString("oauth2.state")
                + "#wechat_redirect";

        log.info("oauth2 request url: " + oauth2Url);

        return oauth2Url;
    }
}
