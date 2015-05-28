package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.xmlapi.command.GetUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.utils.StringUtils;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;
import wang.huaichao.wx.WeiXinUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Administrator on 2015/5/28.
 */
@Controller
public class MeetingRoomController {
    private static final Logger log
            = LoggerFactory.getLogger(MeetingRoomController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/room")
    public String myroom(HttpServletRequest request) {
        final HttpSession session = request.getSession();
        final Object wxid = session.getAttribute("wxid");
        return "redirect:/room/"
                + StringUtils.b32encode(wxid.toString().getBytes());
    }


    @RequestMapping("/room/{wxid}")
    @WxIdRequired(false)
    public String myroom(@PathVariable String wxid,
                         ModelMap map) throws IOException {
        wxid = new String(StringUtils.b32decode(wxid));
        final User user = userService.retrive(wxid);

        if (user == null)
            return "redirect:/room";

        final WebexUser webexUser = new WebexUser();
        webexUser.setWebexSiteName(user.getWbxSiteUrl());
        webexUser.setWebexId(user.getWbxUsername());
        webexUser.setWebexPassword(user.getWbxPassword());

        GetUserResult result = null;
        try {
            result = WebExClientBuilder
                    .getHostClientBuilder(webexUser).build()
                    .getUser(user.getWbxUsername());
        } catch (WebExApiException e) {
            throw new WxWsException("获取信账户信息失败", e);
        }

        if (result.isSuccess()) {
            final String roomUrl = result.getPersonalMeetingRoomURL();
            log.debug("personal room url: " + roomUrl);
            map.put("roomUrl", roomUrl);
            map.put("avatar", WeiXinUtils.getUserAvatar(wxid));
            return "room";
        } else {
            throw new WxWsException("get personal meeting room failed");
        }
    }
}
