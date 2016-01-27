package org.say.xhttp;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by say on 1/20/16.
 */
public interface Response {

    Response character(String character);

    String header(String name);

    Map header();

    String contentType();

    String cookie();

    int code();

    String message();

    InputStream data();

    String dataToString();

    void close();
}
