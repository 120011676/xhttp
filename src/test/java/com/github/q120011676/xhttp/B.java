package com.github.q120011676.xhttp;

/**
 * Created by say on 1/28/16.
 */
public class B {
    public static void main(String[] args) {
        String userAgent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
        String html = XHttp.url("https://www.baidu.com/s")
                .userAgent(userAgent)
                .data("wd", "ss&sc").get().dataToString();
        System.out.println(html);
    }
}
