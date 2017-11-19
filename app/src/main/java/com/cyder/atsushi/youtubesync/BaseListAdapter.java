package com.cyder.atsushi.youtubesync;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.cyder.atsushi.youtubesync.app_data.BaseList;
import com.cyder.atsushi.youtubesync.app_data.ListInterface;

/**
 * Created by atsushi on 2017/11/12.
 */

abstract public class BaseListAdapter<Item, List extends BaseList<Item>> extends android.widget.BaseAdapter implements ListInterface {
    protected Context context;
    protected LayoutInflater layoutInflater = null;
    protected List list;

    public BaseListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setList(List list) {
        this.list = list;
        this.list.setContext(context);
        this.list.addListener(this);
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void updated() {
        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Item getItem(int position) {
        if (list != null && list.size() != 0 && 0 <= position && position <= getCount()) {
            return list.get(position);
        }
        return null;
    }
}
