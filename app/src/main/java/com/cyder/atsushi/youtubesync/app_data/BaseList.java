package com.cyder.atsushi.youtubesync.app_data;

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

    public void setList(ArrayList<T> list) {
        this.list = list;
        updated();
    }

    public void add(T item) {
        list.add(item);
        updated();
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
        for(ListInterface listener : listeners) {
            listener.updated();
        }
    }
}
