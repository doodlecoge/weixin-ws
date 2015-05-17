package wang.huaichao.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.service.UserService;

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

    @Autowired
    private UserService userService;

    @RequestMapping("/profile")
    public String index(HttpServletRequest request, ModelMap map) {
        final HttpSession session = request.getSession();
        final Object userid = session.getAttribute("wxid");

        map.put(
                "wxid",
                userid.toString().isEmpty() ? null : userid.toString()
        );
        return "user/profile";
    }

    @RequestMapping("/binding")
    @WxIdRequired(false)
    public String binding() {
        return "user/binding";
    }

    @RequestMapping("/bind")
    public String bind(
            @RequestParam String wxId,
            @RequestParam String wbxUsername,
            @RequestParam String wbxPassword,
            @RequestParam String wbxSiteUrl) {
        log.debug(wxId);
        log.debug(wbxUsername);
        log.debug(wbxPassword);
        log.debug(wbxSiteUrl);


        userService.create(wxId, wbxUsername, wbxPassword, wbxSiteUrl, null);

        return "user/profile";
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
