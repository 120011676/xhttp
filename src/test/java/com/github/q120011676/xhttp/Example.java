package com.github.q120011676.xhttp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by say on 2/25/16.
 */
public class Example {
    public static void main(String[] args) {
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        String html = XHttp.url("https://www.baidu.com/s")
                .userAgent(userAgent)
                .data("wd", "ss&sc").get().dataToString();
        System.out.println(html);
    }

    public void a() {
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        XHttp.url("https://www.baidu.com/s")
                .character("")
                .connectTimeout(1000)
                .readTimeout(20000)
                .cookie("/")
                .userAgent(userAgent)
                .data("file",new File("/tmp/a.jpg"))
                .data("wd", "ss&sc").post().handle(new Handle() {
                    @Override
                    public void handle(InputStream data) {
                        try {
                            FileWriter fw = new FileWriter("/tmp/a.jpg");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
