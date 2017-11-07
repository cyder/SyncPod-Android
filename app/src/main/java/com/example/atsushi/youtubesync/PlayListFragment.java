package com.example.atsushi.youtubesync;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListFragment extends Fragment {
    private ListView playList;
    private PlayListAdapter adapter;
    private Video nowPlayingVideo;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PlayListAdapter(getActivity());
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

    public void startVideo(Video video) {
        nowPlayingVideo = video;
        showNowPlayingVideo();
        adapter.startVideo(video);
    }

    public void initPlayList(ArrayList<Video> videos) {
        adapter.setVideoList(videos);
    }

    public void addPlayList(Video video) {
        adapter.addVideo(video);
    }

    private void showNowPlayingVideo() {
        if (nowPlayingVideo != null && view != null) {
            TextView title = view.findViewById(R.id.now_title);
            title.setText(nowPlayingVideo.title);
            TextView channelTitle = view.findViewById(R.id.now_channel_title);
            channelTitle.setText(nowPlayingVideo.channel_title);
            TextView published = view.findViewById(R.id.now_published);
            published.setText(String.format(getResources().getString(R.string.published), nowPlayingVideo.published));
            TextView views = view.findViewById(R.id.now_views);
            views.setText(String.format(getResources().getString(R.string.views), "1,000,000"));
        }
    }

}
