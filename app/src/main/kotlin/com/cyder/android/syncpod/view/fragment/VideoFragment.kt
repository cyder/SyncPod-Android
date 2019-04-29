package com.cyder.android.syncpod.view.fragment

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.databinding.FragmentVideoBinding
import com.cyder.android.syncpod.viewmodel.VideoFragmentViewModel
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import javax.inject.Inject

/**
 * Created by chigichan24 on 2018/04/10.
 */

class VideoFragment : BaseFragment() {

    private lateinit var binding: FragmentVideoBinding
    @Inject lateinit var viewModel: VideoFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
        bindViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false)
        binding.viewModel = viewModel
        initPlayer()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun initPlayer() {
        val fragment = YouTubePlayerSupportFragment.newInstance()
        fragmentManager
                ?.beginTransaction()
                ?.add(R.id.view_player, fragment)
                ?.commit()
        viewModel.youtubeFragment = fragment
    }

    companion object {
        fun createInstance(): VideoFragment = VideoFragment()
    }
}