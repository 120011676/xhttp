package com.github.q120011676.xhttp;

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

    /**
     * get global proxy
     *
     * @return Proxy
     */
    public Proxy getProxy() {
        return proxy;
    }

    /**
     * set global proxy
     *
     * @param proxy Proxy
     */
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * get global SSLSocketFactory
     *
     * @return SSLSocketFactory
     */
    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    /**
     * set global SSLSocketFactory
     *
     * @param sslSocketFactory SSLSocketFactory
     */
    public void setSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
    }

    /**
     * get global HostnameVerifier
     *
     * @return HostnameVerifier
     */
    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    /**
     * set global HostnameVerifier
     *
     * @param hostnameVerifier HostnameVerifier
     */
    public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
    }

    /**
     * get global User-Agent
     *
     * @return global User-Agent
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * set global User-Agent
     *
     * @param userAgent global User-Agent
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * get global connect timeout
     *
     * @return global connect timeout (unit second)
     */
    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * set global connect timeout
     *
     * @param connectTimeout global connect timeout (unit second)
     */
    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * get global read timeout
     *
     * @return global read timeout
     */
    public Integer getReadTimeout() {
        return readTimeout;
    }

    /**
     * set global read timeout
     *
     * @param readTimeout global read timeout
     */
    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * get follow redirects
     *
     * @return boolean
     */
    public Boolean getFollowRedirects() {
        return followRedirects;
    }

    /**
     * set follow redirects
     *
     * @param followRedirects boolean
     */
    public void setFollowRedirects(Boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    /**
     * get request character encoding
     *
     * @return request character encoding
     */
    public String getRequestCharacter() {
        return requestCharacter;
    }

    /**
     * set request character encoding
     *
     * @param requestCharacter request character encoding
     */
    public void setRequestCharacter(String requestCharacter) {
        this.requestCharacter = requestCharacter;
    }

    /**
     * get response character encoding
     *
     * @return response character encoding
     */
    public String getResponseCharacter() {
        return responseCharacter;
    }

    /**
     * set response character encoding
     *
     * @param responseCharacter response character encoding
     */
    public void setResponseCharacter(String responseCharacter) {
        this.responseCharacter = responseCharacter;
    }
}
