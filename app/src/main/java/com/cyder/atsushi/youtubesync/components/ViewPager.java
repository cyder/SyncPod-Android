package com.cyder.atsushi.youtubesync.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by atsushi on 2017/11/13.
 */

public class ViewPager extends android.support.v4.view.ViewPager {
    public ViewPager(Context context) {
        super(context);
    }

    public ViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return false;
    }
}
