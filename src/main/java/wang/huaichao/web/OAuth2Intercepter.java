package wang.huaichao.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import wang.huaichao.utils.StringUtils;
import wang.huaichao.web.model.User;
import wang.huaichao.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * Created by Administrator on 2015/5/15.
 */
public class OAuth2Intercepter extends HandlerInterceptorAdapter {
    private static final Logger log =
            LoggerFactory.getLogger(OAuth2Intercepter.class);
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (handler instanceof ResourceHttpRequestHandler) return true;

        final HttpSession session = request.getSession();

        // developing
        if (AppContext.isDeveloping()) {
            session.setAttribute("wxid", "fake-wxid-0123456789");
        }

        HandlerMethod handler2 = (HandlerMethod) handler;
        WxIdRequired required = handler2.getMethodAnnotation(WxIdRequired.class);
        if (required != null && required.value() == false)
            return true;

        log.debug("@@@ request url:  " + request.getRequestURI());
        log.debug("@@@ context path: " + request.getContextPath());
        log.debug("@@@ servlet path: " + request.getServletPath());

        if (session.getAttribute("wxid") != null) {
            User user = userService.retrive(
                    session.getAttribute("wxid").toString()
            );

            log.debug("@@@ wxid: " + session.getAttribute("wxid").toString());

            if (user == null) {
                response.sendRedirect(
                        request.getContextPath() + "/user/binding"
                );
                return false;
            } else {
                return true;
            }
        }


        // get weixin id by oauth2

//        String reqUrl = request.getRequestURL().toString();
//        String param = request.getQueryString();
//        if (param != null) {
//            reqUrl += "?" + param;
//        }

        // @formatter:off
        String reqUrl = AppInitializer.WeiXinConfig.getString("app.base_url")
                + request.getContextPath()
                + request.getServletPath();
        // @formatter:on

        response.sendRedirect(
                request.getContextPath()
                        + "/oauth2?reqUrl="
                        + StringUtils.encodeUrl(reqUrl)
        );
        return false;
    }
}
