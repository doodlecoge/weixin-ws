package wang.huaichao.web;

import com.google.gson.JsonObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import wang.huaichao.misc.WxWsException;
import wang.huaichao.utils.GsonUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2015/5/8.
 */
@ControllerAdvice
public class Adviser {
    @ExceptionHandler({Exception.class, WxWsException.class})
    @WxIdRequired(false)
    public ModelAndView globalErrorHandler(HttpServletRequest request,
                                           HttpServletResponse response,
                                           Exception ex) {

        try {

            response.getWriter().write(
                    GsonUtils.buildResponse(true, ex.getMessage())
            );
            response.getWriter().close();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("message", ex.getMessage());
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
