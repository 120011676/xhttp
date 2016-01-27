package org.say.xhttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import java.io.File;
import java.net.Proxy;
import java.util.Map;

/**
 * Created by say on 1/20/16.
 */
public interface Request {
    Request url(String url);

    Request proxy(Proxy proxy);

    Request sslSocketFactory(SSLSocketFactory sslsf);

    Request hostnameVerifier(HostnameVerifier hv);

    Request connectTimeout(int timeout);

    Request readTimeout(int timeout);

    Request followRedirects(boolean followRedirects);

    Request character(String character);

    Map<String, String> header();

    Request header(String name, String value);

    Request userAgent(String userAgent);

    Request contentType(String contentType);

    Request cookie(String cookie);

    Request data(byte[] data);

    Request data(String data);

    Request data(String name, String value);

    Request data(String name, File data);

    Request method(String method);

    Response get();

    Response post();

    Response execute();
}
