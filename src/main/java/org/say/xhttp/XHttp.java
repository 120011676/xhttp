package org.say.xhttp;

/**
 * Created by say on 1/20/16.
 */
public class XHttp {

    private XHttp() {
    }

    public static XHttpConfig config(HttpConfig config) {
        return new XHttpConfig(config);
    }

    public static Request url(String url) {
        return new HttpRequest().url(url);
    }
}
