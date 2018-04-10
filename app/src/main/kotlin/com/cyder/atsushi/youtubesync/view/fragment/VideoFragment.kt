package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentVideoBinding

/**
 * Created by chigichan24 on 2018/04/10.
 */

class VideoFragment : BaseFragment() {

    private lateinit var binding: FragmentVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video ,container, false)
        return binding.root
    }

    companion object {
        fun createInstance(): VideoFragment = VideoFragment()
    }
}