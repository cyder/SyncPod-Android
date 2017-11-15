package com.cyder.atsushi.youtubesync.app_data;

import com.cyder.atsushi.youtubesync.json_data.Video;

/**
 * Created by atsushi on 2017/11/09.
 */

public class PlayList extends BaseList<Video> {
    public void removeVideo(Video target) {
        for (Video video: list) {
            if(video.id == target.id) {
                list.remove(list.indexOf(video));
                updated();
                break;
            }
        }
    }
}
