import tool.Cofing;
import xyz.xingfeng.www.pixiv.Artworks;
import xyz.xingfeng.www.pixiv.DownloadImg;
import xyz.xingfeng.www.pixiv.GetArtworksHtml;
import xyz.xingfeng.www.pixiv.SouSuo;

public class Main {
    public static void main(String[] args) {
        //设置本地代理
        setProperty("127.0.0.1","7890");
        Cofing cofing = new Cofing();
        //设置下载位置
        cofing.setFilePath("Pixiv");

        SouSuo souSuo = new SouSuo("碧蓝档案");
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
