package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Cookie {
    private String cookie;
    public Cookie(){
        File cookie1 = new File("cookie.txt");
        try {
            cookie = new FileDo(cookie1).copy();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCookie() {
        return cookie;
    }
}
