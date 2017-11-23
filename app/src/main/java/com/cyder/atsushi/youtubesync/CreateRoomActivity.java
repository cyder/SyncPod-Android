package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.cyder.atsushi.youtubesync.json_data.Room;
import com.cyder.atsushi.youtubesync.server.CreateRoom;
import com.cyder.atsushi.youtubesync.server.CreateRoomInterface;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoomActivity extends AppCompatActivity
        implements CreateRoomInterface {

    private Snackbar snackbar;
    private InputMethodManager manager;


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
                        if (validData(name, description)) {
                            createRoom.post(name, description);
                        } else {
                            onCreateFailed();
                        }
                    }
                });
        snackbar = Snackbar.make(findViewById(R.id.create_room_layout), R.string.create_room_error, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    // データが正しいかチェック
    public boolean validData(final String name, final String description) {
        if(!(name.equals("") || description.equals(""))){
            return true;
        }else{
            snackbar.setText(R.string.form_not_filled);
            return false;
        }
    }

    // 作成失敗時の挙動
    @Override
    public void onCreateFailed(){
        // キーボード消す
        manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        snackbar.show();
    }

    @Override
    public void onCreatedRoom(Room room) {
        Intent intent = new Intent();
        intent.putExtra("room_key", room.key);
        setResult(RESULT_OK, intent);
        finish();
    }
}
