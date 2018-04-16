package com.cyder.atsushi.youtubesync.viewmodel

import com.cyder.atsushi.youtubesync.repository.RoomRepository
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.view.helper.Navigator
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/11.
 */

class VideoFragmentViewModel @Inject constructor(
        private val navigator: Navigator,
        private val roomRepository: RoomRepository,
        private val videoRepository: VideoRepository
) : FragmentViewModel() {
    lateinit var youtubeFragment: YouTubePlayerSupportFragment
    private lateinit var player: YouTubePlayer
    val isInitializedPlayer: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    override fun onStart() {
        val key = videoRepository.developerKey.blockingFirst()
        val listener = videoRepository.playerState.blockingFirst()
        youtubeFragment.initialize(key, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
                if (!wasRestored) {
                    player?.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                    player?.setPlayerStateChangeListener(listener)
                }
                player?.apply {
                    this@VideoFragmentViewModel.player = this
                    isInitializedPlayer.onNext(true)
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider?, error: YouTubeInitializationResult?) {
            }
        })
        Observables.combineLatest(
                videoRepository.getNowPlayingVideo().toObservable(),
                isInitializedPlayer.flatMap { Observable.just(it) }) { video, endedFlag -> Pair(video, endedFlag) }
                .filter { it.second == true }
                .subscribe {
                    player.loadVideo(it.first.youtubeVideoId, it.first.currentTime!! * 1000)
                }
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
        isInitializedPlayer.onNext(false)
    }

}