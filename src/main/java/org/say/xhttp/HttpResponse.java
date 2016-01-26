package org.say.xhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        }
        return null;
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
        String charset = charset(this.contentType());
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream in = this.data()) {
            byte[] b = new byte[4096];
            if (in != null) {
                for (int n; (n = in.read(b)) != -1; ) {
                    baos.write(b, 0, n);
                }
            }
            if (this.character == null && charset == null) {
                Pattern p = Pattern.compile("(?<=<meta http-equiv=\"content-type\" content=\").*?(?=\")");
                String html = new String(baos.toByteArray());
                Matcher m = p.matcher(html);
                if (m.find()) {
                    return new String(baos.toByteArray(), charset(m.group()));
                }
                return html;
            } else if (this.character != null) {
                return new String(baos.toByteArray(), this.character);
            } else {
                return new String(baos.toByteArray(), charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
        return null;
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
}
