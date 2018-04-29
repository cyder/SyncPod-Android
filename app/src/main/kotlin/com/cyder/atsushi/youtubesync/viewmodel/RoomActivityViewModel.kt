package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableBoolean
import android.support.v4.app.Fragment
import com.cyder.atsushi.youtubesync.repository.ChatRepository
import com.cyder.atsushi.youtubesync.R
import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.fragment.ChatFragment
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.ActivityViewModel
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository,
        private val chatRepository: ChatRepository,
        private val wsApi: SyncPodWsApi,
        private val navigator: Navigator
) : ActivityViewModel() {
    var isVideoPlayerVisible = ObservableBoolean(false)
    var isChatFragment = ObservableBoolean(false)
    private val onPauseSubject = PublishSubject.create<Unit>()

    lateinit var roomKey: String

    override fun onStart() {
        if (!wsApi.isEntered.blockingFirst()) {
            navigator.navigateToTopActivity()
            navigator.closeActivity()
        }
    }

    override fun onResume() {
        videoRepository.getNowPlayingVideo()
        videoRepository.getPlayList()
        roomRepository.receiveForceExit()
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .subscribe {
                    roomRepository.exitRoom()
                    navigator.navigateToTopActivity(R.string.receive_force_exit)
                    navigator.closeActivity()
                }
        videoRepository.observeIsPlaying()
                .takeUntil(onPauseSubject.toFlowable(BackpressureStrategy.LATEST))
                .subscribe {
                    isVideoPlayerVisible.set(it)
                }
        chatRepository.getPastChats()
    }

    override fun onPause() {
        onPauseSubject.onNext(INVOCATION)
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        roomRepository.exitRoom()
    }

    fun onPageSelected(fragment: Fragment) {
        isChatFragment.set(fragment is ChatFragment)
    }

    companion object {
        val INVOCATION = Unit
    }
}