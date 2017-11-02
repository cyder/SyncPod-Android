package com.example.atsushi.youtubesync;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private final int CREATE_ROOM_REQUEST_CODE = 200;
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
            initUI();
        } else {
            Intent intent = new Intent(MainActivity.this, FirstStartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initUI() {
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        toolbar.setTitle(R.string.title);
        final EditText roomKeyForm = (EditText) findViewById(R.id.room_key);

        findViewById(R.id.startButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomKey = roomKeyForm.getText().toString();
                        Intent varIntent =
                                new Intent(MainActivity.this, VideoActivity.class);
                        varIntent.putExtra("room_key", roomKey);
                        startActivity(varIntent);
                    }
                });

        findViewById(R.id.create_room_link)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent varIntent =
                                new Intent(MainActivity.this, CreateRoomActivity.class);
                        startActivityForResult(varIntent, CREATE_ROOM_REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && intent != null) {
            if (requestCode == CREATE_ROOM_REQUEST_CODE) {
                String res = intent.getStringExtra("room_key");
                EditText roomKeyForm = (EditText) findViewById(R.id.room_key);
                roomKeyForm.setText(res);
            }
        }
    }

}
