package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.databinding.ObservableList
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentChatBinding
import com.cyder.atsushi.youtubesync.databinding.ItemChatBinding
import com.cyder.atsushi.youtubesync.view.adapter.BindingHolder
import com.cyder.atsushi.youtubesync.view.adapter.ObservableListAdapter
import com.cyder.atsushi.youtubesync.viewmodel.ChatFragmentViewModel
import com.cyder.atsushi.youtubesync.viewmodel.ChatViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/16.
 */

class ChatFragment : BaseFragment() {
    private lateinit var binding: FragmentChatBinding
    @Inject lateinit var viewModel: ChatFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
        bindViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.viewModel = viewModel
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(activity)
        manager.stackFromEnd = true
        val adapter = ChatAdapter(viewModel.chatViewModels)
        val dataObserver = AutoScrollDataObserver(manager, adapter)
        adapter.registerAdapterDataObserver(dataObserver)
        binding.chatList.adapter = adapter
        binding.chatList.layoutManager = manager
        binding.chatList.addItemDecoration(DividerItemDecoration(binding.chatList.context, manager.orientation))
    }

    inner class ChatAdapter(list: ObservableList<ChatViewModel>) : ObservableListAdapter<ChatViewModel, BindingHolder<ItemChatBinding>>(list) {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BindingHolder<ItemChatBinding> = BindingHolder(parent, R.layout.item_chat)

        override fun onBindViewHolder(holder: BindingHolder<ItemChatBinding>?, position: Int) {
            val viewModel = getItem(position)
            val binding = holder?.binding
            binding?.viewModel = viewModel
        }
    }

    inner class AutoScrollDataObserver(
            private val manager: LinearLayoutManager,
            private val adapter: ChatAdapter
    ) : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            val lastVisiblePosition = manager.findLastVisibleItemPosition()
            val lastListPosition = adapter.itemCount - 1
            if (lastListPosition - 1 == lastVisiblePosition) {
                binding.chatList.smoothScrollToPosition(lastListPosition)
            }
        }
    }

    companion object {
        fun createInstance() = ChatFragment()
    }
}