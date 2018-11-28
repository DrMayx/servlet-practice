package code.drmayx;

import com.sun.net.httpserver.HttpExchange;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Utilities {

    public static Map<String, String> getParameters(HttpExchange httpExchange) throws IOException {
        Map<String, String> parameters = new HashMap<>();
        InputStream inputStream = httpExchange.getRequestBody();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int read = 0;
        while ((read = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, read);
        }
        String[] keyValuePairs = byteArrayOutputStream.toString().split("&");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split("=");
            if (keyValue.length != 2) {
                continue;
            }
            parameters.put(keyValue[0], keyValue[1]);
        }
        return parameters;
    }
}
