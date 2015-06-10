package wang.huaichao.web.model;

import org.springframework.context.ApplicationContextAware;
import wang.huaichao.utils.CtxUtils;
import wang.huaichao.web.AppCtxHolder;
import wang.huaichao.web.AppInitializer;
import wang.huaichao.web.action.LocaleController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2015/6/9.
 */
public class Meeting {
    private String topic;
    private Date startTime;
    private String password;
    private String host;
    private String meetingNO;
    private int durationMinutes;
    private String sessionKey;


    /**
     * *********************************************
     */


    public String getWeekDay() {
        return CtxUtils.getMessage("str.day" + startTime.getDay());
    }

    public String getDate() {
        return (startTime.getMonth() + 1)
                + CtxUtils.getMessage("str.month")
                + startTime.getDate()
                + CtxUtils.getMessage("str.date");
    }

    public String getDuration() {
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String from = df.format(startTime);
        final long ms = startTime.getTime();
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms + durationMinutes * 60 * 1000);
        String to = df.format(cal.getTime());
        return from + " ~ " + to;
    }

    /**
     * *********************************************
     */

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMeetingNO() {
        return meetingNO;
    }

    public void setMeetingNO(String meetingNO) {
        this.meetingNO = meetingNO;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
