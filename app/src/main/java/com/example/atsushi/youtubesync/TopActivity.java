package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

/**
 * Created by atsushi on 2017/11/03.
 */

public class TopActivity extends AppCompatActivity {
    private final int CREATE_ROOM_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tool_bar);
        toolbar.setTitle(R.string.title);
        final EditText roomKeyForm = (EditText) findViewById(R.id.room_key);

        findViewById(R.id.startButton)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String roomKey = roomKeyForm.getText().toString();
                        Intent varIntent =
                                new Intent(TopActivity.this, RoomActivity.class);
                        varIntent.putExtra("room_key", roomKey);
                        startActivity(varIntent);
                    }
                });

        findViewById(R.id.create_room_link)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent varIntent =
                                new Intent(TopActivity.this, CreateRoomActivity.class);
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
