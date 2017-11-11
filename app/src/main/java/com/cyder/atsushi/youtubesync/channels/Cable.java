package com.cyder.atsushi.youtubesync.channels;

import android.util.Log;

import com.cyder.atsushi.youtubesync.app_data.MySelf;
import com.hosopy.actioncable.ActionCable;
import com.hosopy.actioncable.Consumer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by atsushi on 2017/10/08.
 */

public final class Cable {
    static Consumer consumer = null;

    private static void Init() {
        try {
            URI uri = new URI("ws://59.106.220.89:3000/cable/");
            Consumer.Options options = new Consumer.Options();
            Map<String, String> query = new HashMap();
            query.put("token", MySelf.getToken());
            options.query = query;
            consumer = ActionCable.createConsumer(uri, options);
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
