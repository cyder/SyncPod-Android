package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
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
    @NonNull
    private String shareMessage = "";
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
            shareMessage = String.format(getActivity().getResources().getString(R.string.share_room_key_message), room.name, room.key);
        }

        view.findViewById(R.id.share_room_key_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(getActivity());
                        builder.setText(shareMessage);
                        builder.setType("text/plain");
                        builder.startChooser();
                    }
                });
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
