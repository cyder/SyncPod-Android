package com.cyder.atsushi.youtubesync.app_data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cyder.atsushi.youtubesync.MyApplication;
import com.cyder.atsushi.youtubesync.json_data.User;

/**
 * Created by atsushi on 2017/10/25.
 */

public final class MySelf {

    private static User myself;

    public static Boolean exists() {
        singIn();
        return (myself != null);
    }

    public static String getToken() {
        singIn();
        return myself.access_token;
    }

    public static void setToken(String token) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token);
        editor.apply();
        if (myself == null) {
            myself = new User();
        }
        myself.access_token = token;
    }

    private static void singIn() {
        if (myself == null) {
            SharedPreferences pref = getPref();
            String token = pref.getString("access_token", null);
            if (token != null) {
                myself = new User();
                myself.access_token = token;
            }
        }
    }

    private static SharedPreferences getPref() {
        Context context = MyApplication.getContext();
        return context.getSharedPreferences("youtube-sync", context.MODE_PRIVATE);
    }
}
