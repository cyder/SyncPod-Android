package com.example.atsushi.youtubesync.channels;

import android.util.Log;

import com.google.gson.JsonElement;
import com.hosopy.actioncable.ActionCableException;
import com.hosopy.actioncable.Channel;
import com.hosopy.actioncable.Consumer;
import com.hosopy.actioncable.Subscription;

/**
 * Created by atsushi on 2017/10/08.
 */

public class RoomChannel {
    public RoomChannel() {
        Consumer consumer = Cable.getCunsumer();

        Channel roomChannel = new Channel("RoomChannel");
        Subscription subscription = consumer.getSubscriptions().create(roomChannel);

        subscription
            .onConnected(new Subscription.ConnectedCallback() {
                @Override
                public void call() {
                    Log.d("App", "connected");
                    // Called when the subscription has been successfully completed
                }
            }).onRejected(new Subscription.RejectedCallback() {
            @Override
                public void call() {
                    // Called when the subscription is rejected by the server
                }
            }).onReceived(new Subscription.ReceivedCallback() {
                @Override
                public void call(JsonElement data) {
                    // Called when the subscription receives data from the server
                }
            }).onDisconnected(new Subscription.DisconnectedCallback() {
                @Override
                public void call() {
                    // Called when the subscription has been closed
                }
            }).onFailed(new Subscription.FailedCallback() {
                @Override
                public void call(ActionCableException e) {
                    Log.d("App", "failed");
                    // Called when the subscription encounters any error
                }
            });
    }
}
