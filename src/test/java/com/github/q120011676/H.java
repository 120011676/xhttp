package com.github.q120011676;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by say on 1/26/16.
 */
public class H {

    public static void post(String httpsUrl, String xmlStr) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection) (new URL(httpsUrl)).openConnection();
            urlCon.setDoInput(true);
//            urlCon.setDoOutput(true);
            urlCon.setRequestMethod("GET");
//            urlCon.setRequestProperty("Content-Length",
//                    String.valueOf(xmlStr.getBytes().length));
            urlCon.setUseCaches(false);
            //设置为gbk可以解决服务器接收时读取的数据中文乱码问题
//            urlCon.getOutputStream().write(xmlStr.getBytes("gbk"));
//            urlCon.getOutputStream().flush();
//            urlCon.getOutputStream().close();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlCon.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        URL url = new URL("https://www.baidu.com/s?wd=21");
//        InputStream in = (InputStream) url.getContent();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        byte[] b = new byte[4096];
//        if (in != null) {
//            for (int n; (n = in.read(b)) != -1; ) {
//                baos.write(b, 0, n);
//            }
//        }
//        System.out.println(new String(baos.toByteArray()));
//        baos.close();
//        in.close();
        System.out.println(new URL("https://www.icloud.com/").getContent());

        post("https://www.icloud.com/",null);
    }
}
