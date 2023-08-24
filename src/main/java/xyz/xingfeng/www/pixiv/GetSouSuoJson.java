package xyz.xingfeng.www.pixiv;

import tool.Cofing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetSouSuoJson {

    private String json;
    private String cookie = new Cofing().getCookie();
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36 Core/1.94.168.400 QQBrowser/11.0.5120.400";
    public GetSouSuoJson(String urlString){
        int num = 0;
        while (true) {
            num++;
            if (num >= 5){
                return;
            }
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.addRequestProperty("user-agent", userAgent);
                connection.addRequestProperty("cookie", cookie);
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    json = response.toString();
                    reader.close();
                }
            } catch (Exception e) {
                System.out.println("异常，准备重试");
            }
        }
    }

    public String getJson() {
        return json;
    }
}
