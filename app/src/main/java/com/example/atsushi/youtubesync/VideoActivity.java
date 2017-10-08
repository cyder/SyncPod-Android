package com.example.atsushi.youtubesync;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.atsushi.youtubesync.channels.RoomChannel;
import com.example.atsushi.youtubesync.channels.RoomChannelInterface;
import com.example.atsushi.youtubesync.json_data.*;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class VideoActivity extends YouTubeFailureRecoveryActivity implements RoomChannelInterface {
    RoomChannel roomChannel;
    YouTubePlayer player;
    TextView videoTitleText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        videoTitleText = (TextView) findViewById(R.id.video_title);
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
                    startVideo(jsonData.data.video);
                }
                break;
            case "add_video":
                addPlayList(jsonData.data.video);
                break;
            case "start_video":
                if(jsonData.data != null) {
                    startVideo(jsonData.data.video);
                }
                break;
            case "play_list":
                initPlayList(jsonData.data.play_list);
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

    private void startVideo(final Video video) {
        player.loadVideo(video.youtube_video_id, video.current_time * 1000);
        runOnUiThread(new Runnable() {
            public void run() {
                videoTitleText.setText(video.title);
            }
        });
    }

    private void initPlayList(final ArrayList<Video> videos) {
        runOnUiThread(new Runnable() {
            public void run() {
                LinearLayout layout = (LinearLayout) findViewById(R.id.play_list);
                layout.removeAllViews();
                for(Video video : videos) {
                    View playListItem = getLayoutInflater().inflate(R.layout.play_list_item, null);
                    TextView title = (TextView) playListItem.findViewById(R.id.title);
                    title.setText(video.title);
                    layout.addView(playListItem);
                }
            }
        });
    }

    private void addPlayList(final Video video) {
        runOnUiThread(new Runnable() {
            public void run() {
                LinearLayout layout = (LinearLayout) findViewById(R.id.play_list);
                View playListItem = getLayoutInflater().inflate(R.layout.play_list_item, null);
                TextView title = (TextView) playListItem.findViewById(R.id.title);
                title.setText(video.title);
                layout.addView(playListItem);
            }
        });
    }
}
