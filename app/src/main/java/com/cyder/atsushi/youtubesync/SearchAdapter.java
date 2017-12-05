package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/12.
 */

public class SearchAdapter extends BaseAdapter {
    LayoutInflater layoutInflater = null;
    ArrayList<Video> videoList;
    private Context context = null;

    public SearchAdapter(Context context) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
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
        if (videoList != null) {
            return videoList.size();
        }
        return 0;
    }

    @Override
    public Video getItem(int position) {
        if (videoList != null && 0 <= position && position < getCount()) {
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
        final View convertView = layoutInflater.inflate(R.layout.video_list, parent, false);
        final Video video = videoList.get(position);
        if (video == null) {
            return null;
        }
        ((TextView) convertView.findViewById(R.id.title)).setText(video.title);
        ((TextView) convertView.findViewById(R.id.channel_title)).setText(video.channel_title);
        ((TextView) convertView.findViewById(R.id.published)).setText(String.format(context.getString(R.string.published), video.published));
        ((TextView) convertView.findViewById(R.id.view_count)).setText(String.format(context.getString(R.string.view_count), insertComma(video.view_count)));

        ImageView imageView = convertView.findViewById(R.id.thumbnail);
        ((TextView) convertView.findViewById(R.id.video_duration)).setText(" " + video.duration + " ");
        if (video.thumbnail != null) {
            imageView.setImageBitmap(video.thumbnail);
        } else {
            new ThumbnailGetTask(video, imageView).execute();
        }

        return convertView;
    }

    @NonNull
    private String insertComma(String bignum) {
        StringBuffer sb = new StringBuffer(bignum);
        String reversed = sb.reverse().toString();
        String res = "";
        for (int i = 0; i < reversed.length(); ++i) {
            res += reversed.charAt(i);
            if (i % 3 == 2 && i != reversed.length() - 1) {
                res += ",";
            }
        }
        sb = new StringBuffer(res);
        return sb.reverse().toString();
    }
}
