package com.cyder.android.syncpod.view.fragment

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.FragmentChatBinding
import com.cyder.android.syncpod.databinding.ItemChatBinding
import com.cyder.android.syncpod.view.adapter.BindingHolder
import com.cyder.android.syncpod.view.adapter.ObservableListAdapter
import com.cyder.android.syncpod.viewmodel.ChatFragmentViewModel
import com.cyder.android.syncpod.viewmodel.ChatViewModel
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
        binding.chatList.addItemDecoration(
            DividerItemDecoration(binding.chatList.context, manager.orientation))
    }

    inner class ChatAdapter(list: ObservableList<ChatViewModel>) : ObservableListAdapter<ChatViewModel, BindingHolder<ItemChatBinding>>(list) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<ItemChatBinding> = BindingHolder(parent, R.layout.item_chat)

        override fun onBindViewHolder(holder: BindingHolder<ItemChatBinding>, position: Int) {
            val viewModel = getItem(position)
            val binding = holder.binding
            binding.viewModel = viewModel
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
                binding.chatList.smoothScrollToPosition(lastListPosition + 1)
            }
        }
    }

    companion object {
        fun createInstance() = ChatFragment()
    }
}