package wang.huaichao.misc;

/**
 * Created by Administrator on 2015/5/15.
 */
public class WxWsException extends RuntimeException {
    public WxWsException() {
        super();
    }

    public WxWsException(String message) {
        super(message);
    }

    public WxWsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WxWsException(Throwable cause) {
        super(cause);
    }

    protected WxWsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
