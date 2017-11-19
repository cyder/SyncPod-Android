package com.cyder.atsushi.youtubesync;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.cyder.atsushi.youtubesync.app_data.MySelf;
import com.cyder.atsushi.youtubesync.app_data.RoomData;
import com.cyder.atsushi.youtubesync.channels.RoomChannel;
import com.cyder.atsushi.youtubesync.channels.RoomChannelInterface;
import com.cyder.atsushi.youtubesync.components.ViewPager;
import com.cyder.atsushi.youtubesync.json_data.Chat;
import com.cyder.atsushi.youtubesync.json_data.JsonData;
import com.cyder.atsushi.youtubesync.json_data.Video;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class RoomActivity extends AppCompatActivity implements RoomChannelInterface, VideoFragment.VideoFragmentListener{

    private final String TAG = this.getClass().getSimpleName();

    final int searchVideoRequestCode = 1000;
    private boolean connectFlag = false;
    private RoomFragmentPagerAdapter roomFragmentPagerAdapter;
    private VideoFragment videoFragment;
    RoomChannel roomChannel;
    @NonNull
    RoomData roomData = new RoomData();
    YouTubePlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent varIntent = getIntent();
        String roomKey = varIntent.getStringExtra("room_key");
        roomData.getRoomInfoByKey(roomKey);

        if (savedInstanceState != null) {
            MySelf.restoreInstanceState(savedInstanceState);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        roomFragmentPagerAdapter = new RoomFragmentPagerAdapter(fragmentManager, getResources(), savedInstanceState);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(roomFragmentPagerAdapter);

        final PlayListFragment playListFragment = (PlayListFragment) roomFragmentPagerAdapter.getItem(0);
        final ChatFragment chatFragment = (ChatFragment) roomFragmentPagerAdapter.getItem(1);
        final RoomInformationFragment roomInformationFragment = (RoomInformationFragment) roomFragmentPagerAdapter.getItem(2);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (roomFragmentPagerAdapter.getItem(position) instanceof RoomInformationFragment) {
                    roomInformationFragment.onPageSelected();
                }
            }
        });

        playListFragment.setRoomData(roomData);
        chatFragment.setRoomData(roomData);
        roomInformationFragment.setRoomData(roomData);
        VideoFragment fragment = (VideoFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        fragment.setRoomData(roomData);
        this.videoFragment = fragment;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        connectFlag = false;
        roomChannel = new RoomChannel(roomKey);
        roomChannel.setListener(this);
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
    public void onConnected() {
        Log.d(TAG, "connected");
        roomChannel.getNowPlayingVideo();
        roomChannel.getPlayList();
        roomChannel.getChatList();
        connectFlag = true;
    }

    @Override
    public void onRejected() {
        Intent intent = new Intent();
        intent.putExtra("error", "actionCableRejected");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onReceived(JsonElement data) {
        Gson gson = new Gson();
        JsonData jsonData = gson.fromJson(data.getAsString(), JsonData.class);

        switch (jsonData.data_type) {
            case "now_playing_video":
                if (jsonData.data != null) {
                    if (videoFragment != null) {
                        videoFragment.startVideo(jsonData.data.video);
                    }
                }
                break;
            case "add_video":
                addPlayList(jsonData.data.video);
                break;
            case "start_video":
                if (videoFragment != null) {
                    videoFragment.startVideo(jsonData.data.video);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        MySelf.saveInstanceState(savedInstanceState);
        roomFragmentPagerAdapter.saveInstanceState(savedInstanceState);
    }

    public void startSearchVideoActivity() {
        Intent varIntent = new Intent(RoomActivity.this, SearchVideoActivity.class);
        startActivityForResult(varIntent, searchVideoRequestCode);
    }

    public void sendChat(String message) {
        roomChannel.sendChat(message);
    }

    private void initPlayList(final ArrayList<Video> videos) {
        roomData.getPlayList().setList(videos);
    }

    private void addPlayList(final Video video) {
        if (roomData.getNowPlayingVideo() == null && videoFragment != null) {
            videoFragment.prepareVideo(video);
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

    @Override
    public void onGetNowPlayingVideo() {
        roomChannel.getNowPlayingVideo();
    }
}
