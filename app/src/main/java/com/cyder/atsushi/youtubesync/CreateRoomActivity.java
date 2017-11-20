package com.cyder.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cyder.atsushi.youtubesync.json_data.Room;
import com.cyder.atsushi.youtubesync.server.CreateRoom;
import com.cyder.atsushi.youtubesync.server.CreateRoomInterface;
import com.cyder.atsushi.youtubesync.server.MissingArgumentsException;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.create_room_tool_bar);
        toolbar.setTitle(R.string.create_new_room);
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
                        try {
                            createRoom.post(name, description);
                        } catch (Exception e) {
                            String errMessage = "";
                            if (e.getMessage().equals(MissingArgumentsException.MISSING_BOTH_ARGUMENTS)) {
                                errMessage = "名前と説明を入力してください";
                            } else if (e.getMessage().equals(MissingArgumentsException.MISSING_NAME_ARGUMENT)) {
                                errMessage = "名前を入力してください";
                            } else if (e.getMessage().equals(MissingArgumentsException.MISSING_DESCRIPTION_ARGUMENT)) {
                                errMessage = "説明を入力してください";
                            }
                            e.printStackTrace();
                            Snackbar.make((LinearLayout) findViewById(R.id.create_room_layout),
                                    errMessage, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
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
