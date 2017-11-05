package com.example.atsushi.youtubesync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater = null;
    private ArrayList<Video> videoList;

    public PlayListAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public void addVideo(Video video) {
        videoList.add(video);
        notifyDataSetChanged();
    }

    public void deleteVideo(int position) {
        videoList.remove(position);
        notifyDataSetChanged();
    }

    public void startVideo(Video video) {
        if(getItemId(0) == video.id){
            deleteVideo(0);
        }
    }

    @Override
    public int getCount() {
        if (videoList != null) {
            return videoList.size();
        }
        return 0;
    }

    @Override
    public Video getItem(int position) {
        if (videoList != null && 0 <= position && position < videoList.size()) {
            return videoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (videoList != null && 0 <= position && position < videoList.size()) {
            return videoList.get(position).id;
        }
        return -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = layoutInflater.inflate(R.layout.play_list_item, parent, false);
        final Video video = videoList.get(position);
        if (video == null) {
            return null;
        }

        ((TextView) convertView.findViewById(R.id.title)).setText(video.title);
        ((TextView) convertView.findViewById(R.id.channel_title)).setText(video.channel_title);

        ImageView imageView = ((ImageView) convertView.findViewById(R.id.thumbnail));
        if (video.thumbnail != null) {
            imageView.setImageBitmap(video.thumbnail);
        } else {
            new ThumbnailGetTask(video, imageView).execute();
        }

        return convertView;
    }
}
