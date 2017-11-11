package com.cyder.atsushi.youtubesync;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import com.cyder.atsushi.youtubesync.app_data.MySelf;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
