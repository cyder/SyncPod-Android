package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.MainActivity;
import com.cyder.atsushi.youtubesync.R;
import com.cyder.atsushi.youtubesync.json_data.Response;

/**
 * Created by atsushi on 2017/10/27.
 */

public class CreateRoom extends HttpRequestsHelper {
    private CreateRoomInterface listener = null;

    public void setListener(CreateRoomInterface listener) {
        this.listener = listener;
    }

    public CreateRoom() {
        super();
    }

    public void post(final String name, final String description) throws Exception {
        // エラー処理（名前または説明がなかったとき）
        if (name.equals("") && description.equals("")) {
            throw new CreateRoomException(CreateRoomException.MISSING_BOTH_ARGUMENTS);
        } else if (name.equals("")) {
            throw new CreateRoomException(CreateRoomException.MISSING_NAME_ARGUMENT);
        } else if (description.equals("")) {
            throw new CreateRoomException(CreateRoomException.MISSING_DESCRIPTION_ARGUMENT);
        }

        super.post(new com.cyder.atsushi.youtubesync.json_data.CreateRoom(name, description), "rooms", new HttpRequestCallback() {
            @Override
            public void success(Response response) {
                listener.onCreatedRoom(response.room);
            }

            @Override
            public void failure() throws CreateRoomException {
                throw new CreateRoomException(CreateRoomException.NETWORK_ERROR);
            }
        });
    }
}


