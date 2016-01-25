package org.say.xhttp;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by say on 1/21/16.
 */
public class T {

    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|\')?([^\\s,;\"\']*)");

    public static void main(String[] args) throws IOException {
//        URL u = new URL("http://www.baidu.com");
//        HttpURLConnection urlConn = (HttpURLConnection) u.openConnection();

        // 表达式对象
        Pattern p = Pattern.compile("(?<=<meta http-equiv=\"content-type\" content=\").*?(?=\")");
//        String reg = "meta http-equiv=\"Content-Type\" content=\".*?charset=(.*?)\"";
//        Pattern p = Pattern.compile("meta http-equiv=\"Content-Type\" content=\".*?charset=(.*?)\"");
//        Pattern p = Pattern.compile("(?<=<meta.*?content\\s*=\\s*\\\").*?(?=\\\")");

// 创建 Matcher 对象
        String r = XHttp.url("http://zhidao.baidu.com/link?url=CHjMDsMxDqb9qIchmjHA77k4itumRS-aLqj6T0qi_9lYzYxTnfAsKP0ksSF1dO29x8Xib6k2fx992t4KnHQZta").get().dataToString();
//        System.out.println(r);

        Matcher m = p.matcher(r);

// 是否找到匹配
        boolean found = m.find();

        if (found) {
            String foundstring = m.group();
            System.out.println(foundstring);
        }


    }
}
