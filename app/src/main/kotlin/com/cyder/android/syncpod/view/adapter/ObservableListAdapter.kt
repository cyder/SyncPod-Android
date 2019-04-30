package com.cyder.android.syncpod.view.adapter

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by chigichan24 on 2018/03/17.
 */

abstract class ObservableListAdapter<T, VH : RecyclerView.ViewHolder>(
        list: ObservableList<T>
) : ArrayAdapter<T, VH>(list) {
    init {
        list.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(p0: ObservableList<T>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(p0: ObservableList<T>?, p1: Int, p2: Int) {
                notifyItemRangeRemoved(p1, p2)
            }

            override fun onItemRangeMoved(p0: ObservableList<T>?, p1: Int, p2: Int, p3: Int) {
                notifyItemMoved(p1, p2)
            }

            override fun onItemRangeInserted(p0: ObservableList<T>?, p1: Int, p2: Int) {
                notifyItemRangeInserted(p1, p2)
            }

            override fun onItemRangeChanged(p0: ObservableList<T>?, p1: Int, p2: Int) {
                notifyItemRangeChanged(p1, p2)
            }

        })
    }
}

