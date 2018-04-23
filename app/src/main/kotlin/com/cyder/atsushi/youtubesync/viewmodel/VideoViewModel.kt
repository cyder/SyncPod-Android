package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableField
import android.util.Log
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class VideoViewModel @Inject constructor(
        val navigator: Navigator,
        val video: ObservableField<Video>
) {
    fun onItemClick() {
        Log.d("TAG", video.toString())
    }
}