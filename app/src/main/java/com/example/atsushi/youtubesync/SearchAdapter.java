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
 * Created by atsushi on 2017/10/12.
 */

public class SearchAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Video> videoList;

    public SearchAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setVideoList(ArrayList<Video> videoList) {
        this.videoList = videoList;
    }

    public void addVideoList(ArrayList<Video> videolist) {
        this.videoList.addAll(videolist);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Video getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return videoList.get(position).id;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = layoutInflater.inflate(R.layout.search_result_video_list,parent,false);
        ((TextView)convertView.findViewById(R.id.title)).setText(videoList.get(position).title);
        ((TextView)convertView.findViewById(R.id.channel_title)).setText(videoList.get(position).channel_title);
        ((ImageView)convertView.findViewById(R.id.thumbnail)).setImageBitmap(videoList.get(position).thumbnail);

        return convertView;
    }
}
