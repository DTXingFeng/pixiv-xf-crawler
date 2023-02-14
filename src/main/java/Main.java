public class Main {
    public static void main(String[] args) {
        setProperty("127.0.0.1","7890");
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
