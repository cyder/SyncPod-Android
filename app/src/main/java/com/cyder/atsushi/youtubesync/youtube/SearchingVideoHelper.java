package com.cyder.atsushi.youtubesync.youtube;

import android.content.Context;

import com.cyder.atsushi.youtubesync.json_data.SearchResponse;
import com.cyder.atsushi.youtubesync.json_data.Video;
import com.cyder.atsushi.youtubesync.server.HttpRequestsHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by atsushi on 2017/10/11.
 */

public class SearchingVideoHelper extends HttpRequestsHelper {
    private final static String TAG = SearchingVideoHelper.class.getSimpleName();
    private SearchInterface listener = null;
    private static final long MAX_RESULT = 10;
    private static final int SHOWING_VIDEOS = 5;
    private String nextPageToken = null;
    private String nowPageToken = null;
    private String keyword = null;

    public SearchingVideoHelper(Context context) {
        super(SearchResponse.class);
        this.listener = (SearchInterface) context;
    }

    public void get(final String keyword) {
        nextPageToken = null;
        this.keyword = keyword;
        new Thread(new Runnable() {
            @Override
            public void run() {
                search(keyword);
            }
        }).start();
    }

    public void next(final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (totalItemCount == 0 || (totalItemCount - visibleItemCount - SHOWING_VIDEOS) >= firstVisibleItem) {
            return;
        }
        if (keyword != null && nextPageToken != null && !nextPageToken.equals(nowPageToken)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    search(keyword, nextPageToken);
                    nowPageToken = nextPageToken;
                }
            }).start();
        }
    }

    private void search(final String keyword) {
        search(keyword, null);
    }

    private void search(final String keyword, final String pageToken) {
        HashMap<String, String> params = new HashMap<>();
        params.put("keyword", keyword);
        if (pageToken != null) {
            params.put("page_token", pageToken);
        }
        search(params);
    }

    private void search(final HashMap<String, String> params) {

        super.get(params, "youtube/search", new HttpRequestCallback() {
            @Override
            public void success(Object response) {
                SearchResponse r = (SearchResponse) response;
                if (params.size() == 1) {
                    listener.onReceived(new ArrayList<Video>(Arrays.asList(r.items)));
                } else if (params.size() == 2) {
                    listener.onLoaded((new ArrayList<Video>(Arrays.asList(r.items))));
                }
                nextPageToken = r.next_page_token;
            }

            @Override
            public void failure() {
            }
        });
    }
}
