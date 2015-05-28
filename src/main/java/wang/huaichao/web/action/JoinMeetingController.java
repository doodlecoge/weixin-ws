package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.xmlapi.command.GetJoinMeetingUrlResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.utils.StringUtils;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2015/5/28.
 */
@Controller
public class JoinMeetingController {
    private Logger log = LoggerFactory.getLogger(JoinMeetingController.class);

    @Autowired
    private UserService userService;


    @RequestMapping("/join")
    public String join() {
        return "join";
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(@RequestParam String key,
                       HttpServletRequest request,
                       ModelMap map) throws WebExApiException {
        final HttpSession session = request.getSession();
        final Object wxid = session.getAttribute("wxid");
        return "redirect:/join/" + key + "/" +
                StringUtils.b32encode(wxid.toString().getBytes());
    }

    @RequestMapping("/join/{key}/{wxid}")
    @WxIdRequired(false)
    public String join(@PathVariable String key,
                       @PathVariable String wxid,
                       ModelMap map) throws WebExApiException {
        wxid = new String(StringUtils.b32decode(wxid));
        final User user = userService.retrive(wxid);
        final WebexUser webexUser = new WebexUser();
        webexUser.setWebexId(user.getWbxUsername());
        webexUser.setWebexPassword(user.getWbxPassword());
        webexUser.setWebexSiteName(user.getWbxSiteUrl());
        final String joinUrl = _getJoinUrl(key, webexUser);
        map.put("joinUrl", joinUrl);
        return "join-itmd";
    }

    @RequestMapping(
            value = "/save_join",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveJoin(
            HttpServletRequest request,
            @RequestParam String meetingNo,
            @RequestParam(required = false) String meetingPwd,
            ModelMap map) {

        final User user = userService.retrive(request);
        final WebexUser webexUser = new WebexUser();
        webexUser.setWebexId(user.getWbxUsername());
        webexUser.setWebexPassword(user.getWbxPassword());
        webexUser.setWebexSiteName(user.getWbxSiteUrl());

        try {
            String joinUrl = _getJoinUrl(meetingNo, webexUser);
            log.debug("join url: " + joinUrl);
            map.put("joinUrl", joinUrl);
            return "join-itmd";
        } catch (WebExApiException e) {
            throw new WxWsException("get join url failed", e);
        }
    }


    private String _getJoinUrl(String meetingKey, WebexUser user)
            throws WebExApiException {
        GetJoinMeetingUrlResult result = WebExClientBuilder
                .getHostClientBuilder(user).build()
                .getJoinUrl(meetingKey);
        return result.getJoinUrl();
    }
}
