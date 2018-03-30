package com.cyder.atsushi.youtubesync.util

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by chigichan24 on 2018/01/26.
 */

@BindingAdapter("navigationOnClick")
fun Toolbar.onNavigationClick(clickListener: View.OnClickListener) = setNavigationOnClickListener(clickListener)

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)
}
