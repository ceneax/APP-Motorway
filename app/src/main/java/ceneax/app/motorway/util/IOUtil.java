package ceneax.app.motorway.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtil {

    /**
     * InputStream转String
     * @param inputStream 数据源
     * @return 返回转换后String
     * @throws IOException
     */
    public static String toString(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

}
