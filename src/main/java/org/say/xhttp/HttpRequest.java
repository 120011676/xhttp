package org.say.xhttp;

import javax.activation.MimetypesFileTypeMap;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.*;
import java.util.*;

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
    private Map requestHeaders = new HashMap();
    private String method = HttpMethod.GET;
    private String boundary;
    private final static String LOGO = "xhttp";
    private List data = new ArrayList();

    {
        this.userAgent(LOGO);
    }


    public Request url(String url) {
        this.urlStr = url;
        return this;
    }


    public Request proxy(Proxy proxy) {
        this.proxy = proxy;
        return this;
    }


    public Request sslSocketFactory(SSLSocketFactory sslsf) {
        this.sslsf = sslsf;
        return this;
    }


    public Request hostnameVerifier(HostnameVerifier hv) {
        this.hv = hv;
        return this;
    }


    public Request connectTimeout(int timeout) {
        this.connectTimeout = new Integer(timeout);
        return this;
    }


    public Request readTimeout(int timeout) {
        this.readTimeout = new Integer(timeout);
        return this;
    }


    public Request followRedirects(boolean followRedirects) {
        this.followRedirects = new Boolean(followRedirects);
        return this;
    }


    public Request character(String character) {
        this.character = character;
        return this;
    }


    public Map header() {
        return requestHeaders;
    }


    public Request header(String name, String value) {
        this.requestHeaders.put(name, value);
        return this;
    }


    public Request userAgent(String userAgent) {
        requestHeaders.put(HttpHeader.USER_AGENT, userAgent);
        return this;
    }


    public Request contentType(String contentType) {
        requestHeaders.put(HttpHeader.CONTENT_TYPE, contentType);
        return this;
    }


    public Request cookie(String cookie) {
        requestHeaders.put(HttpHeader.COOKIE, cookie);
        return this;
    }


    public Request data(byte[] data) {
        this.data.add(data);
        return this;
    }


    public Request data(String data) {
        this.data.add(data);
        return this;
    }


    public Request data(String name, String value) {
        Map m = new HashMap(1);
        m.put(name, value);
        this.data.add(m);
        return this;
    }


    public Request data(String name, File data) {
        Map m = new HashMap(1);
        m.put(name, data);
        this.data.add(m);
        if (requestHeaders.get(HttpHeader.CONTENT_TYPE) == null) {
            this.boundary = new RandomString().next(16);
            this.contentType("multipart/form-data; boundary=----" + LOGO + "_" + this.boundary);
        }
        return this;
    }


    public Request method(String method) {
        this.method = method;
        return this;
    }


    public Response get() {
        this.method = HttpMethod.GET;
        return this.execute();
    }


    public Response post() {
        this.method = HttpMethod.POST;
        return this.execute();
    }


    public Response execute() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (this.data != null && this.data.size() > 0) {
                for (int i = 0; i < this.data.size(); i++) {
                    Object obj = this.data.get(i);
                    if (obj instanceof Map) {
                        Map m = (Map) obj;
                        Iterator iterator = m.keySet().iterator();
                        while (iterator.hasNext()) {
                            String k = (String) iterator.next();
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
                                    BufferedInputStream bin = new BufferedInputStream(new FileInputStream(f));
                                    try {
                                        for (int n; (n = bin.read(b)) != -1; ) {
                                            baos.write(b, 0, n);
                                        }
                                    } finally {
                                        bin.close();
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
                httpUrl.setConnectTimeout(connectTimeout.intValue());
            }
            if (readTimeout != null) {
                httpUrl.setReadTimeout(readTimeout.intValue());
            }
            if (followRedirects != null) {
                httpUrl.setInstanceFollowRedirects(followRedirects.booleanValue());
            }
            httpUrl.setRequestMethod(this.method);
            Iterator iterator = requestHeaders.keySet().iterator();
            while (iterator.hasNext()) {
                String k = (String) iterator.next();
                httpUrl.setRequestProperty(k, (String) requestHeaders.get(k));
            }
            if (HttpMethod.POST.equals(this.method.toUpperCase())) {
                if (baos.size() > 0) {
                    httpUrl.setDoOutput(true);
                    OutputStream out = httpUrl.getOutputStream();
                    try {
                        out.write(baos.toByteArray());
                        out.flush();
                    } finally {
                        out.close();
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
