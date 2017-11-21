package com.cyder.atsushi.youtubesync.app_data;

import android.os.Looper;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by atsushi on 2017/11/09.
 */

public class BaseList<T> {
    @NonNull
    private ArrayList<ListInterface> listeners = new ArrayList<>();
    @NonNull
    protected ArrayList<T> list = new ArrayList<>();

    public void setList(@NonNull final ArrayList<T> l) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                list = l;
                updated();
            }
        });
    }

    public void add(final T item) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                list.add(item);
                updated();
            }
        });
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

    public void removeListener(ListInterface listener) {
        listeners.remove(listener);
    }

    protected void updated() {
        for (ListInterface listener : listeners) {
            listener.updated();
        }
    }
}
