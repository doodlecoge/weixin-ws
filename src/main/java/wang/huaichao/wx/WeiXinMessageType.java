package wang.huaichao.wx;

/**
 * Created by Administrator on 2015/5/5.
 */
public enum WeiXinMessageType {
    text("text"),
    image("image"),
    voice("voice"),
    video("video"),
    file("file"),
    news("news"),
    mpnews("mpnews"),
    event("event");

    public String val;

    WeiXinMessageType(String val) {
        this.val = val;
    }
}
