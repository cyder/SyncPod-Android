package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.BuildConfig
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.google.android.youtube.player.YouTubePlayer
import com.hosopy.actioncable.Consumer
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject


/**
 * Created by chigichan24 on 2018/04/15.
 */
class VideoDataRepository @Inject constructor(
        private val syncPodWsApi: SyncPodWsApi
) : VideoRepository {
    private var prepareVideo: Subject<Video> = BehaviorSubject.create()
    private var playingVideo: Subject<Video> = BehaviorSubject.create()
    private var playList: Subject<List<Video>> = BehaviorSubject.create()
    private var isPlaying: Subject<Boolean> = BehaviorSubject.create()
    override val developerKey: Flowable<String> = Flowable.just(BuildConfig.YOUTUBE_DEVELOPER_KEY)
    override val playListObservable: Flowable<List<Video>>
        get() = playList.toFlowable(BackpressureStrategy.LATEST)
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
        initSubjects()
    }

    override fun observeIsPlaying(): Flowable<Boolean> {
        return isPlaying.distinctUntilChanged().toFlowable(BackpressureStrategy.LATEST)
    }

    override fun observePrepareVideo(): Flowable<Video> {
        return prepareVideo.toFlowable(BackpressureStrategy.LATEST)
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

    override fun addPlayList(video: Video): Completable {
        syncPodWsApi.requestAddVideo(video.youtubeVideoId)
        return Completable.complete()
    }

    private fun getNextVideo(): Video? {
        val playlist = playList.blockingFirst()
        val video = playlist.firstOrNull()
        video?.apply {
            this@VideoDataRepository.playList.onNext(playlist.drop(1))
        }
        return video
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

    private fun initSubjects() {
        syncPodWsApi.isEntered
                .filter { it }
                .subscribe {
                    prepareVideo = BehaviorSubject.create()
                    playingVideo = BehaviorSubject.create()
                    playList = BehaviorSubject.createDefault(listOf())
                    isPlaying = BehaviorSubject.createDefault(false)
                    startObserve()
                }

        syncPodWsApi.isEntered
                .filter { !it }
                .subscribe {
                    prepareVideo.onComplete()
                    playingVideo.onComplete()
                    playList.onComplete()
                    isPlaying.onComplete()
                }
    }

}

