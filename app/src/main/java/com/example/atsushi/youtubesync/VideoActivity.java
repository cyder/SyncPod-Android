package com.example.atsushi.youtubesync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.JsonElement;
import com.hosopy.actioncable.*;
import java.net.URI;
import java.net.URISyntaxException;

public class VideoActivity extends YouTubeFailureRecoveryActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        SetupActionCable();
        if (!wasRestored) {
            player.loadVideo("wKJ9KzGQq0w");
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    private void SetupActionCable() {
        try {
            URI uri = new URI("ws://59.106.220.89:3000/cable/");
            Consumer consumer = ActionCable.createConsumer(uri);

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

            consumer.connect();
        } catch (URISyntaxException e) {
            Log.e("App", "URISyntaxException");
        }
    }
}
