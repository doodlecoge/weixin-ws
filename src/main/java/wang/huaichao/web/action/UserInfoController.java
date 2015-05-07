package wang.huaichao.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/5/7.
 */
@Controller
@RequestMapping("/user")
public class UserInfoController {
    private static final Logger log = LoggerFactory.getLogger(
            UserInfoController.class);

    @RequestMapping("/profile")
    public String index(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        if (session.getAttribute("userid") == null) {
            return "redirect:/oauth2?reqUrl=" + getBackUrl(request);
        } else {
            return "user/profile";
        }
    }

    private String getBackUrl(HttpServletRequest request) {
        String backUrl = request.getRequestURL().toString();
        String param = request.getQueryString();
        if (param != null) {
            backUrl += "?" + param;
        }

        log.info(backUrl);

        try {
            return java.net.URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url encoding failed", e);
            throw new RuntimeException("url encoding failed:" + backUrl);
        }
    }
}
