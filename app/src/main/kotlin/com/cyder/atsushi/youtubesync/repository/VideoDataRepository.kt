package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.websocket.Response
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.GsonBuilder
import com.hosopy.actioncable.Consumer
import com.hosopy.actioncable.Subscription
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/15.
 */
class VideoDataRepository @Inject constructor(
        private val consumer: Consumer,
        private val subscription: Subscription
) : VideoRepository {
    private val playingVideo: Subject<Video> = BehaviorSubject.create()
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
                }

                override fun onVideoStarted() {
                }
            }
            return Flowable.just(listener)
        }

    init {
        startRouting()
    }

    override fun getNowPlayingVideo(): Observable<Video> {
        subscription.perform(NOW_PLAYING)
        return playingVideo.flatMap {
            Observable.just(it)
        }
    }

    private fun startRouting() {
        subscription.onReceived = {
            when (it) {
                is String -> {
                    val response = it.toResponse()
                    when (response.dataType) {
                        NOW_PLAYING, START_VIDEO -> {
                            response.data?.apply {
                                playingVideo.onNext(this.video.toModel())
                            }
                        }
                    }
                }
            }
        }
    }

    private fun String.toResponse(): Response {
        return GsonBuilder().create().fromJson(this, Response::class.java)
    }

    companion object {
        const val NOW_PLAYING: String = "now_playing_video"
        const val START_VIDEO: String = "start_video"
    }
}

