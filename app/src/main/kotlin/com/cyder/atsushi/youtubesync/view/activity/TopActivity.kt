package com.cyder.atsushi.youtubesync.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.ActivityTopBinding
import com.cyder.atsushi.youtubesync.databinding.ItemRoomBinding
import com.cyder.atsushi.youtubesync.view.adapter.BindingHolder
import com.cyder.atsushi.youtubesync.view.adapter.ObservableListAdapter
import com.cyder.atsushi.youtubesync.viewmodel.RoomViewModel
import com.cyder.atsushi.youtubesync.viewmodel.TopActivityViewModel
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/01/29.
 */

class TopActivity : BaseActivity() {
    @Inject lateinit var viewModel: TopActivityViewModel
    private lateinit var binding: ActivityTopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent().inject(this)
        bindViewModel(viewModel)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_top)
        binding.viewModel = viewModel

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.joinedRoomRecycler.isNestedScrollingEnabled = false
        val adapter = JoinedRoomAdapter(viewModel.roomViewModels)
        binding.joinedRoomRecycler.adapter = adapter
        binding.joinedRoomRecycler.layoutManager = LinearLayoutManager(this)
    }

    private class JoinedRoomAdapter(list: ObservableList<RoomViewModel>) : ObservableListAdapter<RoomViewModel, BindingHolder<ItemRoomBinding>>(list) {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemRoomBinding> = BindingHolder(parent, R.layout.item_room)

        override fun onBindViewHolder(holder: BindingHolder<ItemRoomBinding>?, position: Int) {
            val viewModel = getItem(position)
            val binding = holder?.binding
            binding?.viewModel = viewModel
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, TopActivity::class.java)
    }
}