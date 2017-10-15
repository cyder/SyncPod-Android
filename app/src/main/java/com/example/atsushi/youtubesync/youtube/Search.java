package com.example.atsushi.youtubesync.youtube;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.atsushi.youtubesync.DeveloperKey;
import com.example.atsushi.youtubesync.json_data.Video;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by atsushi on 2017/10/11.
 */

public class Search {
    private SearchInterface listener = null;
    private static YouTube youtube;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final long maxResult = 10;
    private YouTube.Search.List search;
    private String nextPageToken = null;
    private String nowPageToken = null;

    public Search() {
        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-sync").build();
            search = youtube.search().list("id,snippet");
            search.setKey(DeveloperKey.DEVELOPER_KEY);
            search.setMaxResults(maxResult);
            search.setType("video");
        } catch (IOException e) {
            Log.e("App", "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
    }

    public void get(final String keyword) {
        nextPageToken = null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                search.setQ(keyword);
                search.setPageToken("");
                ArrayList<Video> resultList = getResult();
                listener.onReceived(resultList);
            }
        }).start();
    }

    public void next() {
        if(nextPageToken != null && !nextPageToken.equals(nowPageToken)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    search.setPageToken(nextPageToken);
                    nowPageToken = nextPageToken;
                    ArrayList<Video> resultList = getResult();
                    listener.onLoaded(resultList);
                }
            }).start();
        }
    }

    public void setListener(SearchInterface listener){
        this.listener = listener;
    }

    private ArrayList<Video> getResult() {
        ArrayList<Video> resultList = new ArrayList<Video>();
        try {
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            Iterator<SearchResult> iterator = searchResultList.iterator();
            while (iterator.hasNext()) {
                Video video = convert(iterator.next());
                resultList.add(video);
            }
            nextPageToken = searchResponse.getNextPageToken();
        } catch (IOException e) {
            Log.e("App", "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        return resultList;
    }

    private Video convert(SearchResult result) {
        Video video = new Video();
        video.youtube_video_id = result.getId().getVideoId();
        video.title = result.getSnippet().getTitle();
        video.channel_title = result.getSnippet().getChannelTitle();
        video.thumbnail_url = result.getSnippet().getThumbnails().getMedium().getUrl();

        return video;
    }
}
