package com.cyder.atsushi.youtubesync.util

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import android.view.View

/**
 * Created by chigichan24 on 2018/01/26.
 */

@BindingAdapter("navigationOnClick")
fun Toolbar.onNavigationClick(clickListener: View.OnClickListener) = setNavigationOnClickListener(clickListener)
