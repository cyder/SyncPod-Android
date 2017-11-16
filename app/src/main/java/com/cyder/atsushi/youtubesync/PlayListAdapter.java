package com.cyder.atsushi.youtubesync;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyder.atsushi.youtubesync.app_data.PlayList;
import com.cyder.atsushi.youtubesync.json_data.Video;

/**
 * Created by atsushi on 2017/10/16.
 */

public class PlayListAdapter extends BaseListAdapter<Video, PlayList> {
    public PlayListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        if (list.size() == 0) {
            return 1;
        }
        return list.size();
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
        if (list != null) {
            if (list.size() == 0) {
                return layoutInflater.inflate(R.layout.play_list_empty_view, parent, false);
            }

            final View convertView = layoutInflater.inflate(R.layout.video_list, parent, false);
            final Video video = getItem(position);
            if (video != null) {
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

            return null;
        }

        return layoutInflater.inflate(R.layout.play_list_empty_view, parent, false);
    }
}
