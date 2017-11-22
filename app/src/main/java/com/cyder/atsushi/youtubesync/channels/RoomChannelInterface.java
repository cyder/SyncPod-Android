package com.cyder.atsushi.youtubesync.channels;

import com.google.gson.JsonElement;

/**
 * Created by atsushi on 2017/10/08.
 */

public interface RoomChannelInterface {
    void onConnected();
    void onRejected();
    void onReceived(JsonElement data);
    void onDisconnected();
    void onFailed();
}
