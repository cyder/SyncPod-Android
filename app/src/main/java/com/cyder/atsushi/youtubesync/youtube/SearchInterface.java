package com.cyder.atsushi.youtubesync.youtube;

import com.cyder.atsushi.youtubesync.json_data.Video;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/10/11.
 */

public interface SearchInterface {
    void onReceived(ArrayList<Video> result);
    void onLoaded(ArrayList<Video> result);
}
