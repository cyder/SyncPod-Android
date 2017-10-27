package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atsushi.youtubesync.R;
import com.example.atsushi.youtubesync.json_data.Room;
import com.example.atsushi.youtubesync.server.CreateRoom;
import com.example.atsushi.youtubesync.server.CreateRoomInterface;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoomActivity extends AppCompatActivity
    implements CreateRoomInterface {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        final CreateRoom createRoom = new CreateRoom();
        createRoom.setListener(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.create_room_tool_bar);
        toolbar.setTitle("新規ルーム作成");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final EditText nameForm = (EditText) findViewById(R.id.create_room_name);
        final EditText descriptionForm = (EditText) findViewById(R.id.create_room_description);

        ((Button) findViewById(R.id.create_room_submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameForm.getText().toString();
                        String description = descriptionForm.getText().toString();
                        createRoom.post(name, description);
                    }
                });
    }

    @Override
    public void onCreatedRoom(Room room) {
        Intent intent = new Intent();
        intent.putExtra("room_key", room.key);
        setResult(RESULT_OK, intent);
        finish();
    }
}
