package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableBoolean
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository,
        private val navigator: Navigator
) : ActivityViewModel() {
    var isVideoPlayerVisible = ObservableBoolean(false)

    lateinit var roomKey: String

    override fun onStart() {
        videoRepository.obserbleIsPlaying().subscribe {
            isVideoPlayerVisible.set(it)
        }
    }

    override fun onResume() {
        videoRepository.getNoewPlayingVideo()
        videoRepository.getPlayList()
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        roomRepository.exitRoom()
    }
}