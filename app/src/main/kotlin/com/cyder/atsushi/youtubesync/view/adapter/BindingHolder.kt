package com.cyder.atsushi.youtubesync.view.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by chigichan24 on 2018/03/17.
 */

class BindingHolder<out T : ViewDataBinding>(parent: ViewGroup?, layoutResId: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent!!.context).inflate(layoutResId, parent, false)) {
    val binding: T = DataBindingUtil.bind(itemView)
}