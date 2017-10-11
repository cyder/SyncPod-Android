package com.example.atsushi.youtubesync.youtube;

import android.util.Log;

import com.example.atsushi.youtubesync.DeveloperKey;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

/**
 * Created by atsushi on 2017/10/11.
 */

public class Search {
    private static YouTube youtube;
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private YouTube.Search.List search;

    public Search() {
        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-sync").build();
            search = youtube.search().list("id,snippet");
            search.setKey(DeveloperKey.DEVELOPER_KEY);
        } catch (IOException e) {
            Log.e("App", "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
    }

    public void get(final String keyword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    search.setQ(keyword);
                    SearchListResponse searchResponse = search.execute();
                    Log.d("App", searchResponse.toPrettyString());
                } catch (IOException e) {
                    Log.e("App", "There was an IO error: " + e.getCause() + " : " + e.getMessage());
                }
            }
        }).start();
    }
}
