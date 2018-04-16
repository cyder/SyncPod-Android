package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentChatBinding
import com.cyder.atsushi.youtubesync.viewmodel.ChatFragmentViewModel
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
        return binding.root
    }

    companion object {
        fun createInstance() = ChatFragment()
    }
}