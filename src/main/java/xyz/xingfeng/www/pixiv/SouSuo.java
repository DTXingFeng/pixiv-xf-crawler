package xyz.xingfeng.www.pixiv;

import Swing.MainWindows;
import org.json.JSONArray;
import org.json.JSONObject;
import tool.FileDo;

import java.io.File;
import java.util.ArrayList;

public class SouSuo {
    public static final String ALL = "all";
    public static final String R18 = "r18";
    public static final String SAFE = "safe";
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
     * 筛选条件
     */
    int good = 0;

    /**
     * 默认url头、尾
     */
    String https = "https://www.pixiv.net/ajax/search/artworks/";
    String after = "&lang=zh&s_mode=s_tag_full&p=";
    ArrayList<String> ids = new ArrayList<>();

    /**
     * 爬取搜索页的构建方法
     * @param s 搜索内容
     */
    public SouSuo(String s,int good){
        //生成默认url
        url = https + s + "?&mode=safe" + after;
    }
    public SouSuo(String s,String mode,int good){
        if (mode!=R18 && mode != SAFE && mode != ALL){
            try {
                throw new Exception("mode不是合法的类型");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        this.good = good;
        url = https + s + "?&mode=" + mode + after;
    }

    public void getJson(){
        while (true) {
            GetSouSuoJson getSouSuoJson = new GetSouSuoJson(url + String.valueOf(page));
            //得到json进行处理
            JSONObject jsonObject = new JSONObject(getSouSuoJson.getJson());
            JSONArray jsonArray = jsonObject.getJSONObject("body").getJSONObject("illustManga").getJSONArray("data");
            if (jsonArray.length() == 0) {
                //结束
                break;
            }
            FileDo data = new FileDo(new File("data"));
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                System.out.println("----------");
                System.out.println("id:" + jsonObject1.getString("id"));
                String id = jsonObject1.getString("id");
                System.out.println("title:" + jsonObject1.getString("title"));
                if (data.duibi(id) != 0){
                    continue;
                }
                Artworks artworks = new Artworks(id);
                artworks.setMinLike(good);
                artworks.screen();
                artworks.Download();
                data.zuijia("\n"+id);
                try {
                    Thread.sleep((long) (((int) 1+Math.random()*(4)) * 1000));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            page++;
            try {
                Thread.sleep((long) (((int) 1+Math.random()*(4)) * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        MainWindows.jB1.setEnabled(true);
    }

}
