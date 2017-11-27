package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.BasicResponse;

/**
 * Created by atsushi on 2017/11/15.
 */

public class JoinedRooms extends HttpRequestsHelper {
    private JoinedRoomsInterface listener = null;

    public JoinedRooms() {
        super(BasicResponse.class);
    }

    public void setListener(JoinedRoomsInterface listener) {
        this.listener = listener;
    }

    public void get() {
        super.get(null, "joined_rooms", new HttpRequestCallback() {
            @Override
            public void success(Object response) {
                BasicResponse r = (BasicResponse)response;
                listener.onReceived(r.joined_rooms);
            }

            @Override
            public void failure() {
                //TODO implement
            }
        });
    }
}
