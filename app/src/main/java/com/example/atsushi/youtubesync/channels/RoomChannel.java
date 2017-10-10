package com.example.atsushi.youtubesync.channels;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hosopy.actioncable.ActionCableException;
import com.hosopy.actioncable.Channel;
import com.hosopy.actioncable.Consumer;
import com.hosopy.actioncable.Subscription;

/**
 * Created by atsushi on 2017/10/08.
 */

public class RoomChannel {
    private RoomChannelInterface listener = null;
    private Channel roomChannel;
    private Consumer consumer;
    private Subscription subscription;

    public RoomChannel() {
        consumer = Cable.getCunsumer();
        roomChannel = new Channel("RoomChannel");
        subscription = consumer.getSubscriptions().create(roomChannel);

        subscription
            .onConnected(new Subscription.ConnectedCallback() {
                @Override
                public void call() {
                    listener.onConnected();
                }
            }).onRejected(new Subscription.RejectedCallback() {
            @Override
                public void call() {
                    // Called when the subscription is rejected by the server
                }
            }).onReceived(new Subscription.ReceivedCallback() {
                @Override
                public void call(JsonElement data) {
                    listener.onReceived(data);
                    // Called when the subscription receives data from the server
                }
            }).onDisconnected(new Subscription.DisconnectedCallback() {
                @Override
                public void call() {
                    listener.onDisconnected();
                }
            }).onFailed(new Subscription.FailedCallback() {
                @Override
                public void call(ActionCableException e) {
                    listener.onFailed();
                }
            });
    }

    public void setListener(RoomChannelInterface listener){
        this.listener = listener;
    }

    public void getPlayList() {
        subscription.perform("play_list");
    }

    public void getNowPlayingVideo() {
        subscription.perform("now_playing_video");
    }

    public void addVideo(String youtubeVideoId) {
        JsonObject params = new JsonObject();
        params.addProperty("youtube_video_id", youtubeVideoId);
        subscription.perform("add_video", params);
    }

    public void removeListener(){
        consumer.getSubscriptions().remove(subscription);
        this.listener = null;
    }
}
