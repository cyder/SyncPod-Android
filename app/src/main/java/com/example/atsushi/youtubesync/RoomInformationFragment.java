package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Room;

/**
 * Created by atsushi on 2017/11/07.
 */

public class RoomInformationFragment extends Fragment {
    private Room room;

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
        TextView title = (TextView) view.findViewById(R.id.room_title);
        TextView description = (TextView) view.findViewById(R.id.room_description);
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
