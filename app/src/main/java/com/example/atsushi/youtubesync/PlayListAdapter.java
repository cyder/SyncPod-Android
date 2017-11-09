package com.example.atsushi.youtubesync;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atsushi.youtubesync.app_data.ListInterface;
import com.example.atsushi.youtubesync.app_data.PlayList;
import com.example.atsushi.youtubesync.json_data.Video;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListAdapter extends BaseAdapter implements ListInterface {
    private Context context;
    private LayoutInflater layoutInflater = null;
    private PlayList playList;

    public PlayListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setPlayList(PlayList list) {
        this.playList = list;
        this.playList.addListener(this);
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void updated() {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        if (playList == null) {
            return 0;
        }
        if (playList.size() == 0) {
            return 1;
        }
        return playList.size();
    }

    @Override
    public Video getItem(int position) {
        if (playList != null && playList.size() != 0 && 0 <= position && position < getCount()) {
            return playList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (getItem(position) != null) {
            return getItem(position).id;
        }
        return -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (playList == null) {
            return layoutInflater.inflate(R.layout.play_list_empty_view, parent, false);
        }

        if (playList.size() == 0) {
            return layoutInflater.inflate(R.layout.play_list_empty_view, parent, false);
        }

        final View convertView = layoutInflater.inflate(R.layout.video_list, parent, false);
        final Video video = getItem(position);
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
