package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.atsushi.youtubesync.R;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoomActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        Toolbar toolbar = (Toolbar)findViewById(R.id.create_room_tool_bar);
        toolbar.setTitle("新規ルーム作成");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
