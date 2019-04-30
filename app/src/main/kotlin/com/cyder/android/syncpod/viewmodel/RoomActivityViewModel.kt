package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import com.cyder.android.syncpod.repository.ChatRepository
import com.cyder.android.syncpod.R
import com.cyder.android.syncpod.repository.RoomRepository
import com.cyder.android.syncpod.repository.VideoRepository
import com.cyder.android.syncpod.view.fragment.ChatFragment
import com.cyder.android.syncpod.view.helper.Navigator
import com.cyder.android.syncpod.viewmodel.base.ActivityViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RoomActivityViewModel @Inject constructor(
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository,
        private val chatRepository: ChatRepository,
        private val navigator: Navigator
) : ActivityViewModel() {
    var isVideoPlayerVisible = ObservableBoolean(false)
    var isChatFragment = ObservableBoolean(false)
    var isHideSoftwareKeyboard = ObservableBoolean(false)
    private val onPauseSubject = PublishSubject.create<Unit>()

    lateinit var roomKey: String

    override fun onStart() {
        if (!roomRepository.isEntered.blockingFirst()) {
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
        isHideSoftwareKeyboard.set(fragment !is ChatFragment)
    }

    companion object {
        val INVOCATION = Unit
    }
}