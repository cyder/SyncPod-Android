package com.cyder.atsushi.youtubesync;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.cyder.atsushi.youtubesync.app_data.MySelf;


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
            Intent topIntent = new Intent(MainActivity.this, TopActivity.class);
            Intent mainIntent = getIntent();
            if (mainIntent != null) {
                Uri uri = mainIntent.getData();
                if (uri != null) {
                    String path = uri.getPath();
                    String roomKey = uri.getQueryParameter("room_key");
                    if (path.equals("/room") && roomKey != null) {
                        topIntent.putExtra("room_key", roomKey);
                    }
                }
            }
            topIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(topIntent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, FirstStartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
