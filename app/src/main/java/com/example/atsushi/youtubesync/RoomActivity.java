package com.example.atsushi.youtubesync;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.atsushi.youtubesync.app_data.RoomData;
import com.example.atsushi.youtubesync.channels.RoomChannel;
import com.example.atsushi.youtubesync.channels.RoomChannelInterface;
import com.example.atsushi.youtubesync.json_data.*;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomActivity extends AppCompatActivity
        implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, RoomChannelInterface {

    private final String TAG = this.getClass().getSimpleName();

    final int searchVideoRequestCode = 1000;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private boolean connectFlag = false;

    RoomChannel roomChannel;
    @NonNull
    RoomData roomData = new RoomData();
    YouTubePlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        YouTubePlayerFragment frag =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            frag.initialize(info.metaData.getString("developer_key"), this);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, Arrays.toString(e.getStackTrace()));
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        RoomFragmentPagerAdapter adapter = new RoomFragmentPagerAdapter(fragmentManager, getResources());
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        PlayListFragment playListFragment = (PlayListFragment) adapter.getItem(0);
        ChatFragment chatFragment = (ChatFragment) adapter.getItem(1);
        RoomInformationFragment roomInformationFragment = (RoomInformationFragment) adapter.getItem(2);
        playListFragment.setRoomData(roomData);
        chatFragment.setRoomData(roomData);
        roomInformationFragment.setRoomData(roomData);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        connectFlag = false;
        Intent varIntent = getIntent();
        String roomKey = varIntent.getStringExtra("room_key");
        roomChannel = new RoomChannel(roomKey);
        roomChannel.setListener(this);

        roomData.getRoomInfoByKey(roomKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (connectFlag) {
            roomChannel.getNowPlayingVideo();
            roomChannel.getPlayList();
            roomChannel.getChatList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        roomChannel.removeListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == searchVideoRequestCode && null != intent) {
            String res = intent.getStringExtra("youtube_video_id");
            if (res != null) {
                roomChannel.addVideo(res);
            }
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
            player.setPlayerStateChangeListener(this);
            this.player = player;
        }
        roomChannel.getNowPlayingVideo();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected() {
        Log.d(TAG, "connected");
        roomChannel.getNowPlayingVideo();
        roomChannel.getPlayList();
        roomChannel.getChatList();
        connectFlag = true;
    }

    @Override
    public void onReceived(JsonElement data) {
        Gson gson = new Gson();
        JsonData jsonData = gson.fromJson(data.getAsString(), JsonData.class);

        switch (jsonData.data_type) {
            case "now_playing_video":
                if (jsonData.data != null) {
                    startVideo(jsonData.data.video);
                }
                break;
            case "add_video":
                addPlayList(jsonData.data.video);
                break;
            case "start_video":
                startVideo(jsonData.data.video);
                break;
            case "play_list":
                initPlayList(jsonData.data.play_list);
                break;
            case "add_chat":
                addChat(jsonData.data.chat);
                break;
            case "past_chats":
                initChatList(jsonData.data.past_chats);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "disconnected");
        connectFlag = false;
    }

    @Override
    public void onFailed() {
        Log.d(TAG, "failed");
    }


    @Override
    public void onAdStarted() {
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason reason) {
    }

    @Override
    public void onLoaded(String videoId) {
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onVideoEnded() {
        Video nextVideo = roomData.getPlayList().getTopItem();
        if (nextVideo != null) {
            prepareVideo(nextVideo);
        } else {
            roomData.clearNowPlayingVideo();
            findViewById(R.id.video_player).setVisibility(View.GONE);
        }
    }

    @Override
    public void onVideoStarted() {
    }

    public void startSearchVideoActivity() {
        Intent varIntent = new Intent(RoomActivity.this, SearchVideoActivity.class);
        startActivityForResult(varIntent, searchVideoRequestCode);
    }

    public void sendChat(String message) {
        roomChannel.sendChat(message);
    }

    private void startVideo(final Video video) {
        if (player != null) {
            player.loadVideo(video.youtube_video_id, video.current_time * 1000);
            setNowPlayingVideo(video);
        }
    }

    private void prepareVideo(final Video video) {
        if (player != null) {
            player.cueVideo(video.youtube_video_id);
            setNowPlayingVideo(video);
        }
    }

    private void setNowPlayingVideo(final Video video) {
        roomData.setNowPlayingVideo(video);
        if (player != null) {
            roomData.setNowPlayingVideo(video);
            runOnUiThread(new Runnable() {
                public void run() {
                    findViewById(R.id.video_player).setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private void initPlayList(final ArrayList<Video> videos) {
        roomData.getPlayList().setList(videos);
    }

    private void addPlayList(final Video video) {
        if (roomData.getNowPlayingVideo() == null) {
            prepareVideo(video);
        } else {
            roomData.getPlayList().add(video);
        }
    }

    private void initChatList(final ArrayList<Chat> chats) {
        roomData.getChatList().setList(chats);
    }

    private void addChat(final Chat chat) {
        roomData.getChatList().add(chat);
    }
}
