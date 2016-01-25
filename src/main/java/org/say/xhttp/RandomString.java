package org.say.xhttp;

import java.util.Random;

/**
 * Created by say on 1/21/16.
 */
public class RandomString {
    private final static String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private Random r = new Random();

    private String customChars;

    public RandomString() {
    }

    public RandomString(String customChars) {
        this.customChars = customChars;
    }

    public char next() {
        return customChars != null ? customChars.charAt(r.nextInt(customChars.length())) : CHARS.charAt(r.nextInt(CHARS.length()));
    }

    public String next(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(this.next());
        }
        return sb.toString();
    }
}
