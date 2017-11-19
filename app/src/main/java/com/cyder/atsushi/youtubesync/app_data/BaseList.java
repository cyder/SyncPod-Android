package com.cyder.atsushi.youtubesync.app_data;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/11/09.
 */

public class BaseList<T> {
    @NonNull
    ArrayList<ListInterface> listeners = new ArrayList<>();
    @NonNull
    protected ArrayList<T> list = new ArrayList<>();
    private Context context;

    public void setList(@NonNull final ArrayList<T> l) {
        if (context != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    list = l;
                    updated();
                }
            });
        }
    }

    public void add(final T item) {
        if (context != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    list.add(item);
                    updated();
                }
            });
        }
    }

    public T getTopItem() {
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    public int size() {
        return list.size();
    }

    public T get(int position) {
        return list.get(position);
    }


    public void addListener(ListInterface listener) {
        listeners.add(listener);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void removeListener(ListInterface listener) {
        listeners.remove(listener);
    }

    protected void updated() {
        for (ListInterface listener : listeners) {
            listener.updated();
        }
    }
}
