package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import java.util.HashMap;

public class VideoActivity extends YouTubeFailureRecoveryActivity implements RoomChannelInterface {
    final int searchVideoRequestCode = 1000;

    RoomChannel roomChannel;
    YouTubePlayer player;
    TextView videoTitleText;
    LinearLayout playList;
    HashMap<String, View> playListMap = new HashMap<String, View>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        roomChannel = new RoomChannel();
        roomChannel.setListener(this);

        videoTitleText = (TextView) findViewById(R.id.video_title);
        playList = (LinearLayout) findViewById(R.id.play_list);

        ((FloatingActionButton) findViewById(R.id.add_video_action_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent varIntent = new Intent(VideoActivity.this, SearchVideoActivity.class);
                        startActivityForResult(varIntent, searchVideoRequestCode);
                    }
                });
    }

    @Override
    public void onRestart() {
        super.onRestart();
        roomChannel.getNowPlayingVideo();
        roomChannel.getPlayList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        roomChannel.removeListener();
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(resultCode == RESULT_OK && requestCode == searchVideoRequestCode && null != intent) {
            String res = intent.getStringExtra("youtube_video_id");
            if(res != null) {
                roomChannel.addVideo(res);
            }
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            this.player = player;
        }
        roomChannel.getNowPlayingVideo();
        roomChannel.getPlayList();
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
                if(playListMap.get(String.valueOf(video.id)) != null) {
                    playList.removeView(playListMap.get(String.valueOf(video.id)));
                }
            }
        });
    }

    private void initPlayList(final ArrayList<Video> videos) {
        runOnUiThread(new Runnable() {
            public void run() {
                playList.removeAllViews();
                for(Video video : videos) {
                    addPlayListItem(video);
                }
            }
        });
    }

    private void addPlayList(final Video video) {
        runOnUiThread(new Runnable() {
            public void run() {
                addPlayListItem(video);
            }
        });
    }

    private void addPlayListItem(final Video video) {
        View playListItem = getLayoutInflater().inflate(R.layout.play_list_item, null);
        playListMap.put(String.valueOf(video.id), playListItem);
        TextView title = (TextView) playListItem.findViewById(R.id.title);
        title.setText(video.title);
        playList.addView(playListItem);
    }
}
