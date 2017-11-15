package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/11/15.
 */

public class JoinedRooms extends HttpRequestsHelper {
    private JoinedRoomsInterface listener = null;

    public JoinedRooms() {
        super();
    }

    public void setListener(JoinedRoomsInterface listener) {
        this.listener = listener;
    }

    public void get() {
        super.get(null, "joined_rooms", new HttpRequestCallback() {
            @Override
            public void call(Response response) {
                listener.onReceived(response.joined_rooms);
            }
        });
    }
}
