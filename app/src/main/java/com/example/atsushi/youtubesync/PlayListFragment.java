package com.example.atsushi.youtubesync;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListFragment extends Fragment {
    private ListView playList;
    private PlayListAdapter adapter;

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
        playList = (ListView) view.findViewById(R.id.play_list);
        playList.setAdapter(adapter);
        View listHeader = getActivity().getLayoutInflater().inflate(R.layout.play_list_header, null);
        playList.addHeaderView(listHeader, null, false);

        ((FloatingActionButton) view.findViewById(R.id.add_video_action_button))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        VideoActivity activity = (VideoActivity)getContext();
                        activity.startSearchVideoActivity();
                }
                });
    }

    public void startVideo(Video video) {
        if(adapter.getCount() > 0 && adapter.getItemId(0) == video.id) {
            adapter.deleteVideo(0);
        }
    }

    public void initPlayList(ArrayList<Video> videos) {
        adapter.setVideoList(videos);
    }

    public void addPlayList(Video video) {
        adapter.addVideo(video);
    }
}
