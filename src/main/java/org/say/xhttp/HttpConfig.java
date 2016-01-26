package org.say.xhttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.net.Proxy;

/**
 * Created by say on 1/21/16.
 */
public class HttpConfig {
    private Proxy proxy;
    private SSLSocketFactory sslSocketFactory;
    private HostnameVerifier hostnameVerifier;
    private String userAgent;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Boolean followRedirects;
    private String requestCharacter;
    private String responseCharacter;

    public Proxy getProxy() {
        return proxy;
    }

    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Boolean getFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public String getRequestCharacter() {
        return requestCharacter;
    }

    public void setRequestCharacter(String requestCharacter) {
        this.requestCharacter = requestCharacter;
    }

    public String getResponseCharacter() {
        return responseCharacter;
    }

    public void setResponseCharacter(String responseCharacter) {
        this.responseCharacter = responseCharacter;
    }
}
