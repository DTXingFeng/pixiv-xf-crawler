package xyz.xingfeng.www.pixiv;

import tool.Cofing;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载图片
 */
public class DownloadImg {
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36 Core/1.94.168.400 QQBrowser/11.0.5120.400";
    private String cookie = new Cofing().getCookie();
    public DownloadImg(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("user-agent",userAgent);
            connection.addRequestProperty("cookie",cookie);
            connection.addRequestProperty("referer", "https://www.pixiv.net/");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String fileName = urlString.substring(urlString.lastIndexOf("/"));
                InputStream inputStream = connection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(new Cofing().getFilePath() + fileName);
                fileOutputStream.write(inputStream.readAllBytes());
                fileOutputStream.close();
                inputStream.close();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
