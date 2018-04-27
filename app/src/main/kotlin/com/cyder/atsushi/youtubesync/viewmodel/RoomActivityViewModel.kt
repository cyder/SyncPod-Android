package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableBoolean
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository,
        private val wsApi: SyncPodWsApi,
        private val navigator: Navigator
) : ActivityViewModel() {
    var isVideoPlayerVisible = ObservableBoolean(false)

    lateinit var roomKey: String

    init {
        roomRepository.isReceiveForceExit()
                .filter { it }
                .subscribe {
                    roomRepository.exitRoom()
                    navigator.navigateToTopActivity(R.string.receive_force_exit)
                    navigator.closeActivity()
                }
    }

    override fun onStart() {
        if (!wsApi.isEntered.blockingFirst()) {
            navigator.navigateToTopActivity()
            navigator.closeActivity()
        }
        videoRepository.observeIsPlaying().subscribe {
            isVideoPlayerVisible.set(it)
        }
    }

    override fun onResume() {
        videoRepository.getNowPlayingVideo()
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