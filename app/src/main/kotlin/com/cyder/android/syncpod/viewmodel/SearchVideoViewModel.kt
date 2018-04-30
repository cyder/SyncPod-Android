package com.cyder.android.syncpod.viewmodel

import android.databinding.ObservableField
import com.cyder.android.syncpod.model.Video
import com.cyder.android.syncpod.repository.VideoRepository
import com.cyder.android.syncpod.view.helper.Navigator
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/23.
 */

class SearchVideoViewModel @Inject constructor(
        val navigator: Navigator,
        val repository: VideoRepository,
        val video: ObservableField<Video>
) {
    fun onItemClick() {
        repository.addPlayList(video.get())
                .subscribe {
                    navigator.closeActivity()
                }
    }
}