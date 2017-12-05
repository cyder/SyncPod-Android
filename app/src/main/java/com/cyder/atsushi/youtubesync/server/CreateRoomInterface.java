package com.cyder.atsushi.youtubesync.server;

import com.cyder.atsushi.youtubesync.json_data.Room;

/**
 * Created by atsushi on 2017/10/27.
 */

public interface CreateRoomInterface {
    void onCreatedRoom(Room room);
    void onCreateFailed();
}
