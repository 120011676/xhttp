package com.github.q120011676;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLConnection;
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


    public Response character(String character) {
        this.character = character;
        return this;
    }


    public String header(String name) {
        return this.httpUrl.getHeaderField(name);
    }


    public Map header() {
        return this.httpUrl.getHeaderFields();
    }


    public String contentType() {
        return this.httpUrl.getContentType();
    }


    public String cookie() {
        return this.header(HttpHeader.SET_COOKIE);
    }


    public int code() {
        try {
            return this.httpUrl.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public String message() {
        try {
            return this.httpUrl.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public InputStream data() {
        try {
            return this.httpUrl.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String charset(String contentType) {
        if (contentType != null && contentType.length() > 0) {
            String[] vs = contentType.split(";");
            for (int i = 0; i < vs.length; i++) {
                String[] kv = vs[i].split("=");
                if ("CHARSET".equals(kv[0].toUpperCase())) {
                    return kv[1];
                }
            }
        }
        return null;
    }


    public String dataToString() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = this.data();
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

    public void handle(Handle handle) {
        try {
            handle.handle(this.httpUrl.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void download(String path) {
        this.download(path, null);
    }

    public void download(final String path, final String fileName) {
        this.handle(new Handle() {
            public void handle(InputStream data) throws Exception {
                if (HttpURLConnection.HTTP_OK == code()) {
                    String filename = null;
                    if (fileName == null) {
                        String cd = header(HttpHeader.CONTENT_DISPOSITION);
                        if (cd != null && !"".equals(cd.trim())) {
                            String[] str = cd.split(";");
                            for (int i = 0; i < str.length; i++) {
                                String[] kv = str[i].trim().split("=");
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
                    FileOutputStream fout = null;
                    try {
                        fout = new FileOutputStream(new File(path, filename));
                        byte[] bs = new byte[8192];
                        int size;
                        while ((size = data.read(bs)) != -1) {
                            fout.write(bs, 0, size);
                        }
                        fout.flush();
                    } finally {
                        if (fout != null) {
                            try {
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
    }
}
