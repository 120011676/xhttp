package com.github.q120011676.xhttp;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by say on 1/20/16.
 */
public class HttpRequest implements Request {
    private String urlStr;
    private Proxy proxy;
    private SSLSocketFactory sslsf;
    private HostnameVerifier hv;
    private Integer connectTimeout;
    private Integer readTimeout;
    private Boolean followRedirects;
    private String character;
    private String responseCharacter;
    private Map<String, String> requestHeaders = new HashMap<>();
    private String method = HttpMethod.GET;
    private String boundary;
    private final static String LOGO = "xhttp";
    private List<Object> data = new ArrayList<>();

    {
        this.userAgent(LOGO);
    }

    @Override
    public Request url(String url) {
        this.urlStr = url;
        return this;
    }

    @Override
    public Request proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public Request sslSocketFactory(SSLSocketFactory sslsf) {
        this.sslsf = sslsf;
        return this;
    }

    @Override
    public Request hostnameVerifier(HostnameVerifier hv) {
        this.hv = hv;
        return this;
    }

    @Override
    public Request connectTimeout(int timeout) {
        this.connectTimeout = timeout;
        return this;
    }

    @Override
    public Request readTimeout(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    @Override
    public Request followRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    @Override
    public Request character(String character) {
        this.character = character;
        return this;
    }

    @Override
    public Map<String, String> header() {
        return requestHeaders;
    }

    @Override
    public Request header(String name, String value) {
        this.requestHeaders.put(name, value);
        return this;
    }

    @Override
    public Request userAgent(String userAgent) {
        requestHeaders.put(HttpHeader.USER_AGENT, userAgent);
        return this;
    }

    @Override
    public Request contentType(String contentType) {
        requestHeaders.put(HttpHeader.CONTENT_TYPE, contentType);
        return this;
    }

    @Override
    public Request cookie(String cookie) {
        requestHeaders.put(HttpHeader.COOKIE, cookie);
        return this;
    }

    @Override
    public Request data(byte[] data) {
        this.data.add(data);
        return this;
    }

    @Override
    public Request data(String data) {
        this.data.add(data);
        return this;
    }

    @Override
    public Request data(String name, String value) {
        Map<String, String> m = new HashMap<>(1);
        m.put(name, value);
        this.data.add(m);
        return this;
    }

    @Override
    public Request data(String name, File data) {
        Map<String, File> m = new HashMap<>(1);
        m.put(name, data);
        this.data.add(m);
        if (requestHeaders.get(HttpHeader.CONTENT_TYPE) == null) {
            this.boundary = new RandomString().next(16);
            this.contentType("multipart/form-data; boundary=----" + LOGO + "_" + this.boundary);
        }
        return this;
    }

    @Override
    public Request method(String method) {
        this.method = method;
        return this;
    }

    @Override
    public Response get() {
        this.method = HttpMethod.GET;
        return this.execute();
    }

    @Override
    public Response post() {
        this.method = HttpMethod.POST;
        return this.execute();
    }

    @Override
    public Response execute() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (this.data != null && this.data.size() > 0) {
                for (Object obj : this.data) {
                    if (obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> m = (Map<String, Object>) obj;
                        for (String k : m.keySet()) {
                            Object o = m.get(k);
                            if (o instanceof File) {
                                File f = (File) o;
                                if (this.boundary != null) {
                                    baos.write(this.boundary.getBytes());
                                    baos.write("\r\n".getBytes());
                                    String cd = ("Content-Disposition: form-data; name=\"" + k + "\"; filename=\"" + f.getName() + "\"");
                                    baos.write(this.character != null ? cd.getBytes(this.character) : cd.getBytes());
                                    baos.write("\r\n".getBytes());
                                    baos.write(("Content-Type: " + new MimetypesFileTypeMap().getContentType(f)).getBytes());
                                    baos.write("\r\n\r\n".getBytes());
                                    byte[] b = new byte[4096];
                                    try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f))) {
                                        for (int n; (n = bin.read(b)) != -1; ) {
                                            baos.write(b, 0, n);
                                        }
                                    }
                                }
                            } else if (o instanceof String) {
                                String v = (String) o;
                                if (HttpMethod.POST.equals(this.method.toUpperCase())) {
                                    if (this.boundary != null) {
                                        baos.write(this.boundary.getBytes());
                                        baos.write("\r\n".getBytes());
                                        String cd = ("Content-Disposition: form-data; name=\"" + k + "\"");
                                        baos.write(this.character != null ? cd.getBytes(this.character) : cd.getBytes());
                                        baos.write("\r\n\r\n".getBytes());
                                        baos.write(v.getBytes());
                                    } else {
                                        if (baos.size() > 0) {
                                            baos.write('&');
                                        }
                                        String kv = (k + "=" + v);
                                        baos.write(this.character != null ? kv.getBytes(this.character) : kv.getBytes());
                                    }
                                } else if (HttpMethod.GET.equals(this.method.toUpperCase())) {
                                    this.urlStr += !this.urlStr.contains("?") ?
                                            '?' : '&';
                                    this.urlStr += (this.character != null ? URLEncoder.encode(k, this.character) : k) + "=" + (this.character != null ? URLEncoder.encode(v, this.character) : v);
                                }
                            }
                        }
                    } else if (obj instanceof byte[]) {
                        byte[] bs = (byte[]) obj;
                        if (HttpMethod.POST.equals(this.method.toUpperCase())) {
                            baos.write(bs);
                        } else if (HttpMethod.GET.equals(this.method.toUpperCase())) {
                            String v = (this.character != null ? new String(bs, this.character) : new String(bs));
                            this.urlStr += (this.urlStr.contains("?") ? '&' : '?') + (this.character != null ? URLEncoder.encode(v, this.character) : v);
                        }
                    } else if (obj instanceof String) {
                        String v = (String) obj;
                        if (HttpMethod.POST.equals(this.method.toUpperCase())) {
                            baos.write(this.character != null ? v.getBytes(this.character) : v.getBytes());
                        } else if (HttpMethod.GET.equals(this.method.toUpperCase())) {
                            this.urlStr += (this.urlStr.contains("?") ? '&' : '?') + (this.character != null ? URLEncoder.encode(v, this.character) : v);
                        }
                    }
                }
            }
            if (this.boundary != null) {
                baos.write((this.boundary + "--").getBytes());
            }
            URL url = new URL(this.urlStr);
            URLConnection urlConn = this.proxy != null ? url.openConnection(this.proxy) : url.openConnection();
            if (urlConn instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConn = (HttpsURLConnection) urlConn;
                if (this.sslsf != null) {
                    httpsConn.setSSLSocketFactory(this.sslsf);
                }
                if (this.hv != null) {
                    httpsConn.setHostnameVerifier(this.hv);
                }
            }
            HttpURLConnection httpUrl = (HttpURLConnection) urlConn;
            if (connectTimeout != null) {
                httpUrl.setConnectTimeout(connectTimeout);
            }
            if (readTimeout != null) {
                httpUrl.setReadTimeout(readTimeout);
            }
            if (followRedirects != null) {
                httpUrl.setInstanceFollowRedirects(followRedirects);
            }
            httpUrl.setRequestMethod(this.method);
            for (String k : requestHeaders.keySet()) {
                httpUrl.setRequestProperty(k, requestHeaders.get(k));
            }
            if (HttpMethod.POST.equals(this.method.toUpperCase())) {
                if (baos.size() > 0) {
                    httpUrl.setDoOutput(true);
                    try (OutputStream out = httpUrl.getOutputStream()) {
                        out.write(baos.toByteArray());
                        out.flush();
                    }
                }
            }
            return new HttpResponse(httpUrl).character(this.responseCharacter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Request responseCharacter(String character) {
        this.responseCharacter = character;
        return this;
    }
}
