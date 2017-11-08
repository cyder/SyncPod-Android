package com.example.atsushi.youtubesync;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.example.atsushi.youtubesync.channels.RoomChannel;
import com.example.atsushi.youtubesync.channels.RoomChannelInterface;
import com.example.atsushi.youtubesync.json_data.Chat;
import com.example.atsushi.youtubesync.json_data.JsonData;
import com.example.atsushi.youtubesync.json_data.Video;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, RoomChannelInterface {

    private final String TAG = this.getClass().getSimpleName();

    final int searchVideoRequestCode = 1000;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private boolean connectFlag = false;

    RoomChannel roomChannel;
    YouTubePlayer player;
    PlayListFragment playListFragment;
    ChatFragment chatFragment;
    RoomInformationFragment roomInformationFragment;

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
        playListFragment = (PlayListFragment) adapter.getItem(0);
        chatFragment = (ChatFragment) adapter.getItem(1);
        roomInformationFragment = (RoomInformationFragment) adapter.getItem(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        connectFlag = false;
        Intent varIntent = getIntent();
        String roomKey = varIntent.getStringExtra("room_key");
        roomChannel = new RoomChannel(roomKey);
        roomChannel.setListener(this);

        roomInformationFragment.setRoom(roomKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(connectFlag) {
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
                if (jsonData.data != null) {
                    startVideo(jsonData.data.video);
                }
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

    public void startSearchVideoActivity() {
        Intent varIntent = new Intent(RoomActivity.this, SearchVideoActivity.class);
        startActivityForResult(varIntent, searchVideoRequestCode);
    }

    public void sendChat(String message) {
        roomChannel.sendChat(message);
    }

    private void startVideo(final Video video) {
        if(player != null) {
            player.loadVideo(video.youtube_video_id, video.current_time * 1000);
        }
        runOnUiThread(new Runnable() {
            public void run() {
                playListFragment.startVideo(video);
            }
        });
    }

    private void initPlayList(final ArrayList<Video> videos) {
        runOnUiThread(new Runnable() {
            public void run() {
                playListFragment.initPlayList(videos);
            }
        });
    }

    private void addPlayList(final Video video) {
        runOnUiThread(new Runnable() {
            public void run() {
                playListFragment.addPlayList(video);
            }
        });
    }

    private void initChatList(final ArrayList<Chat> chats) {
        runOnUiThread(new Runnable() {
            public void run() {
                chatFragment.initChatList(chats);
            }
        });
    }

    private void addChat(final Chat chat) {
        runOnUiThread(new Runnable() {
            public void run() {
                chatFragment.addChat(chat);
            }
        });
    }
}
