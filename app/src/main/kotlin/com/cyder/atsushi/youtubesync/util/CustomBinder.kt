package com.cyder.atsushi.youtubesync.util

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import android.view.View

/**
 * Created by chigichan24 on 2018/01/26.
 */

object CustomBinder {
    @JvmStatic
    @BindingAdapter("navigationOnClick")
    fun onNavigationClick(toolbar: Toolbar, clickListener: View.OnClickListener) = toolbar.setNavigationOnClickListener(clickListener)
}