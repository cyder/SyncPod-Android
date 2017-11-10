package com.cyder.atsushi.youtubesync.server;
import com.cyder.atsushi.youtubesync.json_data.Room;

/**
 * Created by atsushi on 2017/11/07.
 */

public interface RoomInterface {
    void onReceived(final Room room);
}
