package com.cyder.atsushi.youtubesync.viewmodel

import android.databinding.ObservableInt
import com.cyder.atsushi.youtubesync.repository.VideoRepository
import com.cyder.atsushi.youtubesync.viewmodel.base.FragmentViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/11.
 */

class VideoFragmentViewModel @Inject constructor(
        private val videoRepository: VideoRepository
) : FragmentViewModel() {
    lateinit var youtubeFragment: YouTubePlayerSupportFragment
    private lateinit var player: YouTubePlayer
    val isInitializedPlayer: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(false)
    var nowProgress = ObservableInt(0)
    var maxProgress = ObservableInt(10000)

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
                isInitializedPlayer.filter { it }
        ).subscribe {
            val video = it.first
            player.loadVideo(video.youtubeVideoId, (video.currentTime ?: 0) * 1000)
        }

        Observables.combineLatest(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                isInitializedPlayer.filter { it }
        ).map { player.currentTimeMillis }
                .filter { player.durationMillis > .0 }
                .subscribe { nowProgress.set((maxProgress.get().toDouble() * it / player.durationMillis).toInt()) }
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

}