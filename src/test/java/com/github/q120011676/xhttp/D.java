package com.github.q120011676.xhttp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by say on 2/26/16.
 */
public class D {
    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        URL u = new URL("https://storage.googleapis.com/golang/go1.6.linux-amd64.tar.gz?a-z");
        System.out.println(u.getPath());
        System.out.println(u.getQuery());
        System.out.println(u.getRef());
        String f = u.getFile();
        int in = f.indexOf("?");
        String filename = f != null && !"".equals(f.trim()) ? f.substring(f.lastIndexOf("/") + 1, in > -1 ? in : f.length()) : null;
        System.out.println(filename);
        System.out.println(u.toURI().getScheme());
//        System.out.println(new File(u.toURI()));


        File file  = new File("/tmp", "hello.go");
        System.out.println(file);
    }
}
