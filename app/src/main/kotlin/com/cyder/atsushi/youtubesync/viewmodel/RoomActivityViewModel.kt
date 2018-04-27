package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableBoolean
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository,
        private val wsApi: SyncPodWsApi,
        private val navigator: Navigator
) : ActivityViewModel() {
    var isVideoPlayerVisible = ObservableBoolean(false)
    private val onPause = PublishSubject.create<Boolean>()

    lateinit var roomKey: String

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
        roomRepository.isReceiveForceExit()
                .filter { it }
                .takeUntil(onPause.toFlowable(BackpressureStrategy.LATEST))
                .subscribe {
                    roomRepository.exitRoom()
                    navigator.navigateToTopActivity(R.string.receive_force_exit)
                    navigator.closeActivity()
                }
    }

    override fun onPause() {
        onPause.onNext(true)
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        roomRepository.exitRoom()
    }
}