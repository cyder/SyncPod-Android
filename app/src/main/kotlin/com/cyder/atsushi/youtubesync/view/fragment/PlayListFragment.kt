package com.cyder.atsushi.youtubesync.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.databinding.FragmentPlaylistBinding
import com.cyder.atsushi.youtubesync.viewmodel.PlayListFragmentViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/16.
 */

class PlayListFragment : BaseFragment() {

    private lateinit var binding: FragmentPlaylistBinding
    @Inject lateinit var viewModel: PlayListFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
        bindViewModel(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    companion object {
        fun createInstance(): PlayListFragment = PlayListFragment()
    }
}