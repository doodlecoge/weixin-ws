package wang.huaichao.web.model;

import wang.huaichao.utils.CtxUtils;
import wang.huaichao.web.AppInitializer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/6/9.
 */
public class DateTime extends Date implements Comparable<Date> {
    public DateTime(long date) {
        super(date);
    }

    public DateTime(Date date) {
        super(date.getTime());
    }

    public String getDateString() {
        return (getMonth() + 1)
                + CtxUtils.getMessage("str.month")
                + getDate()
                + CtxUtils.getMessage("str.date");
    }

    public String getWeekDay() {
        return CtxUtils.getMessage("str.day" + getDay());
    }
}
