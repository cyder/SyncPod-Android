package com.example.atsushi.youtubesync;

/**
 * Created by atsushi on 2017/10/25.
 */

public final class MySelf {
    private static String token = null;

    public static Boolean exists() {
        return (token != null);
    }

    public static String getToken() {
        return token;
    }
}
