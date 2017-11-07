package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/11/07.
 */

public class Room extends Http {
    private RoomInterface listener = null;

    public Room() {
        super();
    }

    public void setListener(RoomInterface listener) {
        this.listener = listener;
    }

    public void get(final String roomKey) {
        super.get("?room_key=" + roomKey, "rooms", new PostCallback() {
            @Override
            public void call(Response response) {
                listener.onReceived(response.room);
            }
        });
    }
}
