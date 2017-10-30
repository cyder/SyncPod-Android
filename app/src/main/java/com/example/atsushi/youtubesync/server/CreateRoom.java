package com.example.atsushi.youtubesync.server;

import com.example.atsushi.youtubesync.json_data.JsonParameter;
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

    @Override
    protected void post(JsonParameter jsonParameter, String endPoint, PostCallback callback) {
        super.post(jsonParameter, endPoint, callback);
    }

    public void post(final String name, final String description) {
        post(new com.example.atsushi.youtubesync.json_data.CreateRoom(name, description), "rooms", new PostCallback() {
            @Override
            public void call(Response response) {
                listener.onCreatedRoom(response.room);
            }
        });
    }
}
