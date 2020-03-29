package cn.andyjee.jnas.ddns.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author AndyJee
 */
public class IpUtil {

    /**
     * 获取当前主机公网IP
     */
    public static String getCurrentHostIP() {
        // 这里使用jsonip.com第三方接口获取本地IP
        String jsonip = "https://jsonip.com/";
        // 接口返回结果
        String result = "";
        BufferedReader in = null;
        try {
            // 使用HttpURLConnection网络请求第三方接口
            URL url = new URL(jsonip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        // 正则表达式，提取xxx.xxx.xxx.xxx，将IP地址从接口返回结果中提取出来
        String rexp = "(\\d{1,3}\\.){3}\\d{1,3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(result);
        String res = "";
        while (mat.find()) {
            res = mat.group();
            break;
        }
        return res;
    }
}
