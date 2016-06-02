package com.github.q120011676.xhttp;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by say on 1/20/16.
 */
public interface Response {

    /**
     * set response character encoding
     *
     * @param character response character encoding
     * @return Response
     */
    Response character(String character);

    /**
     * get response header
     *
     * @param name response header name
     * @return response header value
     */
    String header(String name);

    /**
     * get response header
     *
     * @return response header value
     */
    Map header();

    /**
     * get response header Content-Type
     *
     * @return response header Content-Type
     */
    String contentType();

    /**
     * get response header Set-Cookie
     *
     * @return response header Set-Cookie
     */
    String cookie();

    /**
     * get HTTP HTTPS status
     *
     * @return HTTP HTTPS status
     */
    int code();

    /**
     * get HTTP HTTPS message
     *
     * @return get HTTP HTTPS message
     */
    String message();

    /**
     * get response success body
     *
     * @return response body
     */
    InputStream data();

    /**
     * get response error body
     *
     * @return response body
     */
    InputStream errorData();

    /**
     * get response success or error body
     *
     * @return response body
     */
    InputStream body();

    /**
     * get response body to String
     *
     * @return response success body to String
     */
    String dataToString();


    /**
     * get response error body to String
     *
     * @return response body to String
     */
    String errorDataToString();

    /**
     * get response success or error body
     *
     * @return response body
     */
    String bodyToString();

    /**
     * close connection
     */
    void close();

    /**
     * custom handle response body
     *
     * @param handle Handle
     */
    void handle(Handle handle);

    /**
     * download file
     *
     * @param path save file path
     */
    void download(String path);

    /**
     * download file
     *
     * @param path     save file path
     * @param fileName save file name
     */
    void download(String path, String fileName);
}
