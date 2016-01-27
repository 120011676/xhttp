package org.say.xhttp;

/**
 * Created by say on 1/27/16.
 */
public class XHttpConfig {
    private HttpConfig config;

    public XHttpConfig(HttpConfig config) {
        this.config = config;
    }

    public Request url(String url) {
        HttpRequest req = new HttpRequest();
        req.url(url);
        if (config != null) {
            req.proxy(config.getProxy());
            req.sslSocketFactory(config.getSslSocketFactory());
            req.hostnameVerifier(config.getHostnameVerifier());
            req.userAgent(config.getUserAgent());
            req.connectTimeout(config.getConnectTimeout());
            req.readTimeout(config.getReadTimeout());
            req.followRedirects(config.getFollowRedirects());
            req.character(config.getRequestCharacter());
            req.responseCharacter(config.getResponseCharacter());
        }
        return req;
    }
}
