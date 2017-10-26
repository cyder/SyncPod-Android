package com.example.atsushi.youtubesync;

import com.example.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/10/25.
 */

public final class MySelf {

    private static User myself;

    public static Boolean exists() {
        return (myself != null);
    }

    public static String getToken() {
        return myself.access_token;
    }

    public static void singIn(String token) {
        myself = new User();
        myself.access_token = token;
    }
}
