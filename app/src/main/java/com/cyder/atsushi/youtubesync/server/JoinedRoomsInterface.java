package com.cyder.atsushi.youtubesync.server;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/11/15.
 */

public interface JoinedRoomsInterface {
    void onReceived(ArrayList<com.cyder.atsushi.youtubesync.json_data.Room> joinedRooms);
}
