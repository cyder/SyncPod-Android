package com.cyder.atsushi.youtubesync.app_data;

import android.support.annotation.NonNull;

import com.cyder.atsushi.youtubesync.json_data.Room;
import com.cyder.atsushi.youtubesync.json_data.Video;
import com.cyder.atsushi.youtubesync.server.RoomInterface;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/11/09.
 */

public class RoomData implements RoomInterface {
    @NonNull
    ArrayList<RoomDataInterface> listeners = new ArrayList<>();
    private Room room;
    private Video nowPlayingVideo;
    @NonNull
    private PlayList playList = new PlayList();
    @NonNull
    private ChatList chatList = new ChatList();
    @NonNull
    private OnlineUsersList onlineUsersList = new OnlineUsersList();

    public void setNowPlayingVideo(Video nowPlayingVideo) {
        this.nowPlayingVideo = nowPlayingVideo;
        playList.removeVideo(nowPlayingVideo);
        updated();
    }

    public Video getNowPlayingVideo() {
        return nowPlayingVideo;
    }

    public void clearNowPlayingVideo() {
        this.nowPlayingVideo = null;
        updated();
    }

    public void getRoomInfoByKey(String roomKey) {
        com.cyder.atsushi.youtubesync.server.Room room = new com.cyder.atsushi.youtubesync.server.Room();
        room.setListener(this);
        room.get(roomKey);
    }

    public Room getRoomInfomation() {
        return room;
    }

    @Override
    public void onReceived(final Room room) {
        this.room = room;
        onlineUsersList.setList(room.online_users);
        updated();
    }

    public PlayList getPlayList() {
        return playList;
    }

    public ChatList getChatList() {
        return chatList;
    }

    public OnlineUsersList getOnlineUsersList() {
        return onlineUsersList;
    }

    public void addListener(RoomDataInterface listener) {
        listeners.add(listener);
    }

    public void removeListener(RoomInterface listener) {
        listeners.remove(listener);
    }

    private void updated() {
        for (RoomDataInterface listener : listeners) {
            listener.updated();
        }
    }
}
