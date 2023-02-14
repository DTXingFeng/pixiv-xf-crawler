package tool;

import java.io.File;

public class Cofing {
    private static String cookie;
    private static String FilePath;
    public Cofing(){
        File cookie1 = new File("cookie.txt");
        try {
            cookie = new FileDo(cookie1).copy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilePath() {
        if (!new File(FilePath).exists()) {
            new File(FilePath).mkdir();
        }
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getCookie() {
        return cookie;
    }
}
