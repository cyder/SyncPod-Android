package com.example.atsushi.youtubesync.channels;

import android.util.Log;

import com.hosopy.actioncable.Consumer;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by atsushi on 2017/10/08.
 */

public final class Cable {
    static Consumer consumer = null;

    private static void Init() {
        try {
            URI uri = new URI("ws://59.106.220.89:3000/cable/");
            consumer = com.hosopy.actioncable.ActionCable.createConsumer(uri);
            consumer.connect();
        } catch (URISyntaxException e) {
            Log.e("App", "URISyntaxException");
        }
    }

    public static Consumer getCunsumer() {
        if(consumer == null)
            Init();
        return consumer;
    }
}
