package com.github.q120011676.xhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by say on 1/26/16.
 */
public class C {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode("=tps发起post请","UTF-8"));
    }
}
