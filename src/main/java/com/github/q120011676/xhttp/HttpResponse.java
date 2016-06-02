package com.github.q120011676.xhttp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by say on 1/20/16.
 */
public class HttpResponse implements Response {
    private String character;
    private HttpURLConnection httpUrl;

    public HttpResponse(URLConnection urlConnection) {
        this.httpUrl = (HttpURLConnection) urlConnection;
    }

    public HttpResponse(HttpURLConnection httpURLConnection) {
        this.httpUrl = httpURLConnection;
    }

    @Override
    public Response character(String character) {
        this.character = character;
        return this;
    }

    @Override
    public String header(String name) {
        return this.httpUrl.getHeaderField(name);
    }

    @Override
    public Map<String, List<String>> header() {
        return this.httpUrl.getHeaderFields();
    }

    @Override
    public String contentType() {
        return this.httpUrl.getContentType();
    }

    @Override
    public String cookie() {
        return this.header(HttpHeader.SET_COOKIE);
    }

    @Override
    public int code() {
        try {
            return this.httpUrl.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String message() {
        try {
            return this.httpUrl.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public InputStream data() {
        try {
            return this.httpUrl.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public InputStream errorData() {
        return this.httpUrl.getErrorStream();
    }

    @Override
    public InputStream body() {
        try {
            return this.httpUrl.getInputStream();
        } catch (IOException e) {
            return this.httpUrl.getErrorStream();
        }
    }

    private String charset(String contentType) {
        if (contentType != null && contentType.length() > 0) {
            String[] vs = contentType.split(";");
            for (String v : vs) {
                String[] kv = v.split("=");
                if ("CHARSET".equals(kv[0].toUpperCase())) {
                    return kv[1];
                }
            }
        }
        return null;
    }

    @Override
    public String dataToString() {
        return read(this.data());
    }

    private String read(InputStream in) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[4096];
            if (in != null) {
                for (int n; (n = in.read(b)) != -1; ) {
                    baos.write(b, 0, n);
                }
            }
            if (this.character == null) {
                String charset = charset(this.contentType());
                if (charset == null) {
                    Pattern p = Pattern.compile("(?<=<meta http-equiv=\"content-type\" content=\").*?(?=\")");
                    String html = new String(baos.toByteArray());
                    Matcher m = p.matcher(html);
                    if (m.find()) {
                        return new String(baos.toByteArray(), charset(m.group()));
                    }
                    return html;
                }
                return new String(baos.toByteArray(), charset);
            }
            return new String(baos.toByteArray(), this.character);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.close();
        }
        return null;
    }

    @Override
    public String errorDataToString() {
        return this.read(this.errorData());
    }

    @Override
    public String bodyToString() {
        return this.read(this.body());
    }

    @Override
    public void close() {
        if (this.data() != null) {
            try {
                this.data().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (this.httpUrl != null) {
            this.httpUrl.disconnect();
        }
    }

    @Override
    public void handle(Handle handle) {
        try {
            handle.handle(this.httpUrl.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    @Override
    public void download(String path) {
        this.download(path, null);
    }

    @Override
    public void download(final String path, final String fileName) {
        this.handle(new Handle() {
            @Override
            public void handle(InputStream data) throws Exception {
                if (HttpURLConnection.HTTP_OK == code()) {
                    String filename = null;
                    if (fileName == null) {
                        String cd = header(HttpHeader.CONTENT_DISPOSITION);
                        if (cd != null && !"".equals(cd.trim())) {
                            String[] str = cd.split(";");
                            for (String s : str) {
                                String[] kv = s.trim().split("=");
                                if (kv.length > 1 && "filename".equals(kv[0].trim())) {
                                    filename = kv[1].trim();
                                    break;
                                }
                            }
                        }
                        if (filename == null) {
                            String f = httpUrl.getURL().getFile();
                            int in = f.indexOf("?");
                            filename = !"".equals(f.trim()) ? f.substring(f.lastIndexOf("/") + 1, in > -1 ? in : f.length()) : null;
                        }
                    } else {
                        filename = fileName;
                    }
                    if (filename == null) {
                        throw new RuntimeException("fileName is null");
                    }
                    try (FileOutputStream fout = new FileOutputStream(new File(path, filename))) {
                        byte[] bs = new byte[8192];
                        int size;
                        while ((size = data.read(bs)) != -1) {
                            fout.write(bs, 0, size);
                        }
                        fout.flush();
                    }
                }
            }
        });
    }
}
