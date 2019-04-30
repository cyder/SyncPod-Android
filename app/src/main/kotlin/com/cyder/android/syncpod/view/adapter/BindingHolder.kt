package com.cyder.android.syncpod.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chigichan24 on 2018/03/17.
 */

class BindingHolder<out T : ViewDataBinding>(parent: ViewGroup?, layoutResId: Int) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent!!.context).inflate(layoutResId, parent, false)) {
    val binding: T = DataBindingUtil.bind(itemView)!!
}