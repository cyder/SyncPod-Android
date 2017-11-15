package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.json_data.Room;
import com.cyder.atsushi.youtubesync.server.JoinedRooms;
import com.cyder.atsushi.youtubesync.server.JoinedRoomsInterface;

import java.util.ArrayList;

import com.cyder.atsushi.youtubesync.app_data.MySelf;

/**
 * Created by atsushi on 2017/11/03.
 */

public class TopActivity extends AppCompatActivity
        implements JoinedRoomsInterface, SwipeRefreshLayout.OnRefreshListener {
    private final int JOIN_ROOM_REQUEST_CODE = 100;
    private final int CREATE_ROOM_REQUEST_CODE = 200;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        if (savedInstanceState != null) {
            MySelf.restoreInstanceState(savedInstanceState);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.top_tool_bar);
        toolbar.setLogo(R.drawable.toolbar_logo);

        View rootView = findViewById(R.id.top_root_view);
        snackbar = Snackbar.make(rootView, R.string.reject_message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.ok, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.join_room_dialog, (ViewGroup) findViewById(R.id.join_room_dialog_root));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_joined_rooms);
        swipeRefreshLayout.setOnRefreshListener(this);

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
                        snackbar.dismiss();
                        Intent varIntent =
                                new Intent(TopActivity.this, CreateRoomActivity.class);
                        startActivityForResult(varIntent, CREATE_ROOM_REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getJoinedRooms();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK && intent != null) {
            if (requestCode == JOIN_ROOM_REQUEST_CODE) {
                String res = intent.getStringExtra("error");
                if (res.equals("actionCableRejected")) {
                    snackbar.show();
                }
            } else if (requestCode == CREATE_ROOM_REQUEST_CODE) {
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
    public void onReceived(final ArrayList<Room> joinedRooms) {
        runOnUiThread(new Runnable() {
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                showJoinedRooms(joinedRooms);
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        MySelf.saveInstanceState(savedInstanceState);
    }

    private void joinRoom(String roomKey) {
        snackbar.dismiss();
        Intent intent =
                new Intent(TopActivity.this, RoomActivity.class);
        intent.putExtra("room_key", roomKey);
        startActivityForResult(intent, JOIN_ROOM_REQUEST_CODE);
    }

    private void getJoinedRooms() {
        JoinedRooms joinedRooms = new JoinedRooms();
        joinedRooms.setListener(this);
        joinedRooms.get();
    }

    private void showJoinedRooms(final ArrayList<Room> joinedRooms) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.joined_rooms_list);
        layout.removeAllViews();
        for (final Room room : joinedRooms) {
            View view = getLayoutInflater().inflate(R.layout.room_card, null);
            TextView name = view.findViewById(R.id.room_name);
            TextView description = view.findViewById(R.id.room_description);
            name.setText(room.name);
            description.setText(room.description);
            view.findViewById(R.id.room_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    joinRoom(room.key);
                }
            });
            layout.addView(view);
        }

        View title = findViewById(R.id.online_users_title);
        if (joinedRooms.size() == 0) {
            title.setVisibility(View.GONE);
        } else {
            title.setVisibility(View.VISIBLE);
        }
    }
}
