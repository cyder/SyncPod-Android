package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by chigichan24 on 2017/11/02.
 */

public class Token {
    @NonNull
    private SharedPreferences pref;
    Token(Context context) {
        pref = context.getSharedPreferences("youtube-sync", MODE_PRIVATE);
    }


    public void setToken(String token) {
        MySelf.singIn(token);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("access_token", token);
        editor.apply();
    }
}
