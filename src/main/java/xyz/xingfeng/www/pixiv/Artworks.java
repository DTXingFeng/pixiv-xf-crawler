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

    private String url = "https://www.pixiv.net/artworks/";
    private int like = 0;
    public Artworks(String id){
        //构建url
        url+=id;
        //请求页面
        GetArtworksHtml getArtworksHtml = new GetArtworksHtml(url);
        //获得网页信息
        html = getArtworksHtml.getHtml();
        //解析网页信息
        Document doc = Jsoup.parse(html);
        //筛选
        Elements select = doc.select("meta[name=\"preload-data\"]");
        String content = select.attr("content");
        //构建json
        JSONObject jsonObject = new JSONObject(content);
        //获得点赞数
        like = jsonObject.getJSONObject("illust").getJSONObject(id).getInt("bookmarkCount");
        System.out.println(like);
        //获得下载链接
        GetArtworksHtml getArtworksHtml1 = new GetArtworksHtml("https://www.pixiv.net/ajax/illust/105359936/pages?lang=zh");
        JSONObject dowUrl = new JSONObject(getArtworksHtml1.getHtml());
        System.out.println(getArtworksHtml1.getHtml());
        JSONArray body = dowUrl.getJSONArray("body");
        for (int i = 0; i<body.length(); i++){
            String downloadUrl = body.getJSONObject(i).getJSONObject("urls").getString("original");
            downloadUrls.add(downloadUrl);
        }

    }

}
