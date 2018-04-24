package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/17.
 */

class PlayListFragmentViewModel @Inject constructor(
        private val navigator: Navigator
) : FragmentViewModel() {
    var videoViewModels: ObservableList<VideoViewModel> = ObservableArrayList()

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    fun searchVideo() = navigator.navigateToSearchVideoActivity()

}