package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atsushi.youtubesync.app_data.RoomData;
import com.example.atsushi.youtubesync.app_data.RoomDataInterface;
import com.example.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListFragment extends Fragment implements RoomDataInterface {
    private ListView playList;
    private PlayListAdapter adapter;
    private View view;
    private RoomData roomData;

    public void setRoomData(RoomData roomData) {
        this.roomData = roomData;
        roomData.addListener(this);
        if (adapter != null) {
            adapter.setPlayList(roomData.getPlayList());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PlayListAdapter(getActivity());
        if (roomData != null) {
            adapter.setPlayList(roomData.getPlayList());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.play_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.view = view;

        playList = view.findViewById(R.id.play_list);
        playList.setAdapter(adapter);
        View listHeader = getActivity().getLayoutInflater().inflate(R.layout.play_list_header, null);
        playList.addHeaderView(listHeader, null, false);
        showNowPlayingVideo();

        view.findViewById(R.id.add_video_action_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoomActivity activity = (RoomActivity) getContext();
                        activity.startSearchVideoActivity();
                    }
                });
    }

    @Override
    public void updated() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    showNowPlayingVideo();
                }
            });
        }
    }

    private void showNowPlayingVideo() {
        if (view != null) {
            Video nowPlayingVideo = roomData.getNowPlayingVideo();
            if (nowPlayingVideo != null) {
                view.findViewById(R.id.now_play_video_area).setVisibility(View.VISIBLE);
                TextView title = view.findViewById(R.id.now_title);
                title.setText(nowPlayingVideo.title);
                TextView channelTitle = view.findViewById(R.id.now_channel_title);
                channelTitle.setText(nowPlayingVideo.channel_title);
            } else {
                view.findViewById(R.id.now_play_video_area).setVisibility(View.GONE);
            }
        }
    }

}
