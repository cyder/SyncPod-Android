package com.example.atsushi.youtubesync;

import android.content.Context;
import android.support.annotation.NonNull;
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
    @NonNull
    private ArrayList<Video> videoList = new ArrayList<>();
    private boolean emptyFlag = false;

    public PlayListAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
        emptyFlag = videoList.size() == 0;
        notifyDataSetChanged();
    }

    public void addVideo(Video video) {
        videoList.add(video);
        emptyFlag = false;
        notifyDataSetChanged();
    }

    public void startVideo(Video video) {
        if (getItemId(0) == video.id) {
            videoList.remove(0);
        }
        emptyFlag = videoList.size() == 0;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return emptyFlag ? 1 : videoList.size();
    }

    @Override
    public Video getItem(int position) {
        if (!emptyFlag && 0 <= position && position < getCount()) {
            return videoList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (getItem(position) != null) {
            return videoList.get(position).id;
        }
        return -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (emptyFlag) {
            return layoutInflater.inflate(R.layout.play_list_empty_view, parent, false);
        }

        final View convertView = layoutInflater.inflate(R.layout.video_list, parent, false);
        final Video video = videoList.get(position);
        if (video == null) {
            return null;
        }

        ((TextView) convertView.findViewById(R.id.title)).setText(video.title);
        ((TextView) convertView.findViewById(R.id.channel_title)).setText(video.channel_title);

        ImageView imageView = convertView.findViewById(R.id.thumbnail);
        if (video.thumbnail != null) {
            imageView.setImageBitmap(video.thumbnail);
        } else {
            new ThumbnailGetTask(video, imageView).execute();
        }

        return convertView;
    }
}
