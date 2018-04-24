package com.cyder.atsushi.youtubesync.repository

import com.cyder.atsushi.youtubesync.BuildConfig
import com.cyder.atsushi.youtubesync.api.mapper.toModel
import com.cyder.atsushi.youtubesync.model.Video
import com.cyder.atsushi.youtubesync.websocket.SyncPodWsApi
import com.google.android.youtube.player.YouTubePlayer
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
    private var playListSubject: Subject<List<Video>> = BehaviorSubject.create()
    private var isPlaying: Subject<Boolean> = BehaviorSubject.create()
    private var playList: MutableList<Video> = mutableListOf()
    override val developerKey: Flowable<String> = Flowable.just(BuildConfig.YOUTUBE_DEVELOPER_KEY)
    override val playListObservable: Flowable<List<Video>>
        get() = playListSubject.toFlowable(BackpressureStrategy.LATEST)
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

    override fun addPlayList(video: Video): Completable {
        syncPodWsApi.requestAddVideo(video.youtubeVideoId)
        return Completable.complete()
    }

    private fun getNextVideo(): Video? {
        val video = playList.firstOrNull()
        video?.apply {
            playList.drop(1)
            this@VideoDataRepository.playListSubject.onNext(playList)
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
                        this@VideoDataRepository.playList = this.playList.map { it.toModel() }.toMutableList()
                        this@VideoDataRepository.playListSubject
                                .onNext(this@VideoDataRepository.playList)
                    }
                }
        syncPodWsApi.addVideoResponse
                .subscribe {
                    it.data?.apply {
                        if (isPlaying.blockingFirst()) {
                            this@VideoDataRepository.playList.add(this.video.toModel())
                            this@VideoDataRepository.playListSubject
                                    .onNext(this@VideoDataRepository.playList)
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
                    playListSubject = BehaviorSubject.createDefault(listOf())
                    isPlaying = BehaviorSubject.createDefault(false)
                    startObserve()
                }

        syncPodWsApi.isEntered
                .filter { !it }
                .subscribe {
                    prepareVideo.onComplete()
                    playingVideo.onComplete()
                    playListSubject.onComplete()
                    isPlaying.onComplete()
                }
    }

}

