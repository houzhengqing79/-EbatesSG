package sg.com.ebates.weather.common.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResourceHelper {
    public static String readResource(Class<?> cls, String filePath) throws IOException {
        InputStream inputStream = cls.getResourceAsStream(filePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i = Integer.MAX_VALUE;
        while ((i = inputStream.read(buff, 0, buff.length)) > 0) {
            byteArrayOutputStream.write(buff, 0, i);
        }
        return byteArrayOutputStream.toString();
    }
}
