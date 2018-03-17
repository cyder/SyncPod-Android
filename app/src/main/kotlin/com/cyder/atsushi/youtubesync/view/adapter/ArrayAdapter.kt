package com.cyder.atsushi.youtubesync.view.adapter

import android.support.v7.widget.RecyclerView

/**
 * Created by chigichan24 on 2018/03/17.
 */

abstract class ArrayAdapter<out T, VH : RecyclerView.ViewHolder>(private val list: List<T>) : RecyclerView.Adapter<VH>() {
    fun getItem(position: Int) : T = list[position]

    override fun getItemCount(): Int = list.size
}
