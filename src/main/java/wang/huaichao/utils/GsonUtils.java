package wang.huaichao.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2015/5/5.
 */
public class GsonUtils {
    private static final Gson gson = new Gson();

    public static String o2j(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T j20(String sJson, Class<T> cls) {
        return gson.fromJson(sJson, cls);
    }

    public static String buildResponse(boolean error, String message) {
        final JsonObject jobj = new JsonObject();
        jobj.addProperty("error", error);
        jobj.addProperty("message", message);
        return jobj.toString();
    }

    public static String buildSuccessResponse() {
        final JsonObject jobj = new JsonObject();
        jobj.addProperty("error", false);
        return jobj.toString();
    }
}
