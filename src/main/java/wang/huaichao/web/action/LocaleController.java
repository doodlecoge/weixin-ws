package wang.huaichao.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by Administrator on 2015/6/10.
 */
@Controller
public class LocaleController {
    public static Locale locale = Locale.CHINA;
    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping("/setlocale/{locale}")
    public String changeLocal(@PathVariable String locale,
                              HttpServletRequest request,
                              HttpServletResponse response) {

        Locale loc = new Locale(locale);
        LocaleController.locale = loc;
        localeResolver.setLocale(request, response, loc);
        String referer = request.getHeader("Referer");
        if (referer == null || referer.isEmpty()) referer = "/";
        return "redirect:" + referer;

    }
}
