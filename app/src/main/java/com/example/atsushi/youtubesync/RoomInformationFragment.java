package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Room;
import com.example.atsushi.youtubesync.server.RoomInterface;

/**
 * Created by atsushi on 2017/11/07.
 */

public class RoomInformationFragment extends Fragment
        implements RoomInterface {
    private Room room;
    TextView name;
    TextView description;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.room_information_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        name = view.findViewById(R.id.room_name);
        description = view.findViewById(R.id.room_description);
        if (room != null) {
            name.setText(room.name);
            description.setText(room.description);
        }
    }

    public void setRoom(String roomKey) {
        com.example.atsushi.youtubesync.server.Room room = new com.example.atsushi.youtubesync.server.Room();
        room.setListener(this);
        room.get(roomKey);
    }

    @Override
    public void onReceived(final Room room) {
        this.room = room;
    }
}
