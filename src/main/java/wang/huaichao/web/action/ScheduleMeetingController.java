package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebExClientBuilder;
import com.tcl.meeting.wbxclient.WebexUser;
import com.tcl.meeting.wbxclient.exception.WebExApiException;
import com.tcl.meeting.wbxclient.xmlapi.command.CreateMeetingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/28.
 */
@Controller
public class ScheduleMeetingController {
    @Autowired
    private UserService userService;

    @RequestMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    @RequestMapping(
            value = "/save_schedule",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
//    @ResponseBody
    public String saveSchedule(
            HttpServletRequest request,
            @RequestParam String subject,
            @RequestParam String password,
            @RequestParam String confirmp,
            @RequestParam String startDate,
            @RequestParam String startTime) {

        if (!confirmp.equals(password))
            throw new WxWsException("two passwords not equal");

        if (subject.isEmpty())
            throw new WxWsException("subject is empty");

        if (startDate.isEmpty() || startTime.isEmpty())
            throw new WxWsException("datetime is empty");

        final User user = userService.retrive(request);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date;
        try {
            date = df.parse(startDate + " " + startTime);
        } catch (ParseException e) {
            date = new Date();
        }

        WebexUser webexUser = new WebexUser();
        webexUser.setWebexId(user.getWbxUsername());
        webexUser.setWebexPassword(user.getWbxPassword());
        webexUser.setWebexSiteName(user.getWbxSiteUrl());

        CreateMeetingResult createMeetingResult;
        try {
            createMeetingResult = WebExClientBuilder
                    .getHostClientBuilder(webexUser).build()
                    .createMeeting(subject, password, date);

        } catch (WebExApiException e) {
            throw new WxWsException("error while scheduling meeting", e);
        }

        if (!createMeetingResult.isSuccess())
            new WxWsException("schedule meeting failed");

//        return GsonUtils.buildSuccessResponse();

        return "redirect:/list";
    }
}
