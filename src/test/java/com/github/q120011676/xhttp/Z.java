package com.github.q120011676.xhttp;

/**
 * Created by say on 2/26/16.
 */
public class Z {
    public static void main(String[] args) {
        XHttp.url("https://storage.googleapis.com/golang/go1.6.linux-amd64.tar.gz").get().download("/tmp");
        System.out.println("end");
    }
}
