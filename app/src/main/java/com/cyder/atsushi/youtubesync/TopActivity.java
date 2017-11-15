package com.cyder.atsushi.youtubesync;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cyder.atsushi.youtubesync.json_data.Room;
import com.cyder.atsushi.youtubesync.server.JoinedRooms;
import com.cyder.atsushi.youtubesync.server.JoinedRoomsInterface;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/11/03.
 */

public class TopActivity extends AppCompatActivity
        implements JoinedRoomsInterface, SwipeRefreshLayout.OnRefreshListener {
    private final int CREATE_ROOM_REQUEST_CODE = 200;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_tool_bar);
        toolbar.setLogo(R.drawable.toolbar_logo);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.join_room_dialog, (ViewGroup) findViewById(R.id.join_room_dialog_root));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_joined_rooms);
        swipeRefreshLayout.setOnRefreshListener(this);
        getJoinedRooms();

        final AlertDialog dialog = new AlertDialog.Builder(TopActivity.this)
                .setTitle(R.string.join_room)
                .setView(layout)
                .setPositiveButton(R.string.send_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        EditText input = (EditText) layout.getFocusedChild();
                        joinRoom(input.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel_button, null)
                .create();

        findViewById(R.id.join_room_card)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.show();
                    }
                });

        findViewById(R.id.create_room_card)
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
                joinRoom(res);
            }
        }
    }

    @Override
    public void onRefresh() {
        getJoinedRooms();
    }

    @Override
    public void onReceived(ArrayList<Room> joinedRooms) {
        swipeRefreshLayout.setRefreshing(false);
        Log.d("App", "onReceived");
    }

    private void joinRoom(String roomKey) {
        Intent intent =
                new Intent(TopActivity.this, RoomActivity.class);
        intent.putExtra("room_key", roomKey);
        startActivity(intent);
    }

    private void getJoinedRooms() {
        JoinedRooms joinedRooms = new JoinedRooms();
        joinedRooms.setListener(this);
        joinedRooms.get();
    }
}
