package wang.huaichao.wx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/5/5.
 */
public class JsApiTicket {
    @SerializedName("ticket")
    private String ticlet;

    @SerializedName(("expires_in"))
    private int expiresIn;

    public String getTiclet() {
        return ticlet;
    }

    public void setTiclet(String ticlet) {
        this.ticlet = ticlet;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
