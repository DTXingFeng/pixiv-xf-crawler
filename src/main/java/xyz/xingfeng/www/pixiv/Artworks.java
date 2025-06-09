package xyz.xingfeng.www.pixiv;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class Artworks {
    private final String html;

    private ArrayList<String> downloadUrls = new ArrayList<>();

    private String url = "https://www.pixiv.net/ajax/illust/%s?lang=zh";
    private int like = 0;
    private int minLike = 0;
    String id = "0";

    public Artworks(String id) {
        this.id = id;
        //构建url
        url = String.format(url, id);
        //请求页面
        GetArtworksHtml getArtworksHtml = new GetArtworksHtml(url);
        //获得网页信息
        html = getArtworksHtml.getHtml();
        System.out.println(url);

    }

    public void setMinLike(int minLike) {
        this.minLike = minLike;
    }

    /**
     * 筛选
     */
    public void screen() {
        //构建json
        JSONObject jsonObject;
        int x = 0;
        //解析网页信息
        JSONObject json = new JSONObject(html);
        if (json.getBoolean("error")) {
            System.out.println("获取数据失败，可能是id错误");
            return;
        }
        //获得点赞数
        int like = json.getJSONObject("body").getInt("bookmarkCount");
        if (like < minLike) {
            return;
        }
        JSONArray body = json.getJSONArray("body");
        for (int i = 0; i < body.length(); i++) {
            String downloadUrl = body.getJSONObject(i).getJSONObject("urls").getString("original");
            downloadUrls.add(downloadUrl);
        }
    }

    public void Download() {
        if (downloadUrls.equals(new ArrayList<>())) {
            return;
        }
        for (String url : downloadUrls) {
            new DownloadImg(url);
        }
    }
}
