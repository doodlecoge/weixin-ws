package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebExClient;
import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.urlapi.command.ResultStatus;
import com.tcl.meeting.wbxclient.urlapi.command.user.GetEncryptedPasswordResponse;
import com.tcl.meeting.wbxclient.urlapi.command.user.GetSessionTicketResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.model.User;
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

    @WxIdRequired(false)
    @RequestMapping(value = "/bind", method = RequestMethod.POST)
    public String bind(
            HttpServletRequest request,
            @RequestParam String wbxUsername,
            @RequestParam String wbxPassword,
            @RequestParam String wbxSiteUrl) {
        log.debug(wbxUsername);
        log.debug(wbxPassword);
        log.debug(wbxSiteUrl);

        final HttpSession session = request.getSession();
        String wxId = session.getAttribute("wxid").toString();


        final User user = userService.retrive(wxId);

        if (user != null)
            throw new RuntimeException("user already exists: " + wxId);

        if (wbxSiteUrl.indexOf(".") > 0) {
            wbxSiteUrl = wbxSiteUrl.substring(0, wbxSiteUrl.indexOf("."));
        }

        try {
            String ticket = checkWebExAccount(
                    wbxUsername, wbxPassword, wbxSiteUrl
            );
            if (ticket == null) throw new WebExApiException("ticket is null");
        } catch (WebExApiException e) {
            throw new WxWsException("failed to check WebEx account", e);
        }
        userService.create(wxId, wbxUsername, wbxPassword, wbxSiteUrl);

        return "schedule";
    }


    private String checkWebExAccount(String wbxId, String wbxPwd, String wbxUrl)
            throws WebExApiException {
        WebexUser user = new WebexUser();
        user.setWebexId(wbxId);
        user.setWebexPassword(wbxPwd);
        user.setWebexSiteName(wbxUrl);

        WebExClient client = WebExClientBuilder
                .getHostClientBuilder(user).build();
        String ticket = null;

        GetEncryptedPasswordResponse password =
                client.getEncryptedPassword(wbxId, wbxPwd);
        String encryptedPassword = password.getEncryptedPassword();
        GetSessionTicketResponse response =
                client.getSessionTicket(wbxId, encryptedPassword);
        if (response != null &&
                response.getResult().equals(ResultStatus.SUCCESS)) {
            ticket = response.getSessionTicket();
        }

        return ticket;
    }
}
