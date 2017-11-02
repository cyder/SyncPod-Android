package com.example.atsushi.youtubesync;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    @NonNull
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("youtube-sync", MODE_PRIVATE);
        String token = pref.getString("access_token", null);
        if (token != null) {
            MySelf.singIn(token);
        }

        if (MySelf.exists()) {
            Intent intent = new Intent(MainActivity.this, TopActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, FirstStartActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
