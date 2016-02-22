package com.github.q120011676.xhttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.net.Proxy;
import java.util.Map;

/**
 * Created by say on 1/20/16.
 */
public interface Request {

    /**
     * set http or https URL
     *
     * @param url URL
     * @return Request
     */
    Request url(String url);

    /**
     * set proxy http https
     *
     * @param proxy Prox
     * @return Request
     */
    Request proxy(Proxy proxy);

    /**
     * set https SSL
     *
     * @param sslsf SSLSocketFactory
     * @return Request
     */
    Request sslSocketFactory(SSLSocketFactory sslsf);

    /**
     * set doman verifier
     *
     * @param hv HostnameVerifier
     * @return Request
     */
    Request hostnameVerifier(HostnameVerifier hv);

    /**
     * set connect timeout
     *
     * @param timeout timeout (unit second)
     * @return Request
     */
    Request connectTimeout(int timeout);

    /**
     * set read timeout
     *
     * @param timeout timeout (unit second)
     * @return Request
     */
    Request readTimeout(int timeout);

    /**
     * set follow redirects
     *
     * @param followRedirects boolean value
     * @return Request
     */
    Request followRedirects(boolean followRedirects);

    /**
     * set request character encoding
     *
     * @param character request character encoding
     * @return Request
     */
    Request character(String character);

    /**
     * get request header info
     *
     * @return Map
     */
    Map header();

    /**
     * set request header info
     *
     * @param name  header name
     * @param value header value
     * @return Request
     */
    Request header(String name, String value);

    /**
     * set request header User-Agent
     *
     * @param userAgent User-Agent value
     * @return Request
     */
    Request userAgent(String userAgent);

    /**
     * set request header Content-Type
     *
     * @param contentType Content-Type  value
     * @return Request
     */
    Request contentType(String contentType);

    /**
     * set request header Cookie
     *
     * @param cookie Cookie value
     * @return Request
     */
    Request cookie(String cookie);

    /**
     * set body data
     *
     * @param data byte[]
     * @return Request
     */
    Request data(byte[] data);

    /**
     * set body data
     *
     * @param data String
     * @return Request
     */
    Request data(String data);

    /**
     * set body data
     *
     * @param name  String
     * @param value String
     * @return Request
     */
    Request data(String name, String value);

    /**
     * set body data
     *
     * @param name String
     * @param data file
     * @return Request
     */
    Request data(String name, File data);

    /**
     * set http https method
     *
     * @param method String default GET.(HttpMethod.POST HttpMethod.GET)
     * @return Request
     */
    Request method(String method);

    /**
     * HTTP or HTTPS method GET send
     *
     * @return Response
     */
    Response get();

    /**
     * HTTP or HTTPS method POST send
     *
     * @return Response
     */
    Response post();

    /**
     * custom HTTP or HTTPS send
     *
     * @return Response
     */
    Response execute();
}
