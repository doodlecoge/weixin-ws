package wang.huaichao.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/5/20.
 */
public class FileUtils {
    public static String loadFromClassPath(String path) throws IOException {
        final InputStream is = FileUtils.class.getResourceAsStream(path);
        int len;
        final byte[] buff = new byte[2048];
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while ((len = is.read(buff)) != -1) {
            baos.write(buff, 0, len);
        }
        is.close();
        return baos.toString();
    }
}
