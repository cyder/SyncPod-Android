package com.cyder.atsushi.youtubesync.util

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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

@BindingAdapter("linksNavigable")
fun TextView.setLinksNavigable(flag: Boolean) {
    movementMethod = if (flag) LinkMovementMethod.getInstance() else null
}

@BindingAdapter("onEnterClick")
fun EditText.setKeyboardClick(listener: TextView.OnEditorActionListener) = setOnEditorActionListener(listener)

@BindingAdapter(value = ["onScrolled", "onScrollStateChanged"], requireAll = false)
fun RecyclerView.setOnScrollChangedListeners(
        onScrolled: OnScrolledListener?,
        onScrollStateChanged: OnScrollStateChangedListener?
) {
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recycler: RecyclerView, newState: Int) {
            onScrollStateChanged?.onScrollStateChanged(recycler, newState)
        }

        override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
            val totalCount = recycler.adapter.itemCount
            val childCount = recycler.childCount
            val layoutManager = layoutManager as LinearLayoutManager
            onScrolled?.onScrolled(totalCount == childCount + layoutManager.findFirstVisibleItemPosition())
        }
    }

    addOnScrollListener(listener)
}

interface OnScrolledListener {
    fun onScrolled(isBottom: Boolean)
}

interface OnScrollStateChangedListener {
    fun onScrollStateChanged(recycler: RecyclerView, newState: Int)
}