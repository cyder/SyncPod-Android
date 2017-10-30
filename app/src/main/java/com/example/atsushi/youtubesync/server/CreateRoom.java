package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoom extends Post {
    private CreateRoomInterface listener = null;

    public void setListener(CreateRoomInterface listener) {
        this.listener = listener;
    }

    public CreateRoom() {
        super();
    }

    public void post(final String name, final String description) {
        super.post(new com.example.atsushi.youtubesync.json_data.CreateRoom(name, description), "rooms", new PostCallback() {
            @Override
            public void call(Response response) {
                listener.onCreatedRoom(response.room);
            }
        });
    }
}
