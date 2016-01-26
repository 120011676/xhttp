package org.say.xhttp;

/**
 * Created by say on 1/20/16.
 */
public class XHttp {

    private static HttpConfig CONFIG;

    private static XHttp xHttp = new XHttp();

    private XHttp() {
    }

    public static XHttp config(HttpConfig config) {
        CONFIG = config;
        return xHttp;
    }

    public static Request url(String url) {
        Request req = new HttpRequest().url(url);
        if (CONFIG != null) {
            req.proxy(CONFIG.getProxy());
            req.sslSocketFactory(CONFIG.getSslSocketFactory());
            req.hostnameVerifier(CONFIG.getHostnameVerifier());
            req.userAgent(CONFIG.getUserAgent());
            req.connectTimeout(CONFIG.getConnectTimeout());
            req.readTimeout(CONFIG.getReadTimeout());
            req.followRedirects(CONFIG.getFollowRedirects());
            req.character(CONFIG.getRequestCharacter());
        }
        return req;
    }
}
