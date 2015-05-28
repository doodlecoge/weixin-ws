package wang.huaichao.web.action;

import com.tcl.meeting.wbxclient.WebexUser;
import wang.huaichao.web.model.User;

/**
 * Created by Administrator on 2015/5/28.
 */
public class ActionHelper {
    public static WebexUser getWebexUserFrom(User user) {
        final WebexUser webexUser = new WebexUser();
        webexUser.setWebexPassword(user.getWbxPassword());
        webexUser.setWebexId(user.getWbxUsername());
        webexUser.setWebexSiteName(user.getWbxSiteUrl());
        return webexUser;
    }
}
