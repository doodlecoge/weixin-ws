package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.xmlapi.command.GetSessionInfoResult;
import com.tcl.meeting.wbxclient.xmlapi.command.ListSummarySessionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.huaichao.utils.StringUtils;
import wang.huaichao.web.WxIdRequired;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/28.
 */
@Controller
public class ListMeetingController {
    @Autowired
    private UserService userService;

    @WxIdRequired(false)
    @RequestMapping("/list")
    public String list(HttpServletRequest request, ModelMap map) throws WebExApiException {
        final User user = userService.retrive(request);
        final WebexUser wbxUser = ActionHelper.getWebexUserFrom(user);

        final ListSummarySessionResult result = WebExClientBuilder
                .getHostClientBuilder(wbxUser).build()
                .listSummarySession();

        if (result.isSuccess()) {
            final List<ListSummarySessionResult.Session> sessions
                    = result.getSessions();
            map.put("sessions", sessions);

            final HttpSession session = request.getSession();
            final Object wxid = session.getAttribute("wxid");
            map.put("wxid", StringUtils.b32encode(wxid.toString().getBytes()));
        }

        return "list";
    }

    @RequestMapping("/list/{key}")
    public String showMeetingDetails(@PathVariable String key,
                                     HttpServletRequest request) throws WebExApiException {
        final User user = userService.retrive(request);
        final WebexUser wbxUser = ActionHelper.getWebexUserFrom(user);

        final GetSessionInfoResult result = WebExClientBuilder
                .getHostClientBuilder(wbxUser).build()
                .getSessionInfo(key);

        return "meeting_details";

    }


}
