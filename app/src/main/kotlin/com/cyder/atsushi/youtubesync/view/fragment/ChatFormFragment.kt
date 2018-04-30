package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentChatFormBinding
import com.cyder.atsushi.youtubesync.viewmodel.ChatFormFragmentViewModel
import javax.inject.Inject

class ChatFormFragment : BaseFragment() {
    private lateinit var binding: FragmentChatFormBinding
    @Inject lateinit var viewModel: ChatFormFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
        bindViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_form, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    companion object {
        fun createInstance(): ChatFormFragment = ChatFormFragment()
    }
}