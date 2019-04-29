package com.cyder.android.syncpod.viewmodel

import androidx.databinding.ObservableInt
import com.cyder.android.syncpod.repository.VideoRepository
import com.cyder.android.syncpod.viewmodel.base.FragmentViewModel
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
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
    private val onPauseSubject = PublishSubject.create<Unit>()
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
    }

    override fun onResume() {
        observerWithInitPlayer()
    }

    override fun onPause() {
        onPauseSubject.onNext(INVOCATION)
    }

    override fun onStop() {
    }

    private fun observerWithInitPlayer() {
        Observables.combineLatest(
                videoRepository.observePrepareVideo().toObservable(),
                isInitializedPlayer.filter { it }
        )
                .takeUntil(onPauseSubject)
                .subscribe {
                    val video = it.first
                    player.cueVideo(video.youtubeVideoId)
                }

        Observables.combineLatest(
                videoRepository.observeNowPlayingVideo().toObservable(),
                isInitializedPlayer.filter { it }
        )
                .takeUntil(onPauseSubject)
                .subscribe {
                    val video = it.first
                    player.loadVideo(video.youtubeVideoId, (video.currentTime ?: 0) * 1000)
                }

        Observables.combineLatest(
                Observable.interval(100, TimeUnit.MILLISECONDS),
                isInitializedPlayer.filter { it }
        )
                .takeUntil(onPauseSubject)
                .map { player.currentTimeMillis }
                .filter { player.durationMillis > .0 }
                .subscribe { nowProgress.set((maxProgress.get().toDouble() * it / player.durationMillis).toInt()) }
    }

    companion object {
        val INVOCATION = Unit
    }
}