package wang.huaichao;

import com.google.gson.Gson;

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
}
