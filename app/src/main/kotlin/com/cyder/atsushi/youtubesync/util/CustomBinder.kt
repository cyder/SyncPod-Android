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
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.opengl.ETC1.getHeight
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.view.fragment.ChatFragment
import com.cyder.atsushi.youtubesync.view.helper.ResizeAnimation


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
    val OFFSET = 5
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recycler: RecyclerView, newState: Int) {
            onScrollStateChanged?.onScrollStateChanged(recycler, newState)
        }

        override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
            val totalCount = recycler.adapter.itemCount
            val childCount = recycler.childCount
            val layoutManager = layoutManager as LinearLayoutManager
            val nowHeadPos = layoutManager.findFirstVisibleItemPosition()
            val lastPos = layoutManager.findLastCompletelyVisibleItemPosition()
            onScrolled?.onScrolled(totalCount <= childCount + nowHeadPos + OFFSET)
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

@BindingAdapter("animatedVisibility")
fun View.setAnimatedVisibility(visibility: Int) {
    val animation: ResizeAnimation
    if (visibility == View.VISIBLE) {
        this.visibility = View.VISIBLE
        animation = ResizeAnimation(this, 100, 0)
    } else {
        animation = ResizeAnimation(this, -this.height, this.height)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(arg0: Animation) {
                this@setAnimatedVisibility.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }
    animation.duration = 100
    animation.interpolator = AccelerateDecelerateInterpolator()
    this.startAnimation(animation)
}