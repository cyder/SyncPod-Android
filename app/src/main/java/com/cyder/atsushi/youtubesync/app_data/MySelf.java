package com.cyder.atsushi.youtubesync.app_data;

import android.os.Bundle;

import com.cyder.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/10/25.
 */

public final class MySelf {

    private static User myself;
    private static final String STATE_USER_TOKEN = "userToken";

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

    public static void saveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATE_USER_TOKEN, myself.access_token);
    }

    public static void restoreInstanceState(Bundle savedInstanceState) {
        singIn(savedInstanceState.getString(STATE_USER_TOKEN));
    }
}
