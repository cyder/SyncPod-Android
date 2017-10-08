package com.example.atsushi.youtubesync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.atsushi.youtubesync.channels.RoomChannel;
import com.example.atsushi.youtubesync.channels.RoomChannelInterface;
import com.example.atsushi.youtubesync.json_data.JsonData;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class VideoActivity extends YouTubeFailureRecoveryActivity implements RoomChannelInterface {
    RoomChannel roomChannel;
    YouTubePlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        roomChannel = new RoomChannel();
        roomChannel.setListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        roomChannel.removeListener();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        roomChannel = new RoomChannel();
        roomChannel.setListener(this);
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            this.player = player;
        }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    @Override
    public void onConnected() {
        Log.d("App", "connected");
    }

    @Override
    public void onReceived(JsonElement data) {
        Gson gson = new Gson();
        JsonData jsonData = gson.fromJson(data.getAsString(), JsonData.class);

        switch (jsonData.data_type) {
            case "now_playing_video":
                if(jsonData.data != null) {
                    player.loadVideo(jsonData.data.video.youtube_video_id, jsonData.data.video.current_time * 1000);
                }
                break;
            case "add_video":
                break;
            case "start_video":
                if(jsonData.data != null) {
                    player.loadVideo(jsonData.data.video.youtube_video_id);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDisconnected() {
        Log.d("App", "disconnected");
    }

    @Override
    public void onFailed() {
        Log.d("App", "failed");
    }
}
