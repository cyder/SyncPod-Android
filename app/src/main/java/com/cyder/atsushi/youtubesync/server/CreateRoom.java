package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.BasicResponse;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoom extends HttpRequestsHelper {
    private CreateRoomInterface listener = null;

    public void setListener(CreateRoomInterface listener) {
        this.listener = listener;
    }

    public CreateRoom() {
        super(BasicResponse.class);
    }

    public void post(final String name, final String description) {
        super.post(new com.cyder.atsushi.youtubesync.json_data.CreateRoom(name, description), "rooms", new HttpRequestCallback() {
            @Override
            public void success(Object response) {
                BasicResponse r = (BasicResponse)response;
                listener.onCreatedRoom(r.room);
            }

            @Override
            public void failure() {

            }
        });
    }
}
