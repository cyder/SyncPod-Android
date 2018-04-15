package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.SyncPodApi
import com.cyder.atsushi.youtubesync.model.Video
import com.google.android.youtube.player.YouTubePlayer
import com.hosopy.actioncable.Consumer
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/15.
 */
class VideoDataRepository @Inject constructor(
        private val syncPodApi: SyncPodApi,
        private val consumer: Consumer
) : VideoRepository {
    private val loadNextVideo: BehaviorSubject<Video> = BehaviorSubject.create()
    private val playList: List<Video>? = null
    override val playerState: Flowable<YouTubePlayer.PlayerStateChangeListener>
        get() {
            val listener = object : YouTubePlayer.PlayerStateChangeListener {
                override fun onAdStarted() {
                }

                override fun onError(error: YouTubePlayer.ErrorReason?) {
                }

                override fun onLoaded(s: String?) {
                }

                override fun onLoading() {
                }

                override fun onVideoEnded() {
                    playList?.run {
                        loadNextVideo.onNext(playList[0])
                    }

                }

                override fun onVideoStarted() {
                }
            }
            return Flowable.just(listener)
        }

    override fun getNextVideo(): Observable<Video> {
        return loadNextVideo.flatMap {
            Observable.just(it)
        }
    }
}

