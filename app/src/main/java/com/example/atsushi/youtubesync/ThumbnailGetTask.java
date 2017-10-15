package com.example.atsushi.youtubesync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.atsushi.youtubesync.json_data.Video;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by atsushi on 2017/10/16.
 */

public class ThumbnailGetTask extends AsyncTask<Void,Void,Bitmap> {
    private ImageView image;
    private Video video;

    public ThumbnailGetTask(Video _video, ImageView _image) {
        image = _image;
        video = _video;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL imageUrl = new URL(video.thumbnail_url);
            InputStream imageIs;
            imageIs = imageUrl.openStream();
            video.thumbnail = BitmapFactory.decodeStream(imageIs);
            return video.thumbnail;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        image.setImageBitmap(result);
    }
}
