package wang.huaichao.utils;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public class StringUtils {
    public static String join(List<String> vals, String sep) {
        if (vals == null || vals.size() == 0) return "";
        String ret = "";
        for (int i = 0; i < vals.size(); i++) {
            if (i != 0) ret += sep;
            ret += vals.get(i);
        }
        return ret;
    }
}
