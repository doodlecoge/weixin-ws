package wang.huaichao.wx;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2015/5/5.
 */
public class AccessToken {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName(("expires_in"))
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
