package com.cyder.atsushi.youtubesync.repository

import android.util.Log
import com.cyder.atsushi.youtubesync.BuildConfig
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.websocket.Response
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.google.android.youtube.player.YouTubePlayer
import com.google.gson.GsonBuilder
import com.hosopy.actioncable.Consumer
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/15.
 */
class VideoDataRepository @Inject constructor(
        private val consumer: Consumer,
        private val syncPodWsApi: SyncPodWsApi
) : VideoRepository {
    private var prepareVideo: Subject<Video> = BehaviorSubject.create()
    private var playingVideo: Subject<Video> = BehaviorSubject.create()
    private var playList: Subject<List<Video>> = BehaviorSubject.createDefault(listOf())
    private var isPlaying: Subject<Boolean> = BehaviorSubject.createDefault(false)
    override val developerKey: Flowable<String> = Flowable.just(BuildConfig.YOUTUBE_DEVELOPER_KEY)
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
                    getNextVideo()?.apply {
                        prepareVideo.onNext(this)
                    } ?: apply {
                        isPlaying.onNext(false)
                    }
                }

                override fun onVideoStarted() {
                }
            }
            return Flowable.just(listener)
        }

    init {
        startObserve()
    }

    override fun observeIsPlaying(): Flowable<Boolean> {
        isPlaying.subscribe {
        }
        return isPlaying.distinctUntilChanged().toFlowable(BackpressureStrategy.LATEST)
    }

    override fun observePrepareVideo(): Flowable<Video> {
        return playingVideo.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun observeNowPlayingVideo(): Flowable<Video> {
        return playingVideo.toFlowable(BackpressureStrategy.LATEST)
    }

    override fun getNowPlayingVideo(): Flowable<Video> {
        syncPodWsApi.requestNowPlayingVideo()
        return observeNowPlayingVideo()
    }

    override fun getPlayList(): Flowable<List<Video>> {
        syncPodWsApi.requestPlayList()
        return Flowable.empty()
    }

    override fun resetStatus() {
        prepareVideo.onComplete()
        playingVideo.onComplete()
        playList.onComplete()
        isPlaying.onComplete()
        prepareVideo = BehaviorSubject.create()
        playingVideo = BehaviorSubject.create()
        playList = BehaviorSubject.createDefault(listOf())
        isPlaying = BehaviorSubject.createDefault(false)
        syncPodWsApi.exitRoom()
        startObserve()
    }

    private fun getNextVideo(): Video? {
        val playlist = playList.blockingFirst()
        val video = playlist.firstOrNull()
        video?.apply {
            this@VideoDataRepository.playList.onNext(playlist.drop(1))
        }
        return video
    }

    private fun String.toResponse(): Response {
        return GsonBuilder().create().fromJson(this, Response::class.java)
    }

    private fun startObserve() {
        syncPodWsApi.nowPlayingResponse
                .subscribe {
                    it.data?.apply {
                        playingVideo.onNext(this.video.toModel())
                        isPlaying.onNext(true)
                    }
                }
        syncPodWsApi.playListResponse
                .subscribe {
                    it.data?.apply {
                        this@VideoDataRepository.playList
                                .onNext(this.playList.map { it.toModel() })
                    }
                }
        syncPodWsApi.addVideoResponse
                .subscribe {
                    it.data?.apply {
                        if (isPlaying.blockingFirst()) {
                            val newPlayList = this@VideoDataRepository.playList.blockingFirst() + video.toModel()
                            this@VideoDataRepository.playList.onNext(newPlayList)
                        } else {
                            prepareVideo.onNext(video.toModel())
                            isPlaying.onNext(true)
                        }
                    }
                }
    }

    companion object {
        const val NOW_PLAYING: String = "now_playing_video"
        const val START_VIDEO: String = "start_video"
        const val PLAY_LIST: String = "play_list"
        const val ADD_VIDEO: String = "add_video"
    }
}

