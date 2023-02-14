package xyz.xingfeng.www.pixiv;

import org.json.JSONArray;
import org.json.JSONObject;

public class SouSuo {
    static final String ALL = "all";
    static final String R18 = "r18";
    static final String SAFE = "safe";
    private String url = "";

    /**
     * 默认限制级
     */
    String type = "safe";

    /**
     * 当前页数默认为1
     */
    Integer page = 1;

    /**
     * 默认url头、尾
     */
    String https = "https://www.pixiv.net/ajax/search/artworks/";
    String after = "&lang=zh&s_mode=s_tag_full&p=";

    /**
     * 爬取搜索页的构建方法
     * @param s 搜索内容
     */
    public SouSuo(String s){
        //生成默认url
        url = https + s + "?&mode=safe" + after;
    }
    public SouSuo(String s,String mode){
        if (mode!=R18 && mode != SAFE && mode != ALL){
            try {
                throw new Exception("mode不是合法的类型");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        url = https + s + "?&mode=" + mode + after;
    }

    public void getJson(){
        GetSouSuoJson getSouSuoJson = new GetSouSuoJson(url + page);
        //得到json进行处理
        JSONObject jsonObject = new JSONObject(getSouSuoJson.getJson());
        JSONArray jsonArray = jsonObject.getJSONObject("body").getJSONObject("illustManga").getJSONArray("data");
        if (jsonArray.length() == 0){
            //结束
            return;
        }
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            System.out.println("id:"+jsonObject1.getString("id"));
            System.out.println("title:"+jsonObject1.getString("title"));
            System.out.println("----------");
        }
        page++;
    }

}
