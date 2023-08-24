package Swing;

import tool.Cofing;
import xyz.xingfeng.www.pixiv.SouSuo;

public class LoadCrawler {
    public LoadCrawler(String ip, String port, String tag, String type, int good){
        //设置本地代理
        setProperty(ip,port);
        Cofing cofing = new Cofing();
        //设置下载位置
        cofing.setFilePath("Pixiv");

        SouSuo souSuo = new SouSuo(tag,type,good);
        souSuo.getJson();
    }

    /**
     * 设置代理
     */
    public static void setProperty(String Host,String Port){
        System.setProperty("http.proxyHost", Host);
        System.setProperty("http.proxyPort", Port);

        // 对https也开启代理
        System.setProperty("https.proxyHost", Host);
        System.setProperty("https.proxyPort", Port);
    }
}
